package com.myFileSys.sys;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;

public class SessionManager extends Controller{

	private static SessionManager me = new SessionManager();
	private Map<String ,Integer> sessionMap ;
	
	public SessionManager(){
		sessionMap = new HashMap<String ,Integer>();
	}
	
	public static SessionManager getMe(){
		return me;
	}
	
	public boolean validate(String sessionId){
		Integer id = sessionMap.get(sessionId);
		if( id==null ){
			return false;
		} else {
			return true;
		}
	}
	
	public void putSession(String sessionId,Integer id) {
		sessionMap.put(sessionId, id);
	}
}
