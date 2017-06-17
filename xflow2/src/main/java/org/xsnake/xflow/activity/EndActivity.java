package org.xsnake.xflow.activity;

import java.util.List;

import org.dom4j.Element;
import org.xsnake.xflow.api.DefinitionException;
import org.xsnake.xflow.api.XflowException;
import org.xsnake.xflow.core.ProcessDefinition;
import org.xsnake.xflow.core.ProcessInstanceContext;
import org.xsnake.xflow.core.Transition;

public class EndActivity extends AutomaticActivity {
	
	public EndActivity(ProcessDefinition processDefinition, Element activityElement) {
		super(processDefinition, activityElement);
	}

	@Override
	public List<Transition> doWork(ProcessInstanceContext context) throws XflowException {
		return null;
	}

	@Override
	public void definitionValidate() {
		if(getTransitionList().size() > 0 ){
			throw new DefinitionException("结束节点不能包含流转信息");
		}
	}

}
