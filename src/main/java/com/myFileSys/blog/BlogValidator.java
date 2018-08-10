package com.myFileSys.blog;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
import com.myFileSys.common.model.Blog;

/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法
 * 详见 JFinal 俱乐部: http://jfinal.com/club
 * 
 * BlogValidator.
 */
public class BlogValidator extends Validator {
	//校验电话号码正则表达式
		private static final String phonePattern="\\b(0(\\d{2,3})-\\d{6,9})\\b";
		//校验手机号码正则表达式
		private static final String mobilePattern="\\b(1[3,5,7,8,9]\\d{9})\\b";
		//校验电话手机号码正则表达式
		private static final String phoneMobilePattern="\\b((1[3,5,7,8,9]\\d{9})|(0(\\d{2,3})-\\d{6,9}))\\b";
		
		/**
		 * 校验电话号码
		 * @param field 校验字段
		 * @param errorKey
		 * @param errorMsg
		 */
		protected void validatePhonePattern(String field,String errorKey,String errorMsg){
			validateRegex(field, phonePattern, false, errorKey, errorMsg);
		}
		
		/**
		 * 验证手机号码
		 * @param field
		 * @param errorKey
		 * @param errorMsg
		 */
		protected void validateMobilePattern(String field,String errorKey,String errorMsg){
			validateRegex(field, mobilePattern, false, errorKey, errorMsg);
		}
		
		/**
		 * 验证电话手机号码
		 * @param field
		 * @param errorKey
		 * @param errorMsg
		 */
		protected void validatePhoneMobilePattern(String field,String errorKey,String errorMsg){
			validateRegex(field, phoneMobilePattern, false, errorKey, errorMsg);
		}

	
	protected void validate(Controller controller) {
		validateRequiredString("blog.title", "titleMsg", "请输入Blog标题!");
		validateRequiredString("blog.content", "contentMsg", "请输入Blog内容!");
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(Blog.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/blog/save"))
			controller.render("add.html");
		else if (actionKey.equals("/blog/update"))
			controller.render("edit.html");
	}
}
