package com.aigovn.pushmail.service;

import com.aigovn.pushmail.Constant;
import com.aigovn.pushmail.PushMailApplication;

public class APIURL {
	
	public static final String API_REG_USER = "_reg.php";
	public static final String API_REG_TOKEN = "_regtoken.php";
	public static final String API_GET_MAIL = "get_mail.php";
	public static final String API_SEND_MAIL = "send_mail.php";
	public static final String API_CHECK_STATUS = "chkst.php";
	public static final String API_LOG_STARTING = "starting.php";
	public static final String API_FIRST_STARTING = "_appstart.php";
	
	public static final String URL_DEV = "http://mail.mailp.net/";
	public static final String URL_PROD = "http://dev.mailp.net/";
	
	public static String getApiUrl(String api){
		String url = URL_DEV;
		if(PushMailApplication.serviceEnvironment == Constant.SERVICE_PRO)
			url = URL_PROD;
		return url + api;
	}

}
