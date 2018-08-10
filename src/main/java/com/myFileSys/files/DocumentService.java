package com.myFileSys.files;

import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.myFileSys.common.model.Document;
import com.myFileSys.common.model.Movie;
import com.myFileSys.common.model.Picture;
import com.myFileSys.common.utils.FilesUtils;
import com.myFileSys.group.GroupService;

public class DocumentService {
	public static DocumentService me = new DocumentService();
	Document dao = new Document().dao();
	
	public List<Document> getDocument(int userId,int groupId){
		return dao.find(" select a.id,a.url,a.status,a.name,a.groupId,a.type from t_document a where a.groupId=? and userId=? and status=1",groupId,userId);
	}
	
	public void save(int userId,String url,int groupId,String name,String type){
		Document m = new Document();
		m.set("userId", userId)
		.set("url", url)
		.set("groupId", groupId)
		.set("name", name)
		.set("type", type)
		.save();
	}
	public Document getById(int id){
		return dao.findById(id);
	}
	public Page<Document> paginate(int pageNumber, int pageSize,int groupId) {
		return dao.paginate(pageNumber, pageSize, "select a.id,a.url,a.status,a.name,a.groupId,a.type",
				"from t_document a where a.groupId=? and a.status=1 order by a.id asc",groupId);
	}
	public boolean deleteFile(int id){
		Document document = dao.findById(id);
		if (document!=null&&document.getUrl()!=null ) {
			FilesUtils.deleteFile(document.getUrl());
		}
		return dao.deleteById(id);
	}
}
