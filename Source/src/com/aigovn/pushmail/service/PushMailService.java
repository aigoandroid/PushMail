package com.aigovn.pushmail.service;

import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.util.Base64;

import com.aigovn.net.service.HttpRestClient;
import com.aigovn.net.service.HttpServiceException;
import com.aigovn.pushmail.Constant;

public class PushMailService {
	
	public static final int MAX_ENCRYPTION_KEY = 10;
	public static final String[] ENCRYPTION_KEYS = new String[]{
		"jIJSG*9T3Csgu7rnv*-8SMCYmb%p%M8D"
		, "ssH2=l4xJyeCy.NO$rDdHk&1$luox?gc"
		, "wxLM9SaL*B*w.#WRd_f5Ui5pfl1K97tg"
		, "znn=.fm65i-2zw-dgwoRjU#H#XZ1wrtb"
		, "u0KIe#?Lp#4PM#keTUdX1Q82u4e?85DX"
		, "X!Dvls9E?PGTG418gT*-!Tdyp7n&LI=f"
		, "6-XNIXYDqz0_8PjJ.=OLcCMSu?qUAuJS"
		, "rV1CP#XHb_voGB?y0#y7OsFU_#=ekoN#"
		, "v0680djCoOoAV9t7G+xZ!L4fBWUfIdsT"
		, "Ebn%zNDVuH0xitjFWs8OJO2a2eBoqlSi"
	};
	public static final String KEY_ENCRYPTION_KEY = "kid";
	public static final String KEY_ENCRYPTION_DATA = "reqd";
	
	private HttpRestClient mHttpRestClient;
	
	private static PushMailService mInstance;
	
	private PushMailService(){
		mHttpRestClient = HttpRestClient.getInstance();
		mHttpRestClient.setTimeOut(Constant.DEFAULT_REQUEST_TIMEOUT);
	}
	
	public String getServiceNormal(String url) throws PushMailServiceException{
		try {
			return mHttpRestClient.getFromUrl(url);
		} catch (HttpServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new PushMailServiceException(e);
		}
	}
	
	public String postServiceNormal(String url, List<NameValuePair> params) throws PushMailServiceException{
		try {
			return mHttpRestClient.postToURL(url, params);
		} catch (HttpServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new PushMailServiceException(e);
		}
	}
	
	public String postServiceEncrypt(String url, List<NameValuePair> params, String data) throws PushMailServiceException{
		int randomKeyIndex = Utils.getRandom(MAX_ENCRYPTION_KEY);
		String encryptionKey = ENCRYPTION_KEYS[randomKeyIndex];
		byte[] plainRequestData = data.getBytes();
		byte[] encryptedRequestData = Utils.encryptData(encryptionKey, plainRequestData);
		if(encryptedRequestData == null)
			throw new PushMailServiceException(PushMailServiceException.ERR_ENCRYPTION, "");
		params.add(new BasicNameValuePair(KEY_ENCRYPTION_KEY, encryptionKey));
		params.add(new BasicNameValuePair(KEY_ENCRYPTION_DATA, Base64.encodeToString(encryptedRequestData, Base64.NO_WRAP)));
		try {
			String encryptedResponseString = mHttpRestClient.postToURL(url, params);
			byte[] encryptedResponseData = Base64.decode(encryptedResponseString, Base64.NO_WRAP);
			byte[] plainResponseData = Utils.decryptData(encryptionKey, encryptedResponseData);
			String plainResponseString = "";
			if(plainResponseData != null){
				plainResponseString = new String(plainResponseData);
			}
			return plainResponseString;
		} catch (HttpServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new PushMailServiceException(e);
		}
	}
	
	public static PushMailService getInstance(){
		if(mInstance != null)
			return mInstance;
		
		mInstance = new PushMailService();
		return mInstance;
	}
}
