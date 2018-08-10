package com.myFileSys.login;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jfinal.core.Controller;
import com.myFileSys.common.model.Memu;
import com.myFileSys.common.model.User;
import com.myFileSys.files.MemuService;
import com.myFileSys.sys.SessionManager;

public class LoginController extends Controller {
	private static LoginController me = new LoginController();
	LoginService service = LoginService.me;
	MemuService mService = MemuService.me;
	
	
	
	
	public void login(){
		render("/login/login.html");
	}
	public void toLogin(){
		String id = getPara("id");
		String password = getPara("password");
		User user = service.login(id, password);
		HttpServletRequest request= getRequest();
		HttpServletResponse response = getResponse();
		HttpSession session =  request.getSession();
		if(user==null){
			session.invalidate();
			setAttr("password", null);
			setAttr("msg","禁止登陆");
			render("/login/login.html");
//			render("/index/index.html");
			return;
		}else {
			String sessionId = session.getId();
			Cookie cookie  = new Cookie("SESSIONID", sessionId);
			SessionManager.getMe().putSession(sessionId, user.getId());
			cookie.setPath(request.getContextPath());
			response.addCookie(cookie);
			List<Memu> list = MemuService.me.getMemus(user.getId());
			session.setAttribute("user", user);
			session.setAttribute("memu", list);
			setAttr("user", user);
			setAttr("menu", list);
			render("/index/index.html");
			return;
		}
	}
}
