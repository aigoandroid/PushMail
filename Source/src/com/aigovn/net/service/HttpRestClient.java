package com.aigovn.net.service;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpRestClient {
	
	public static final String TAG = "API";
	public static final String SCHEME_HTTPS = "https";
	public static final String SSL_PROTOCOL_TLS = "TLS";
	public static final int DEFAULT_TIMEOUT = 30000; //30s
	
	private String mHttpsScheme;
	private String mSSLProtocol;
	private int mSSLPort;
	private int mTimeOut;
	
	private static HttpRestClient mInstance;
	
	private HttpRestClient(){
		mHttpsScheme = SCHEME_HTTPS;
		mSSLProtocol = SSL_PROTOCOL_TLS;
		mSSLPort = 443;
		mInstance = new HttpRestClient();
		mTimeOut = DEFAULT_TIMEOUT;
	}
	
	public int getTimeOut(){
		return mTimeOut;
	}
	
	public void setTimeOut(int timeoutInMili){
		mTimeOut = timeoutInMili;
	}
	
	public HttpClient httpsClient(HttpClient client) {
		try {
			X509TrustManager tm = new X509TrustManager() {

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType)
						throws java.security.cert.CertificateException {
					// TODO Auto-generated method stub

				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType)
						throws java.security.cert.CertificateException {
					// TODO Auto-generated method stub

				}
			};
			SSLContext ctx = SSLContext.getInstance(mSSLProtocol);
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new MySSLSocketFactory(ctx);
			ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = client.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme(mHttpsScheme, ssf, mSSLPort));
			return new DefaultHttpClient(ccm, client.getParams());
		}
		catch (Exception ex) {
			return null;
		}
	}
	
	public String getFromUrl(String url) throws HttpServiceException {
		return getFromUrl(url, mTimeOut);
	}
	
	public String getFromUrl(String url, int timeOut) throws HttpServiceException {
		String result = "";

		if (url != null) {
			HttpClient client = null;
			if (isHttps(url)) {
				client = httpsClient(new DefaultHttpClient());
			}
			else {
				client = new DefaultHttpClient();
			}
			HttpGet httpGet = new HttpGet(url);
			HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, timeOut);
			HttpConnectionParams.setSoTimeout(httpParams, timeOut);
			httpGet.setParams(httpParams);
			try {
				HttpResponse response = client.execute(httpGet);
				switch (response.getStatusLine().getStatusCode()) {
					case 400:
						throw new HttpServiceException(HttpServiceException.CODE_BAD_REQUEST, "");
					case 404:
						throw new HttpServiceException(HttpServiceException.CODE_NOT_FOUND, "");
					case 405:
						throw new HttpServiceException(HttpServiceException.CODE_METHOD_NOT_ALLOWED, "");
					case 500:
						throw new HttpServiceException(HttpServiceException.CODE_INTERNAL_SERVER_ERROR, "");
					case 408:
						throw new HttpServiceException(HttpServiceException.CODE_REQUEST_TIMEOUT, "");
					case 598:
						throw new HttpServiceException(HttpServiceException.CODE_NETWORK_READ_TIMEOUT_ERROR, "");
					case 599:
						throw new HttpServiceException(HttpServiceException.CODE_NETWORK_CONNECT_TIMEOUT_ERROR, "");
					case 502:
						throw new HttpServiceException(HttpServiceException.CODE_BAD_GATEWAY, "");
					case 503:
						throw new HttpServiceException(HttpServiceException.CODE_SERVICE_UNAVAILABLE, "");
					case 504:
						throw new HttpServiceException(HttpServiceException.CODE_GATEWAY_TIMEOUT, "");
				}
				result = EntityUtils.toString(response.getEntity(), "UTF-8").trim();
				Log.d(TAG, "StatusCode = " + response.getStatusLine().getStatusCode());
			}
			catch (IOException e) {
				Log.d(TAG, "StatusCode = IOException");
				throw new HttpServiceException(HttpServiceException.CODE_IO_EXCEPTION, e.getMessage());
			}
			finally {
				client.getConnectionManager().shutdown();
				Log.d(TAG, "API = " + httpGet.getURI());
				Log.d(TAG, "Result = " + result);
				Log.d(TAG, "--------------------------");
			}
		}

		return result;
	}
	
	public String postToURL(String url, List<NameValuePair> params)
			throws HttpServiceException {
		return postToURL(url, params, mTimeOut);
	}
	
	public String postToURL(String url, List<NameValuePair> params, int timeOut)
			throws HttpServiceException {
		String result = "";

		if (url != null) {
			HttpClient client = null;
			if (isHttps(url)) {
				client = httpsClient(new DefaultHttpClient());
			}
			else {
				client = new DefaultHttpClient();
			}
			try {
				HttpPost httpPost = new HttpPost(url);
				httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
				httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
				HttpParams httpParams = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(httpParams, timeOut);
				HttpConnectionParams.setSoTimeout(httpParams, timeOut);
				httpPost.setParams(httpParams);
				HttpResponse response = client.execute(httpPost);
				switch (response.getStatusLine().getStatusCode()) {
					case 400:
						throw new HttpServiceException(HttpServiceException.CODE_BAD_REQUEST, "");
					case 404:
						throw new HttpServiceException(HttpServiceException.CODE_NOT_FOUND, "");
					case 405:
						throw new HttpServiceException(HttpServiceException.CODE_METHOD_NOT_ALLOWED, "");
					case 500:
						throw new HttpServiceException(HttpServiceException.CODE_INTERNAL_SERVER_ERROR, "");
					case 408:
						throw new HttpServiceException(HttpServiceException.CODE_REQUEST_TIMEOUT, "");
					case 598:
						throw new HttpServiceException(HttpServiceException.CODE_NETWORK_READ_TIMEOUT_ERROR, "");
					case 599:
						throw new HttpServiceException(HttpServiceException.CODE_NETWORK_CONNECT_TIMEOUT_ERROR, "");
					case 502:
						throw new HttpServiceException(HttpServiceException.CODE_BAD_GATEWAY, "");
					case 503:
						throw new HttpServiceException(HttpServiceException.CODE_SERVICE_UNAVAILABLE, "");
					case 504:
						throw new HttpServiceException(HttpServiceException.CODE_GATEWAY_TIMEOUT, "");
				}
				result = EntityUtils.toString(response.getEntity()).trim();
				Log.d(TAG, "StatusCode = " + response.getStatusLine().getStatusCode());
			}
			catch (IOException e) {
				Log.d(TAG, "StatusCode = IOException");
				throw new HttpServiceException(HttpServiceException.CODE_IO_EXCEPTION, e.getMessage());
				//
			}
			finally {
				client.getConnectionManager().shutdown();
				Log.d(TAG, "API = " + url);
				Log.d(TAG, "Result = " + result);
				Log.d(TAG, "--------------------------");
			}
		}

		return result;
	}
	
	public static HttpRestClient getInstance(){
		if(mInstance != null)
			return mInstance;
		
		mInstance = new HttpRestClient();
		return mInstance;
	}
	
	public static boolean isHttps(String url){
		Matcher matcher = Pattern.compile("^https?://").matcher(url);
		return matcher.find();
	}
	
	public class MySSLSocketFactory extends SSLSocketFactory {
		SSLContext sslContext = SSLContext.getInstance("TLS");

		public MySSLSocketFactory(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException,
				KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new X509TrustManager() {
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType)
						throws java.security.cert.CertificateException {
					// TODO Auto-generated method stub

				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType)
						throws java.security.cert.CertificateException {
					// TODO Auto-generated method stub

				}
			};

			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		public MySSLSocketFactory(SSLContext context) throws KeyManagementException, NoSuchAlgorithmException,
				KeyStoreException, UnrecoverableKeyException {
			super(null);
			sslContext = context;
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException,
				UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	};
	
}
