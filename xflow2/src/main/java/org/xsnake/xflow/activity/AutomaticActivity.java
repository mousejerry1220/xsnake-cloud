package org.xsnake.xflow.activity;

import org.dom4j.Element;
import org.xsnake.xflow.core.Activity;
import org.xsnake.xflow.core.ProcessDefinition;


public abstract class AutomaticActivity extends Activity {

	public AutomaticActivity(ProcessDefinition processDefinition, Element activityElement) {
		super(processDefinition, activityElement);
	}
	
	public boolean isAutomatic(){
		return true;
	}
	
}
