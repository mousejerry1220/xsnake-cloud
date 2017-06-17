package org.xsnake.xflow.activity;

import java.util.List;

import org.dom4j.Element;
import org.xsnake.xflow.api.DefinitionException;
import org.xsnake.xflow.api.XflowException;
import org.xsnake.xflow.core.ProcessDefinition;
import org.xsnake.xflow.core.ProcessInstanceContext;
import org.xsnake.xflow.core.Transition;

public class ForkActivity extends AutomaticActivity{

	public ForkActivity(ProcessDefinition processDefinition, Element activityElement) {
		super(processDefinition, activityElement);
	}

	//将任务分支为多个
	@Override
	public List<Transition> doWork(ProcessInstanceContext context) throws XflowException {
		return getTransitionList();
	}

	@Override
	public void definitionValidate() {
		if(getTransitionList().size() < 2){
			throw new DefinitionException("分支节点必须包含多个流转信息");
		}
	}

}
