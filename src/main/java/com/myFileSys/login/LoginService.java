package com.myFileSys.login;

import com.myFileSys.common.model.User;

public class LoginService {
	public static final LoginService me = new LoginService();
	
	private User dao = new User().dao();
	
	public User login(String id,String password){
		return dao.findFirst("select id,name,age,login,userType,headImg from t_user where id=? and password=? and login=1",id,password);
	}
	
}
