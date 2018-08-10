package com.myFileSys.group;

import com.jfinal.core.Controller;

public class GroupController extends Controller{

	
	public void addGroup(){
		int memuId = getParaToInt("memuId");
		int userId = getParaToInt("userId");
		String groupName = getPara("groupName");
		if( groupName!=null &&!groupName.equals("")) {
			GroupService.me.addGroup(memuId, groupName,userId);
		}
		redirect("/files/getGroup/"+memuId+"-"+userId);
	}
}
