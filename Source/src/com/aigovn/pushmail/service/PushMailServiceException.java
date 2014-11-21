package com.aigovn.pushmail.service;

import com.aigovn.net.service.ServiceException;

public class PushMailServiceException extends Exception {
	private static final long serialVersionUID = 592193852020179742L;
	
	public static final int ERR_ENCRYPTION = -1;
	
	private int mCode;
	private String mMsg;
	
	public PushMailServiceException(){
		super();
	}
	
	public PushMailServiceException(int code, String msg){
		super(msg);
		mCode = code;
		mMsg = msg;
	}
	
	public PushMailServiceException(ServiceException ex){
		super(ex.getMessage());
		mCode = ex.getCode();
		mMsg = ex.getMessage();
	}

	public int getCode() {
		return mCode;
	}

	public void setCode(int mCode) {
		this.mCode = mCode;
	}

	public String getMsg() {
		return mMsg;
	}

	public void setMsg(String mMsg) {
		this.mMsg = mMsg;
	}
}
