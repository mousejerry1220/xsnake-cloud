package org.xsnake.web.session;

public interface SessionSupport {

	void setSessionAttribute(String key , Object obj);
	
	void sessionInvalidate();
	
	Object getSessionAttribute(String key);
	
	void removeAttribute(String key);
	
}
