package com.myFileSys.sys;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.log.Log4jLog;

public class ErrorIntercepter implements Interceptor{
	private static final Log4jLog logger = Log4jLog.getLog(ErrorIntercepter.class);
	@Override
	public void intercept(Invocation inv) {
			try {
				inv.invoke();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(),e);
				inv.getController().render("/error/error.html");
			}
	}

}
