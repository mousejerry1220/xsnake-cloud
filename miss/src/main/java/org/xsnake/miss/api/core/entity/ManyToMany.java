package org.xsnake.miss.api.lov.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SYS_MANY_TO_MANY")
public class ManyToMany implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "SYS_MANY_TO_MANY_ID_GENERATOR")
	@GenericGenerator(name="SYS_MANY_TO_MANY_ID_GENERATOR", strategy="uuid")
	@Column(name="ROW_ID")
	private String id;
	
	@Column(name="LEFT_MEMBER_ID")
	private String leftMemberId;
	
	@Column(name="RIGHT_MEMBER_ID")
	private String rightMemberId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLeftMemberId() {
		return leftMemberId;
	}

	public void setLeftMemberId(String leftMemberId) {
		this.leftMemberId = leftMemberId;
	}

	public String getRightMemberId() {
		return rightMemberId;
	}

	public void setRightMemberId(String rightMemberId) {
		this.rightMemberId = rightMemberId;
	}
	
}
