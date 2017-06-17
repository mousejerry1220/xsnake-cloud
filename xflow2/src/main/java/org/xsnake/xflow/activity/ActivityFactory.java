package org.xsnake.xflow.activity;

import org.dom4j.Element;
import org.xsnake.xflow.core.Activity;
import org.xsnake.xflow.core.ProcessDefinition;

public class ActivityFactory {

	public static Activity create(ProcessDefinition processDefinition,Element activityElement){
		
		Activity activity = null;
		//获取流程定义的类型
		String type = activityElement.attributeValue("type");
		//创建定义的流程类型实例
		//开始类型
		if(type.equalsIgnoreCase("start")){
			activity = new StartActivity(processDefinition, activityElement);
		}
		
		//结束类型
		else if (type.equalsIgnoreCase("end")){
			activity = new EndActivity(processDefinition, activityElement);
		} 
		
		//人员参与的任务类型
		else if (type.equalsIgnoreCase("task")){
			activity = new TaskActivity(processDefinition, activityElement);
		}
		
		//自动判断类型
		else if (type.equalsIgnoreCase("decision")){
			activity = new DecisionActivity(processDefinition, activityElement);
		}
		
		else if (type.equalsIgnoreCase("fork")){
			activity = new ForkActivity(processDefinition, activityElement);
		}
		
		else if (type.equalsIgnoreCase("join")){
			activity = new JoinActivity(processDefinition, activityElement);
		}
		
		else if (type.equalsIgnoreCase("notice")) {
			activity = new NoticeActivity(processDefinition, activityElement);
		}
		return activity;
	}
}
