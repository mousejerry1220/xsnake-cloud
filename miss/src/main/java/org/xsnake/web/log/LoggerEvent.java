package org.xsnake.web.log;

import java.util.Date;

import org.springframework.context.ApplicationEvent;

public class LoggerEvent extends ApplicationEvent{

	public LoggerEvent(Object source) {
		super(source);
	}

	private static final long serialVersionUID = 1L;

	private Date date;
	
	private String ip;
	
	private String moudle;
	
	private String notes;
	
	private String argsJson;

	private String url;
	
	private String userId;
	
	private String username;
	
	private String positionId;
	
	private String positionName;
	
	private String orgId;
	
	private String orgName;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMoudle() {
		return moudle;
	}

	public void setMoudle(String moudle) {
		this.moudle = moudle;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getArgsJson() {
		return argsJson;
	}

	public void setArgsJson(String argsJson) {
		this.argsJson = argsJson;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	
}
