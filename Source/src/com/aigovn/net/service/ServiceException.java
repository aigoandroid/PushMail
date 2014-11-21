package com.aigovn.net.service;

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	public static final int CODE_NETWORK_DISABLE = 1;
	public static final int CODE_IO_EXCEPTION = 2;
	public static final int CODE_UNSUPPORTED_ENCODING = 4;
	public static final int CODE_PROTOCOL_ERROR = 5;
	public static final int CODE_UNKNOWN_ERROR = 6;
	
	private int mCode;
	private String mMsg;
	
	public ServiceException(){
		super();
	}
	
	public ServiceException(int code, String msg){
		super(msg);
		mCode = code;
		mMsg = msg;
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
