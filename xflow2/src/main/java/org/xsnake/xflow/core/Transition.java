package org.xsnake.xflow.core;

import org.dom4j.Element;
import org.xsnake.common.utils.StringUtil;
import org.xsnake.xflow.api.DefinitionException;

/**
 * @author Jerry.Zhao
 * 
 */
public class Transition {

	protected String id;

	protected String name;

	/**
	 * 流转的目标节点
	 */
	protected Activity toActivity;

	/**
	 * 流转的源头节点
	 */
	protected Activity srcActivity;

	public Transition(String id, String name, Activity srcActivity, Activity toActivity) {
		this.id = id;
		this.name = name;
		this.srcActivity = srcActivity;
		this.toActivity = toActivity;
	}

	public Transition(Activity srcActivity, Element element) {
		String to = element.attributeValue("to");
		name = element.attributeValue("name");
		id = element.attributeValue("id");
		if (StringUtil.isEmpty(id)) {
			throw new DefinitionException("流转的ID不能为空");
		}
		if (StringUtil.isEmpty(name)) {
			throw new DefinitionException("流转的NAME不能为空");
		}
		if (StringUtil.isEmpty(to)) {
			throw new DefinitionException("指向的节点信息不能为空");
		}
		this.srcActivity = srcActivity;
		this.toActivity = srcActivity.processDefinition.findActivity(to);
		if (toActivity == null) {
			throw new DefinitionException("流转信息 to:" + to + " 定义中没有找到指向的节点");
		}
		if (srcActivity == toActivity) {
			throw new DefinitionException("流转信息不能流向自身节点");
		}
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Activity getToActivity() {
		return toActivity;
	}

	public Activity getSrcActivity() {
		return srcActivity;
	}
	
}
