package org.xsnake.xflow.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.xsnake.common.utils.StringUtil;
import org.xsnake.xflow.api.DefinitionException;
import org.xsnake.xflow.api.XflowException;

/**
 * 节点定义，一个节点一般包含了参与者以及一个或者多个流转信息
 * 抽象的节点只包含了流转信息。抽象节点下分为两大类节点，一类为人工参与完成，一类为程序自动完成
 * @author Jerry.Zhao
 *
 */
public abstract class Activity {
	
	protected String id;
	
	protected String name;
	
	protected String type;
	
	protected ProcessDefinition processDefinition;
	
	protected Map<String,Transition> transitionMap = new LinkedHashMap<String,Transition>();
	
	private Element activityElement;
	
	public Activity(ProcessDefinition processDefinition, Element activityElement) {
		id = activityElement.attributeValue("id");
		name = activityElement.attributeValue("name");
		type = activityElement.attributeValue("type");
		this.processDefinition = processDefinition;
		this.activityElement = activityElement;
		if(StringUtil.isEmpty(id)){
			throw new DefinitionException("节点必须包含ID属性");
		}
		if(StringUtil.isEmpty(name)){
			throw new DefinitionException("节点必须包含NAME属性");
		}
		if(StringUtil.isEmpty(type)){
			throw new DefinitionException("节点必须包含TYPE属性");
		}
	}
	
	/**
	 * 初始XML定义中的流转信息
	 */
	public void initTransition() {
		@SuppressWarnings("unchecked")
		List<Element> list = activityElement.elements("transition");
		for (Element transitionElement : list) {
			Transition transition = new Transition(this, transitionElement);
			transitionMap.put(transition.id, transition);
		}
	}
	
	public List<Transition> getTransitionList(){
		return new ArrayList<Transition>(transitionMap.values());
	}
	
	/**
	 * 节点做自己要做的工作，完成工作后返回下一步得流转信息
	 * @param context 流程实例上线文
	 * @return
	 * @throws XflowException
	 */
	public abstract List<Transition> doWork(ProcessInstanceContext context) throws XflowException;
	/**
	 * 检查每个节点的定义是否有错误
	 */
	public abstract void definitionValidate();
	/**
	 * 调度者需要通过此标志区分是人工参与还是程序自动执行
	 * @return
	 */
	public abstract boolean isAutomatic();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ProcessDefinition getProcessDefinition() {
		return processDefinition;
	}
	
}
