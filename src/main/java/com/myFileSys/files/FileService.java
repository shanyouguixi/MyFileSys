package com.myFileSys.files;

import java.util.List;
import java.util.Map;

import com.jfinal.upload.UploadFile;
import com.myFileSys.common.utils.FilesUtils;

public class FileService {
	public static FileService me = new FileService();
	
	public void saveFile(String userId,List<UploadFile> files,int groupId){
		for(UploadFile item:files){
			Map<String, String> map =  FilesUtils.saveFile(item, userId);
			String url = map.get("path");
			String fileName = map.get("fileName");
			String fileType = FilesUtils.fileType(fileName);
			if( fileType.equals("video")){
				MoviceService.me.save(Integer.parseInt(userId), url, groupId, fileName,"video");
			} else if( fileType.equals("music") ) {
				MusicService.me.save(Integer.parseInt(userId), url, groupId, fileName,"music");
			} else if( fileType.equals("picture") ){
				PictureService.me.save(Integer.parseInt(userId), url, groupId, fileName,"picture");
			} else {
				DocumentService.me.save(Integer.parseInt(userId), url, groupId, fileName,"document");
			}
		}
	}
}
