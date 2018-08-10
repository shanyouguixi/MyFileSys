package com.myFileSys.user;

import java.sql.SQLException;
import java.util.List;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.IAtom;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.myFileSys.common.model.User;
import com.myFileSys.files.MemuService;
import com.myFileSys.group.GroupService;

public class UserService {
	public static UserService me = new UserService();
	User dao = new User().dao();
	
	public List<User> getUserList(){
		List<User> list = dao.find("select id,name,age,headImg,userType,login from t_user");
		return list;
	}
	public User getUser(int userId){
		return dao.findById(userId);
	}
	
	@Before(Tx.class)
	public void edit(int userId, String name, int login,int userType,String groupIds,String memuIds) {
		User user = new User();
		boolean flag1 = user.set("id", userId).set("name", name).set("login", login).set("userType", userType).update();
		boolean flag2 = MemuService.me.updateMemuId(userId, memuIds, 2);
		boolean flag3 = GroupService.me.updateGroupId(userId, groupIds, 2);
		/*Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				return flag1&&flag2&&flag3;
			}
		});*/
	}
	@Before(Tx.class)
	public void add(String password, String name, int login,int userType,String groupIds,String memuIds) {
		User user = new User();
		user.set("name", name).set("password", password).set("login", login).set("userType", userType).save();
		int userId = user.getId();
		boolean flag2 = MemuService.me.updateMemuId(userId, memuIds, 2);
		boolean flag3 = GroupService.me.updateGroupId(userId, groupIds, 2);
		/*Db.tx(new IAtom() {
			@Override
			public boolean run() throws SQLException {
				return flag1&&flag2&&flag3;
			}
		});*/
	}
}
