package org.xsnake.xflow.activity;

import java.util.List;

import org.dom4j.Element;
import org.xsnake.common.dao.BaseDao;
import org.xsnake.xflow.api.DefinitionException;
import org.xsnake.xflow.api.XflowException;
import org.xsnake.xflow.api.model.Task;
import org.xsnake.xflow.core.ProcessDefinition;
import org.xsnake.xflow.core.ProcessInstanceContext;
import org.xsnake.xflow.core.Transition;

public class JoinActivity extends AutomaticActivity{

	public JoinActivity(ProcessDefinition processDefinition, Element activityElement) {
		super(processDefinition, activityElement);
	}

	/**
	 * join节点会等待所有任务结束才进入下一个节点
	 */
	@Override
	public List<Transition> doWork(ProcessInstanceContext context) throws XflowException {
		BaseDao baseDao = context.getBaseDao();
		List<Task> list = baseDao.findEntity(" from Task t where t.processInstanceId = ? ",context.getProcessInstance().getId());
		if(list.size() == 0){
			return getTransitionList();
		}
		return null;
	}

	@Override
	public void definitionValidate() {
		if(getTransitionList().size() != 1){
			throw new DefinitionException("汇合节点有且只有一个流转信息");
		}
	}

}
