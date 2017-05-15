package org.xsnake.miss.api.lov.model;

import java.io.Serializable;
import java.util.List;

import org.xsnake.miss.api.lov.entity.LovGroup;
import org.xsnake.miss.api.lov.entity.LovGroupAttributes;

public class LovGroupModel implements Serializable {

	private static final long serialVersionUID = 1L;

	private LovGroup lovGroup;
	
	private List<LovGroupAttributes> attributes;

	public LovGroupModel(LovGroup lovGroup) {
		this.lovGroup = lovGroup;
	}
	
	public LovGroupModel(LovGroup lovGroup, List<LovGroupAttributes> attributes) {
		this.lovGroup = lovGroup;
		this.attributes = attributes;
	}

	public LovGroup getLovGroup() {
		return lovGroup;
	}

	public void setLovGroup(LovGroup lovGroup) {
		this.lovGroup = lovGroup;
	}

	public List<LovGroupAttributes> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<LovGroupAttributes> attributes) {
		this.attributes = attributes;
	}
	
}
