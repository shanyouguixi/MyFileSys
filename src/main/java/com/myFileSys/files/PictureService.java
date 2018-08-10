package com.myFileSys.files;

import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.myFileSys.common.model.Music;
import com.myFileSys.common.model.Picture;
import com.myFileSys.common.utils.FilesUtils;

public class PictureService {
	public static PictureService me = new PictureService();
	Picture dao = new Picture().dao();
	
	public List<Picture> getPicture(int userId,int groupId){
		return dao.find(" select a.id,a.url,a.status,a.name,a.groupId,a.type from t_picture a where a.groupId=? and userId=? and status=1",groupId,userId);
	}
	
	public void save(int userId,String url,int groupId,String name,String type){
		Picture m = new Picture();
		m.set("userId", userId)
		.set("url", url)
		.set("groupId", groupId)
		.set("name", name)
		.set("type", type)
		.save();
	}
	public Picture getById(int id){
		return dao.findById(id);
	}
	
	public Page<Picture> paginate(int pageNumber, int pageSize,int groupId) {
		return dao.paginate(pageNumber, pageSize, "select a.id,a.url,a.status,a.name,a.groupId,a.type",
				"from t_picture a where a.groupId=? and a.status=1 order by a.id asc",groupId);
	}
	
	public boolean deleteFile(int id){
		Picture picture = dao.findById(id);
		if (picture!=null&&picture.getUrl()!=null ) {
			FilesUtils.deleteFile(picture.getUrl());
		}
		return dao.deleteById(id);
	}
}
