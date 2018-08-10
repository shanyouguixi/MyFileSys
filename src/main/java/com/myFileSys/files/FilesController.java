package com.myFileSys.files;

import java.io.File;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
import com.myFileSys.common.model.Document;
import com.myFileSys.common.model.Group;
import com.myFileSys.common.model.Memu;
import com.myFileSys.common.model.Movie;
import com.myFileSys.common.model.Music;
import com.myFileSys.common.model.Picture;
import com.myFileSys.common.model.User;
import com.myFileSys.common.utils.FilesUtils;
import com.myFileSys.group.GroupService;

public class FilesController extends Controller{

	/**
	 * 获取分组
	 */
	public void setSessionValue(String name,Object obj){
		HttpSession session =  getSession();
		session.setAttribute(name, obj);
	}
	public void getGroup(){
		int memuId = getParaToInt(0);
		int userId = getParaToInt(1);
		User user = (User) getRequest().getSession().getAttribute("user");
		List<Memu> memu =  (List<Memu>) getRequest().getSession().getAttribute("memu");
		setAttr("user", user);
		setAttr("memu", memu);
		setAttr("memuId", memuId);
		List<Group> group = MemuService.me.getGroupId(userId, memuId);
		setAttr("group", group);
		setSessionValue("group",group);
		for(Memu item:memu){
			if(item.getId()==memuId){
				setAttr("theMemu", item);
			}
		}
		render("/movie/movie.html");
	}
	/**
	 * 获取分组下的资源
	 */
	public void getMyFiles(){
		int groupId = getParaToInt(0);
		int userId = getParaToInt(1);
		int memuId = getParaToInt(2);
		User user = (User) getRequest().getSession().getAttribute("user");
		List<Memu> memu =  (List<Memu>) getRequest().getSession().getAttribute("memu");
		List<Group> group =  (List<Group>) getRequest().getSession().getAttribute("group");
		setAttr("user", user);
		setAttr("memu", memu);
		setAttr("group", group);
		setAttr("groupId", groupId);
		setAttr("memuId", memuId);
		Object obj= getSessionAttr("FileData");
		if( memuId==1 ) {
			Page<Movie> movices = MoviceService.me.paginate(getParaToInt(3, 1),10, groupId);
			setAttr("files", movices);
			render("/movie/movie.html");
			setSessionValue("memuId",memuId);
			return;
		} else if ( memuId==2 ) {
			Page<Music> musics = MusicService.me.paginate(getParaToInt(3, 1),10, groupId);
			setAttr("files", musics);
			render("/movie/movie.html");
			setSessionValue("memuId",memuId);
			return;
		} else if ( memuId==3 ){
			Page<Picture> pictures = PictureService.me.paginate(getParaToInt(3, 1),10, groupId);
			setAttr("files", pictures);
			render("/movie/movie.html");
			setSessionValue("memuId",memuId);
			return;
		} else {
			Page<Document> document = DocumentService.me.paginate(getParaToInt(3, 1),10, groupId);
			setAttr("files", document);
			render("/movie/movie.html");
			setSessionValue("memuId",memuId);
			return;
		}
	}
	
	public void uploadFiles(){
		List<UploadFile> files = getFiles();
		int groupId = getParaToInt("groupId");
		User user = (User) getRequest().getSession().getAttribute("user");
		int userId = user.getId();
		String memuId = getPara("memuId");
		FileService.me.saveFile(String.valueOf(userId), files, groupId);
		redirect("/files/getGroup/"+memuId+"-"+userId);
	}
	
	public void form(){
		int id = getParaToInt(0);
		String type = getPara(1);
		if( type.equals("video")){
			Movie movie = MoviceService.me.getById(id);
			setAttr("movie", movie);
			render("/movie/_form.html");
		} else if( type.equals("music") ) {
			Music music = MusicService.me.getById(id);
			setAttr("music", music);
			render("/music/_form.html");
		} else if( type.equals("picture") ){
			Picture picture = PictureService.me.getById(id);
			setAttr("picture", picture);
			render("/picture/_form.html");
		} else {
			Document document = DocumentService.me.getById(id);
			setAttr("document", document);
			render("/document/_form.html");
			
		}
	}
	
	public void deleteFile(){
		int id = getParaToInt(0);
		String type = getPara(1);
		String memuId = getPara(2,"1");
		String groupId = getPara(3);
		User user = (User) getRequest().getSession().getAttribute("user");
		int userId = user.getId();
		if( type.equals("video")){
			MoviceService.me.deleteFile(id);
		} else if( type.equals("music") ) {
			MusicService.me.deleteFile(id);
		} else if( type.equals("picture") ){
			PictureService.me.deleteFile(id);
		} else {
			DocumentService.me.deleteFile(id);
		}
		redirect("/files/getGroup/"+memuId+"-"+userId);
	}
}
