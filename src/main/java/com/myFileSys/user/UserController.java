package com.myFileSys.user;

import java.util.List;

import javax.websocket.Session;

import com.jfinal.core.Controller;
import com.myFileSys.common.model.Group;
import com.myFileSys.common.model.GroupRule;
import com.myFileSys.common.model.Memu;
import com.myFileSys.common.model.MemuRule;
import com.myFileSys.common.model.User;
import com.myFileSys.common.utils.ToString;
import com.myFileSys.files.MemuService;
import com.myFileSys.group.GroupService;

public class UserController extends Controller{

	public void getUserList(){
		int userId = getParaToInt(0);
		User user = UserService.me.getUser(userId);
		List<User> userList = UserService.me.getUserList();
		setAttr("userList", userList);
		setAttr("user", user);
		render("/user/user.html");
	}
	
	public void form(){
		int userId = getParaToInt(0);
		List<Memu> memus = MemuService.me.getMemus();
		List<Group> groups = GroupService.me.getGroups();
		User user = UserService.me.getUser(userId);
		GroupRule gRule = GroupService.me.getRule(userId);
		if( gRule!=null&&gRule.getGroupId()!=null&&!gRule.getGroupId().equals("")){
			System.out.println(gRule.getGroupId());
			setAttr("gRule", gRule.getGroupId());
		} else {
			setAttr("gRule", "");
		}
		MemuRule mRule = MemuService.me.getMemuRule(userId);
		if( mRule!=null&&mRule.getMemuId()!=null&&!mRule.getMemuId().equals("")){
			setAttr("mRule", mRule.getMemuId());
		} else {
			setAttr("mRule", "");
		}
		setAttr("user", user);
		setAttr("memus", memus);
		setAttr("groups", groups);
		render("/user/form.html");
	}
	
	public void edit(){
		int userId = getParaToInt("id");
		Integer[] groupIds = getParaValuesToInt("groupId");
		Integer[] memuIds = getParaValuesToInt("memuId");
		String name = getPara("name");
		int login = getParaToInt("login");
		int userType = getParaToInt("userType");
		String groupIdStr = ToString.toString(groupIds);
		String memuIdStr = ToString.toString(memuIds);
		UserService userService = enhance(UserService.class);
		userService.edit(userId, name, login, userType, groupIdStr, memuIdStr);
		redirect("/user/getUserList/"+userId);
	}
	
	public void addForm(){
		int userId = getParaToInt(0);
		User user = (User) getRequest().getSession().getAttribute("user");
		List<Memu> memus = MemuService.me.getMemus(userId);
		List<Group> groups = GroupService.me.getGroups();
		GroupRule gRule = GroupService.me.getRule();
		if( gRule!=null&&gRule.getGroupId()!=null&&!gRule.getGroupId().equals("")){
			System.out.println(gRule.getGroupId());
			setAttr("gRule", gRule.getGroupId());
		} else {
			setAttr("gRule", "");
		}
		MemuRule mRule = MemuService.me.getMemuRule();
		if( mRule!=null&&mRule.getMemuId()!=null&&!mRule.getMemuId().equals("")){
			setAttr("mRule", mRule.getMemuId());
		} else {
			setAttr("mRule", "");
		}
		setAttr("user", user);
		setAttr("memus", memus);
		setAttr("groups", groups);
		render("/user/form.html");
		if(userId==1){
			render("/user/add.html");
		}
	}
	
	public void add(){
		Integer[] groupIds = getParaValuesToInt("groupId");
		Integer[] memuIds = getParaValuesToInt("memuId");
		String name = getPara("name");
		String password = getPara("password");
		int login = getParaToInt("login");
		int userType = getParaToInt("userType");
		String groupIdStr = ToString.toString(groupIds);
		String memuIdStr = ToString.toString(memuIds);
		UserService userService = enhance(UserService.class);
		userService.add(password, name, login, userType, groupIdStr, memuIdStr);
		redirect("/user/getUserList/"+1);
	}
}
