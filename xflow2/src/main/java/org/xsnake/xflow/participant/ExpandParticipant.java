package org.xsnake.xflow.participant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.xsnake.common.utils.HttpUtil;
import org.xsnake.common.utils.StringUtil;
import org.xsnake.xflow.api.DefinitionException;
import org.xsnake.xflow.api.Participant;
import org.xsnake.xflow.api.XflowException;
import org.xsnake.xflow.core.Activity;
import org.xsnake.xflow.core.ParticipantHandle;
import org.xsnake.xflow.core.ProcessInstanceContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ExpandParticipant extends ParticipantHandle {

	Activity activity;
	
	String url;
	
	public ExpandParticipant(Activity activity, Element participantElement) {
		this.activity = activity;
		this.url = participantElement.elementText("url");
		if(url == null){
			url = participantElement.attributeValue("url");
		}
		if(StringUtil.isEmpty(url)){
			throw new DefinitionException("扩展参与者必须指定服务URL地址");
		}
	}
	
	@Override
	public List<Participant> findParticipantList(ProcessInstanceContext context) {
		String _url = null;
		try {
			_url = StringUtil.getTemplateString(url, context.getContextMap());
		} catch (Exception e) {
			throw new XflowException("参与者接口服务设置错误:"+e.getMessage());
		}
		List<Participant> list = new ArrayList<Participant>();
		String str = null;
		try {
			str = HttpUtil.get(_url);
		} catch (IOException e) {
			return list;
		}
		
		if(StringUtil.isEmpty(str)){
			return list;
		}
		
		JSONObject result = (JSONObject)JSON.parse(str);
		JSONArray ps = (JSONArray)result.get("message");
		
		for(int i=0;i<ps.size();i++){
			JSONObject p = ps.getJSONObject(i);
			if(p !=null){
				String id = (String)p.get("id");
				String name = (String)p.get("name");
				String type = (String)p.get("type");
				list.add(new Participant(id, name, type));
			}
		}
		return list;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
