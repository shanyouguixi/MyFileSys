package com.myFileSys.sys;

import com.jfinal.config.Routes;
import com.myFileSys.blog.BlogController;
import com.myFileSys.files.FilesController;
import com.myFileSys.group.GroupController;
import com.myFileSys.index.IndexController;
import com.myFileSys.login.LoginController;
import com.myFileSys.user.UserController;

public class RoutesConfig extends Routes{

	@Override
	public void config() {
		add("/index", IndexController.class);	// 第三个参数为该Controller的视图存放路径
		add("/blog", BlogController.class);			// 第三个参数省略时默认与第一个参数值相同，在此即为 "/blog"
		add("/login", LoginController.class);
		add("/files",FilesController.class);
		add("/group",GroupController.class);
		add("/user",UserController.class);
	}

}
