package com.aigovn.net.service;

public class HttpServiceException extends ServiceException {

	private static final long serialVersionUID = 1L;
	
	public static final int CODE_BAD_REQUEST = 400;
	public static final int CODE_NOT_FOUND = 404;
	public static final int CODE_METHOD_NOT_ALLOWED = 405;
	public static final int CODE_INTERNAL_SERVER_ERROR = 500;
	public static final int CODE_REQUEST_TIMEOUT = 408;
	public static final int CODE_NETWORK_READ_TIMEOUT_ERROR = 598;
	public static final int CODE_NETWORK_CONNECT_TIMEOUT_ERROR = 599;
	public static final int CODE_BAD_GATEWAY = 502;
	public static final int CODE_SERVICE_UNAVAILABLE = 503;
	public static final int CODE_GATEWAY_TIMEOUT = 504;
	
	public HttpServiceException(){
		super();
	}
	
	public HttpServiceException(int code, String msg){
		super(code, msg);
	}

}
