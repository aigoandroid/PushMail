package com.aigovn.pushmail.businesslogic;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.aigovn.fragments.SplashFragment;
import com.aigovn.pushmail.service.PushMailService;

public class SplashBus {
	
	private SplashFragment mFragment;
	private PushMailService mService;
	
	public SplashBus(SplashFragment fragment){
		mFragment = fragment;
		mService = PushMailService.getInstance();
	}
	
	public void doLogStartingApp(){
		
	}
	
	public void doRegisterUser(){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair(name, value))
	}
}
