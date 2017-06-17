package org.xsnake.xflow.api;

import java.io.Serializable;

/**
 * 参与者是通过松耦合方式与业务系统集成，工作流不用关心具体的参与者是谁。
 * 业务系统可以规定参与者是具体人、岗位、组织、群组等
 * @author Administrator
 *
 */
public class Participant implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 参与者ID
	 */
	String id;
	/**
	 * 参与者名称
	 */
	String name;
	/**
	 * 参与者类型
	 */
	String type;
	
	public Participant(String id,String name,String type){
		this.id = id;
		this.name=name;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Participant)){
			return false;
		}
		Participant t = (Participant)obj;
		if( (this.getId() == t.getId() || this.getId().equals(t.getId()) ) &&  
			(this.getName() == t.getName() || this.getName().equals(t.getName()))  &&  
			(this.getType() == t.getType() || this.getType().equals(t.getType()))
			)
			return true;
		
		return false;
	}

}
