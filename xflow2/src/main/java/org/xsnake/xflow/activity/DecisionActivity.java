package org.xsnake.xflow.activity;

import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.dom4j.Element;
import org.xsnake.common.utils.StringUtil;
import org.xsnake.xflow.api.DefinitionException;
import org.xsnake.xflow.api.XflowException;
import org.xsnake.xflow.core.ProcessDefinition;
import org.xsnake.xflow.core.ProcessInstanceContext;
import org.xsnake.xflow.core.Transition;

public class DecisionActivity extends AutomaticActivity {

	String expression;
	
	public DecisionActivity(ProcessDefinition processDefinition, Element activityElement) {
		super(processDefinition, activityElement);
		expression = activityElement.elementText("expression");
		if(expression == null || "".equals(expression.trim())){
			throw new DefinitionException("判断节点定义必须包含表达式");
		}
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	@Override
	public List<Transition> doWork(ProcessInstanceContext context) throws XflowException {
		String newExpression = null;
		try {
			newExpression = StringUtil.getTemplateString(expression, context.getContextMap());
			String to = String.valueOf(getEngine().eval("function test() {" + newExpression + "} test()"));
			if (StringUtil.isEmpty(to)) {
				throw new XflowException("表达式错误，没有返回一个正确的流转名称");
			}
			List<Transition> _transitionList = new ArrayList<Transition>();
			String[] ts;
			if(to.indexOf(",") > -1){
				ts = to.split(",");
			}else{
				ts = new String[]{to};
			}
			for(String t : ts){
				if(StringUtil.isEmpty(t.trim())){
					continue;
				}
				Transition transition = context.getProcessDefinition().findTransition(t);
				if (transition == null) {
					throw new XflowException("表达式所返回的流转名称有误，未找到");
				}
				// 判断返回的流转是否属于该判断环节
				if (!id.equals(transition.getSrcActivity().getId())) {
					throw new XflowException("没有找到正确的流转");
				}
				_transitionList.add(transition);
			}
			
			return _transitionList;
		} catch (Exception e) {
			throw new XflowException("表达式解析异常：" + e.getMessage());
		}
	}
	
	private static ScriptEngine getEngine(){
		ScriptEngineManager sfm = new ScriptEngineManager(); 
		ScriptEngine jsEngine = sfm.getEngineByName("JavaScript"); 
        if (jsEngine == null) { 
            throw new RuntimeException("找不到 JavaScript 引擎。"); 
        }
        return jsEngine;
	}

	@Override
	public void definitionValidate() {
		if(getTransitionList().size() == 0 ){
			throw new DefinitionException("判断节点必须要包含流转信息");
		}
	}
	
}
