package org.xsnake.xflow.core;

import java.util.HashMap;
import java.util.Map;

import org.xsnake.common.dao.BaseDao;
import org.xsnake.xflow.api.Participant;
import org.xsnake.xflow.api.model.ProcessInstance;
import org.xsnake.xflow.api.model.Task;

public class ProcessInstanceContext {

	BaseDao baseDao;
	
	ProcessDefinition processDefinition;
	
	ProcessInstance processInstance;
	
	Task task;
	
	Participant operator;
	
	String comment;
	
	ProcessInstance parentProcessInstance;
	
	Transition toTransition;
	
	String title;
	
	String businessKey;
	
	String businessType;
	
	Map<String,String> varmap = new HashMap<String,String>();
	
	public Map<String,String> getContextMap(){
		Map<String,String> map = new HashMap<String,String>();
		map.putAll(varmap);
		map.put("businessKey", processInstance.getBusinessKey());//业务ID
		map.put("businessType", processInstance.getBusinessType());//业务类型
		map.put("operatorId", operator.getId());//操作者ID
		map.put("operatorName", operator.getName());//操作者名称
		map.put("operatorType", operator.getType());//操作者类型
		map.put("processInstanceId", processInstance.getId());//流程实例ID
		map.put("creatorId", processInstance.getCreatorId());//创建者ID
		map.put("creatorName", processInstance.getCreatorName());//创建者名称
		map.put("creatorType", processInstance.getCreatorType());//创建者类型
		map.put("processDefinitionCode", processInstance.getProcessDefinitionCode());//流程定义编码
		return map;
	}
	
	int orderNo = 0;
	
	public ProcessInstanceContext(BaseDao baseDao){
		this.baseDao = baseDao;
	}
	
	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}

	public void setProcessDefinition(ProcessDefinition processDefinition) {
		this.processDefinition = processDefinition;
	}

	public BaseDao getBaseDao() {
		return baseDao;
	}

	public ProcessInstance getProcessInstance() {
		return processInstance;
	}

	public Task getTask() {
		return task;
	}

	public Participant getOperator() {
		return operator;
	}

	public String getComment() {
		return comment;
	}

	public ProcessInstance getParentProcessInstance() {
		return parentProcessInstance;
	}

	public void setProcessInstance(ProcessInstance processInstance) {
		this.processInstance = processInstance;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public void setOperator(Participant operator) {
		this.operator = operator;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setParentProcessInstance(ProcessInstance parentProcessInstance) {
		this.parentProcessInstance = parentProcessInstance;
	}

	public void setVarmap(Map<String, String> varmap) {
		this.varmap.putAll(varmap);
	}

	public Transition getToTransition() {
		return toTransition;
	}

	public void setToTransition(Transition toTransition) {
		this.toTransition = toTransition;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public int getOrderNo() {
		orderNo++;
		return orderNo;
	}
	
}
