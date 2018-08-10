package com.myFileSys.index;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.myFileSys.login.LoginController;
import com.myFileSys.sys.SessionManager;

public class CheckInterceptor implements Interceptor{

	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		String sessionid = controller.getCookie("SESSIONID");
		boolean flag = SessionManager.getMe().validate(sessionid);
//		if ( sessionid==null || controller.getControllerKey().equals("/login")) {
		if(!flag&&!controller.getControllerKey().equals("/login")){
			controller.setAttr("password", null);
			controller.render("/login/login.html");
			return;
		}
		inv.invoke();
	}

}
