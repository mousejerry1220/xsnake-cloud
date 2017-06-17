package org.xsnake.xflow.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.xsnake.common.dao.BaseDao;
import org.xsnake.common.utils.StringUtil;
import org.xsnake.xflow.api.DefinitionException;
import org.xsnake.xflow.api.Participant;
import org.xsnake.xflow.api.XflowException;
import org.xsnake.xflow.api.model.HistoryActivityInstance;
import org.xsnake.xflow.api.model.Task;
import org.xsnake.xflow.core.Activity;
import org.xsnake.xflow.core.ParticipantHandle;
import org.xsnake.xflow.core.ProcessDefinition;
import org.xsnake.xflow.core.ProcessInstanceContext;
import org.xsnake.xflow.core.Transition;
import org.xsnake.xflow.participant.ExpandParticipant;
import org.xsnake.xflow.participant.ParticipantHandleFactory;

public class TaskActivity extends Activity{
	
	private ParticipantHandle participantHandle;
	
	String messageFlag;
	
	String rejectFlag;
	
	String closeFlag;

	public TaskActivity(ProcessDefinition processDefinition, Element activityElement) throws DefinitionException {
		super(processDefinition, activityElement);
		Element participantElement = activityElement.element("participants");
		if(participantElement==null){
			throw new DefinitionException("任务类型节点必须指定参与者");
		}
		
		//TODO替换成扩展属性Extensions
		messageFlag = activityElement.attributeValue("message");
		rejectFlag = activityElement.attributeValue("reject");
		closeFlag = activityElement.attributeValue("close");
		participantHandle = ParticipantHandleFactory.getParticipantHandle(this,participantElement);
	}
	
	//创建任务及日志
		public void createTask(ProcessInstanceContext context) {
			List<Participant> participantList = new ArrayList<Participant>();
			//查找办理人
			participantList = participantHandle.findParticipantList(context);
			if(participantHandle instanceof ExpandParticipant){
				if(participantList == null || participantList.size() == 0){
					participantList.add(new Participant("AUTO","AUTO","AUTO"));
				}
			}
			
			String batchNo = StringUtil.getUUID();
			BaseDao baseDao = context.getBaseDao();
			Map<String,String> ps = new HashMap<String,String>();
			Transition toTransition = context.getToTransition();
			for(Participant participant :participantList){
				Object obj = ps.get(participant.getType() + participant.getId());
				if(obj !=null){
					continue;
				}
				ps.put(participant.getType() + participant.getId(), participant.getType() + participant.getId());
				String id = StringUtil.getUUID();
				Task task = new Task();
				task.setId(id);
				task.setActivityId(this.getId());
				task.setActivityName(this.getName());
				task.setActivityType(this.getType());
				task.setAssigneeId(participant.getId());
				task.setAssigneeName(participant.getName());
				task.setAssigneeType(participant.getType());
				task.setBatchNo(batchNo);
				task.setFormURL(null);
				task.setForTaskId(null);
				task.setIsNormalTask("Y");
				task.setOrderNo(context.getOrderNo());
				task.setProcessDefinitionId(context.getProcessDefinition().getId());
				task.setProcessDefinitionName(context.getProcessDefinition().getName());
				task.setProcessInstanceId(context.getProcessInstance().getId());
				task.setStatus("running");
				task.setStartTime(new Date());
				task.setTitle(context.getProcessInstance().getName());
				task.setSrcActivityId(toTransition.getSrcActivity().getId());
				task.setSrcActivityName(toTransition.getSrcActivity().getName());
				task.setSrcTransitionId(toTransition.getId());
				task.setSrcTransitionName(toTransition.getName());
				
				if(getTransitionList().size() > 1){
					String ids = "";
					for(Transition _tr : getTransitionList()){
						ids = ids + _tr.getId() + ";";
					}
					task.setToIds(ids);
				}
				
				context.setTask(task);
				baseDao.save(task);
				
				HistoryActivityInstance hai = new HistoryActivityInstance();
				hai.setActivityId(this.getId());
				hai.setActivityName(this.getName());
				hai.setActivityType(this.getType());
				hai.setStartTime(new Date());
				hai.setProcessDefinitionId(context.getProcessDefinition().getId());
				hai.setProcessInstanceId(context.getProcessInstance().getId());
				if(this instanceof TaskActivity){
					hai.setId(task.getId());
					hai.setOrderNo(task !=null ? task.getOrderNo() : null);
					hai.setBatchNo(task !=null ? task.getBatchNo() : null);
					hai.setAssigneeId(task !=null ? task.getAssigneeId() : null);
					hai.setAssigneeName(task !=null ? task.getAssigneeName() : null);
					hai.setAssigneeType(task !=null ? task.getAssigneeType() : null);
					hai.setComment(context.getComment());
					hai.setEndTime(null);
					hai.setStatus("running");
				}
				baseDao.save(hai);
			}
		} 
	
	public boolean isAutomatic(){
		return false;
	}

	public ParticipantHandle getParticipantHandle() {
		return participantHandle;
	}

	@Override
	public void definitionValidate() {
		if(participantHandle == null ){
			throw new DefinitionException("任务节点必须包含参与者");
		}
	}
	
	//任何一方完成任务删除同批次任务
	public void completeTask(ProcessInstanceContext context) {
		Task task = context.getTask();
		BaseDao baseDao = context.getBaseDao();
		HistoryActivityInstance hai = baseDao.get(HistoryActivityInstance.class, task.getId());
		if("N".equals(task.getIsNormalTask())){
			throw new XflowException("流程引擎异常错误:流程定义外产生的任务,不由核心系统处理.");
		}
		
		Transition toTransition = context.getToTransition();
		baseDao.executeHQL(" delete Task t where t.batchNo = ? ",new Object[]{task.getBatchNo()});
		baseDao.executeHQL(" update HistoryActivityInstance set status='other end' , endTime = ? , comment = ? , orderNo = 2 where batchNo = ? and id != ? and status='running' " ,new Object[]{new Date(),"该任务已经被 "+context.getOperator().getName()+" 办理",task.getBatchNo(),task.getId()});
		hai.setToTransitionId(toTransition.getId());
		hai.setToTransitionName(toTransition.getName());
		
		if(toTransition.getToActivity()!=null){
			hai.setToActivityId(toTransition.getToActivity().getId());
			hai.setToActivityName(toTransition.getToActivity().getName());
		}
	
		hai.setEndTime(new Date());
		hai.setComment(context.getComment());
		hai.setOperatorId(context.getOperator().getId());
		hai.setOperatorName(context.getOperator().getName());
		hai.setStatus("end");
		baseDao.update(hai);
	}

	@Override
	public List<Transition> doWork(ProcessInstanceContext context) throws XflowException {
		return null;
	}
	
}
