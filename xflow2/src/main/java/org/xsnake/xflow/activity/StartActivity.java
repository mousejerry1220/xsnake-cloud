package org.xsnake.xflow.activity;

import java.util.Date;
import java.util.List;

import org.dom4j.Element;
import org.springframework.beans.BeanUtils;
import org.xsnake.common.dao.BaseDao;
import org.xsnake.common.utils.StringUtil;
import org.xsnake.xflow.api.DefinitionException;
import org.xsnake.xflow.api.XflowException;
import org.xsnake.xflow.api.model.HistoryProcessInstance;
import org.xsnake.xflow.api.model.ProcessInstance;
import org.xsnake.xflow.core.Activity;
import org.xsnake.xflow.core.ProcessDefinition;
import org.xsnake.xflow.core.ProcessInstanceContext;
import org.xsnake.xflow.core.Transition;
import org.xsnake.xflow.participant.CreatorParticipant;

public class StartActivity extends AutomaticActivity{

	public StartActivity(ProcessDefinition processDefinition, Element activityElement) {
		super(processDefinition, activityElement);
	}

	@Override
	public void definitionValidate() {
		List<Transition> transitionList = getTransitionList();
		if(transitionList.size() != 1){
			throw new DefinitionException("开始节点有且只有一个流转信息");
		}
		Transition transition = transitionList.get(0);
		Activity toActivity = transition.getToActivity(); 
		if(!(toActivity instanceof TaskActivity)){
			throw new DefinitionException("开始节点后的第一个节点必须为任务节点");
		}
		
		TaskActivity taskActivity = (TaskActivity)toActivity;
		
		if(!(taskActivity.getParticipantHandle() instanceof CreatorParticipant)){
			throw new DefinitionException("第一个任务节点的参与者必须为发起人");
		}
		
	}

	@Override
	public List<Transition> doWork(ProcessInstanceContext context) throws XflowException {
		BaseDao baseDao = context.getBaseDao();
		String id = StringUtil.getUUID();
		ProcessDefinition pd = this.getProcessDefinition();
		HistoryProcessInstance hpi = new HistoryProcessInstance();
		hpi.setId(id);
		hpi.setBusinessKey(context.getBusinessKey());
		hpi.setBusinessType(context.getBusinessType());
		hpi.setStartTime(new Date());
		hpi.setName(context.getTitle());
		hpi.setProcessDefinitionId(pd.getId());
		hpi.setProcessDefinitionName(pd.getName());
		hpi.setProcessDefinitionCode(pd.getCode());
		hpi.setCreatorId(context.getOperator().getId());
		hpi.setCreatorName(context.getOperator().getName());
		hpi.setCreatorType(context.getOperator().getType());
		hpi.setStatus("running");
		baseDao.save(hpi);
		
		ProcessInstance pi = new ProcessInstance();
		BeanUtils.copyProperties(hpi, pi);
		baseDao.save(pi);
		
		context.setProcessInstance(pi);
		context.setProcessDefinition(processDefinition);
		return getTransitionList();
	}

}
