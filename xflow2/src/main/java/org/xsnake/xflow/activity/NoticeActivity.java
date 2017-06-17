package org.xsnake.xflow.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dom4j.Element;
import org.xsnake.common.utils.StringUtil;
import org.xsnake.xflow.api.DefinitionException;
import org.xsnake.xflow.api.Participant;
import org.xsnake.xflow.api.XflowException;
import org.xsnake.xflow.api.model.HistoryActivityInstance;
import org.xsnake.xflow.core.ParticipantHandle;
import org.xsnake.xflow.core.ProcessDefinition;
import org.xsnake.xflow.core.ProcessInstanceContext;
import org.xsnake.xflow.core.Transition;
import org.xsnake.xflow.participant.ParticipantHandleFactory;

public class NoticeActivity extends AutomaticActivity{

	ParticipantHandle participantHandle = null;
	
	public NoticeActivity(ProcessDefinition processDefinition, Element activityElement) {
		super(processDefinition, activityElement);
		Element participantElement = activityElement.element("participants");
		if(participantElement==null){
			throw new DefinitionException("通知类型节点必须指定参与者");
		}
		participantHandle = ParticipantHandleFactory.getParticipantHandle(this,participantElement);
	}

	@Override
	public List<Transition> doWork(ProcessInstanceContext context) throws XflowException {
		
		List<Participant> participantsList = participantHandle.findParticipantList(context);
		
		for(Participant participant : participantsList){
			List<Object> args = new ArrayList<Object>();
			args.add(StringUtil.getUUID());
			args.add("NOTICE");
			args.add(participant.getId());
			args.add(participant.getName());
			args.add(participant.getType());
			args.add(context.getProcessInstance().getId());
			args.add(context.getProcessInstance().getName());
			args.add(getName());
			args.add(new Date());
			args.add("N");
			context.getBaseDao().executeSQL( " insert into xflow_notice (ROW_ID,NOTICE_TYPE,PARTICIPANT_ID,PARTICIPANT_NAME,PARTICIPANT_TYPE,PROCESS_INSTANCE_ID,PROCESS_INSTANCE_NAME,ACTIVITY_NAME,CREATE_DATE,STATUS) values (?,?,?,?,?,?,?,?,?,?) ",args.toArray());
			
			HistoryActivityInstance hai = new HistoryActivityInstance();
			hai.setActivityId(this.getId());
			hai.setActivityName(this.getName());
			hai.setActivityType(this.getType());
			hai.setStartTime(new Date());
			hai.setProcessDefinitionId(context.getProcessDefinition().getId());
			hai.setProcessInstanceId(context.getProcessInstance().getId());

			hai.setOperatorId(participant.getId());
			hai.setOperatorName(participant.getName());
			
			hai.setAssigneeId(participant.getId());
			hai.setAssigneeName(participant.getName());
			hai.setAssigneeType(participant.getType());
			
			hai.setId(StringUtil.getUUID());
			hai.setStatus("end");
			hai.setEndTime(new Date());
			context.getBaseDao().save(hai);
		
		}
		return getTransitionList();
	}

	@Override
	public void definitionValidate() {
		if(getTransitionList().size() != 1){
			throw new DefinitionException("通知节点有且只有一个流转信息");
		}
	}

}
