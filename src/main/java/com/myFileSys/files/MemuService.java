package com.myFileSys.files;

import java.util.ArrayList;
import java.util.List;

import com.myFileSys.common.model.Group;
import com.myFileSys.common.model.GroupRule;
import com.myFileSys.common.model.Memu;
import com.myFileSys.common.model.MemuRule;

public class MemuService {
	public static MemuService me = new MemuService();
	Memu dao = new Memu().dao();
	MemuRule r_dao = new MemuRule().dao();
	Group g_dao = new Group().dao();
	GroupRule gr_dao = new GroupRule().dao();
	public List<Memu> getList(){
		return dao.find("select id,name,status from t_memu where status=1");
	}
	public List<String> getByUserId(int userId){
		List<String> idList = new ArrayList<String>();
		MemuRule rule = r_dao.findFirst("select id,memuId,userId from t_memu_rule where userId=?",userId);
		String memuIds = rule.getMemuId();
		if ( memuIds!=null&&!memuIds.equals("") ) {
			String[] id = memuIds.split(",");
			for( String item:id ) {
				idList.add(item);
			}
		}
		return idList; 
	}
	public List<Group> getGroupId(int userId,int memuId){
		List<Group> groups = new ArrayList<Group>();
		List<Group> group = g_dao.find("select a.id,a.name,a.status,a.memuId from t_group a "
				+ "left join t_memu b on a.memuId = b.id where a.memuId=? and a.status=1",memuId);
		GroupRule gRule = gr_dao.findFirst("select id,groupId,userId from t_group_rule where userId=?",userId);
		if ( gRule!=null&&!gRule.equals("")) {
			String[] id = gRule.getGroupId().split(",");
			for(Group g:group ){
				for( String item:id ) {
					if( g.getId().equals(Integer.parseInt(item)) ){
						groups.add(g);
					}
				}
			}
		}
		return groups;
	}
	
	public List<Memu> getMemus(int userId){
		List<Memu> memu = new ArrayList<Memu>();
		List<Memu> memus = dao.find("select a.id,a.name,a.status from t_memu a ");
		MemuRule r = r_dao.findFirst("select id, memuId,userId from t_memu_rule where userId=?",userId);
		if ( r!=null&&!r.getMemuId().equals("")) {
			String[] id = r.getMemuId().split(",");
			for(Memu g:memus ){
				for( String item:id ) {
					if( g.getId().equals(Integer.parseInt(item)) ){
						memu.add(g);
					}
				}
			}
		}
		return memu;
	}
	public List<Memu> getMemus(){
		return dao.find("select a.id,a.name,a.status from t_memu a ");
	}
	public MemuRule getMemuRule(int userId){
		return r_dao.findFirst("select id,memuId,userId from t_memu_rule where userId=?",userId);
	}
	public MemuRule getMemuRule(){
		return r_dao.findFirst("select id,memuId,userId from t_memu_rule");
	}
	
	
	public boolean updateMemuId(int userId,String memuId,int type){
		MemuRule rule = getMemuRule(userId);
		MemuRule r = new MemuRule();
		if(rule==null){
			return r.set("memuId", memuId).set("userId", userId).save();
		}
		String memuIds = rule.getMemuId();
		if(type==1){
			if(memuIds!=null&&memuIds.length()>2){
				memuIds.replace(","+memuId, "");
			} else {
				memuIds.replace(memuId, "");
			}
			return r.set("id", rule.getId()).set("memuId", memuIds).update();
		} else {
			return r.set("id", rule.getId()).set("memuId", memuId).update();
		}
	}
}
