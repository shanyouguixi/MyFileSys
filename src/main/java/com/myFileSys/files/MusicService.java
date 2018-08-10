package com.myFileSys.files;

import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.myFileSys.common.model.Document;
import com.myFileSys.common.model.Movie;
import com.myFileSys.common.model.Music;
import com.myFileSys.common.utils.FilesUtils;

public class MusicService {
	public static MusicService me = new MusicService();
	Music dao = new Music().dao();
	public List<Music> getMusic(int userId,int groupId){
		return dao.find(" select a.id,a.url,a.status,a.name,a.groupId,a.type from t_music a where a.groupId=? and userId=? and status=1",
				groupId,userId);
	}
	
	public void save(int userId,String url,int groupId,String name,String type){
		Music m = new Music();
		m.set("userId", userId)
		.set("url", url)
		.set("groupId", groupId)
		.set("name", name)
		.set("type", type)
		.save();
	}
	public Music getById(int id){
		return dao.findById(id);
	}
	public Page<Music> paginate(int pageNumber, int pageSize,int groupId) {
		return dao.paginate(pageNumber, pageSize, "select a.id,a.url,a.status,a.name,a.groupId,a.type",
				"from t_music a where a.groupId=? and a.status=1 order by a.id asc",groupId);
	}
	public boolean deleteFile(int id){
		Music music = dao.findById(id);
		if (music!=null&&music.getUrl()!=null ) {
			FilesUtils.deleteFile(music.getUrl());
		}
		return dao.deleteById(id);
	}
}
