package com.myFileSys.group;

import java.util.List;

import com.jfinal.plugin.activerecord.Record;
import com.myFileSys.common.model.Group;
import com.myFileSys.common.model.GroupRule;
import com.myFileSys.common.model.MemuRule;

public class GroupService {
	public static GroupService me = new GroupService();
	Group dao = new Group().dao();
	GroupRule r_dao = new GroupRule().dao();
	
	public boolean addGroup(int memuId,String groupName,int userId){
		GroupRule rule = r_dao.findFirst("select id,groupId,userId from t_group_rule where userId=?",userId);
		int maxId = dao.getMaxId();
		int id = maxId+1;
		if( rule!=null ) {
			GroupRule r = new GroupRule();
			r.set("id", rule.getId());
			r.set("groupId", rule.getGroupId()+","+id);
			r.update();
		} else {
			GroupRule r = new GroupRule();
			r.set("userId", userId);
			r.setGroupId(String.valueOf(id));
			r.save();
		}
		Group group = new Group();
		group.set("id", id);
		group.set("memuId", memuId);
		group.set("name", groupName);
		return group.save();
	}
	
	public List<Group> getGroups(){
		return dao.find("select * from t_group");
	}
	public GroupRule getRule(int userId){
		return r_dao.findFirst("select id,groupId,userId from t_group_rule where userId=?",userId);
	}
	public GroupRule getRule(){
		return r_dao.findFirst("select id,groupId,userId from t_group_rule");
	}
	
	/**
	 * 
	 * @param userId 用户id
	 * @param groupId 分组id
	 * @param type 1删除2修改
	 */
	public boolean updateGroupId(int userId,String groupId,int type){
		GroupRule rule = getRule(userId);
		GroupRule r = new GroupRule();
		if(rule==null){
			return r.set("groupId", groupId).set("userId", userId).save();
		}
		String groupIds = rule.getGroupId();
		if(type==1){
			//删除分组
			if(groupIds!=null&&groupIds.length()>2){
				groupIds.replace(","+groupId, "");
			} else {
				groupIds.replace(groupId, "");
			}
			return r.set("id", rule.getId()).set("groupId", groupIds).update();
		} else {
			//修改分组
			return r.set("id", rule.getId()).set("groupId", groupId).update();
		}
	}
}
