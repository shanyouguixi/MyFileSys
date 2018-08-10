package com.myFileSys.files;

import java.util.List;

import com.jfinal.plugin.activerecord.Page;
import com.myFileSys.common.model.Blog;
import com.myFileSys.common.model.Movie;
import com.myFileSys.common.utils.FilesUtils;

public class MoviceService {
	
	Movie dao = new Movie().dao();
	public static MoviceService me = new MoviceService();
	
	public List<Movie> getMovices(int userId,int groupId){
		return dao.find(" select a.id,a.url,a.status,a.name,a.groupId,a.type from t_movie a where a.groupId=? and userId=? and status=1",
				groupId,userId);
	}
	public Page<Movie> paginate(int pageNumber, int pageSize,int groupId) {
		return dao.paginate(pageNumber, pageSize, "select a.id,a.url,a.status,a.name,a.groupId,a.type",
				"from t_movie a where a.groupId=? and a.status=1 order by a.id asc",groupId);
	}
	
	public void save(int userId,String url,int groupId,String name,String type){
		Movie m = new Movie();
		m.set("userId", userId)
		.set("url", url)
		.set("groupId", groupId)
		.set("name", name)
		.set("type", type)
		.save();
	}
	public Movie getById(int id){
		return dao.findById(id);
	}
	
	public boolean deleteFile(int id){
		Movie movie = dao.findById(id);
		if (movie!=null&&movie.getUrl()!=null ) {
			FilesUtils.deleteFile(movie.getUrl());
		}
		return movie.deleteById(id);
	}
}
