package org.xsnake.xflow.core;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.xsnake.common.utils.StringUtil;
import org.xsnake.xflow.activity.ActivityFactory;
import org.xsnake.xflow.api.DefinitionException;

/** 
* 流程定义。
* 标准的流程中：流程定义包含了整个流程中的所有节点(activity)以及流转(transition)，
* 但是在很多人员参与的场合会动态派生出一些标准流程定义以外的节点以及流转，这些节点和流转是事先并没有定义好的。
* 在实际的使用中如：某个节点需要加签、驳回（这些也许是节点的一个属性是否可加签，是否可驳回，也许默认都为是）
* 那么在流程实例的运行中，在这些节点就可以派生出新的节点、流程。
* 
* 将XML形式的描述转化为结构化对象的形式。
* 
* @author Jerry.Zhao 
* @version 创建时间：2017年6月16日 下午11:16:44 
* 
*/
public class ProcessDefinition {

	private String id;
	/**
	 * 流程定义的名称
	 */
	private String name;
	/**
	 * 客户端开启流程时通过CODE查找具体的流程定义，如果流程代码相同，在新建时为版本升级
	 */
	private String code;
	/**
	 * 流程定义描述
	 */
	private String description;
	/**
	 * 流程定义的节点开始起点
	 */
	Activity startActivity = null;
	/**
	 * 存储流程定义中包含的全部节点
	 */
	private Map<String,Activity> activityMap = new LinkedHashMap<String,Activity>();
	
	private Map<String,Transition> transitionMap = new LinkedHashMap<String,Transition>();
	
	/**
	 * 新建一个流程定义时使用
	 * @param xml
	 */
	public ProcessDefinition(String xml){
		this(StringUtil.getUUID(),xml);
	}
	
	/**
	 * 从已存在的流程定义中加载时使用
	 * @param id
	 * @param xml
	 */
	public ProcessDefinition(String id,String xml){
		this.id = id;
		
		Document document = null;
		try {
			document = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			throw new DefinitionException("流程定义XML错误");
		}
		
		Element root = document.getRootElement();
		
		name = root.attributeValue("name");
		if(StringUtil.isEmpty(name)){
			throw new DefinitionException("必须指定流程名称");
		}
		
		code = root.attributeValue("code");
		if(code == null){
			throw new DefinitionException("必须指定流程代码");
		}
		
		description = root.elementText("description");
		
		@SuppressWarnings("unchecked")
		List<Element> list = root.elements("activity");
		
		/**
		 * 实例定义中的节点并找出开始节点
		 */
		for(Element activityElement : list){
			Activity activity = ActivityFactory.create(this,activityElement);
			if(activityMap.get(activity.id) != null){
				throw new DefinitionException("流程定义中存在重复的节点:" + activity.id + "["+activity.name+"]");
			}
			activityMap.put(activity.id,activity);
			//每个流程定义有且只有一个开始
			if(activity.type.equalsIgnoreCase("start")){
				if(startActivity != null){
					throw new DefinitionException("流程定义中存在重复的开始节点");
				}
				startActivity = activity;
			}
		}
		
		if(startActivity == null){
			throw new DefinitionException("流程定义必须有且只有一个开始节点");
		}
		
		/**
		 * 初始化所有节点中的流转信息
		 * 将所有的流转信息注册到流程定义中
		 */
		Collection<Activity> activityList = activityMap.values();
		for(Activity activity : activityList){
			activity.initTransition();
			List<Transition> transitionList = activity.getTransitionList();
			for(Transition transition : transitionList){
				if(transitionMap.get(transition.id) != null){
					throw new DefinitionException("流程定义中存在重复的流转:"+transition.id);
				}
				transitionMap.put(transition.id, transition);
			}
		}
		
		/**
		 * 初始化已完毕，检查定义是否有错误
		 */
		for(Activity activity : activityList){
			activity.definitionValidate();
		}
	}
	
	public Activity findActivity(String id){
		return activityMap.get(id);
	}
	
	public Transition findTransition(String id){
		return transitionMap.get(id);
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
}
