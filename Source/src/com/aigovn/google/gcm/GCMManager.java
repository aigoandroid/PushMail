package com.aigovn.google.gcm;

import java.io.IOException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GCMManager {

	private static final String GCM_PREFERENCE = "com.aigovn.google.gcm.preferences";
	private static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";

	private static final String GCM_PERMISSION = "com.google.android.c2dm.permission.SEND";
	private static final String GCM_INTENT_ACTION = "com.google.android.c2dm.intent.RECEIVE";

	private Context mContext;
	private IGCMListener mListener;
	private GoogleCloudMessaging mGcm;
	private GcmBroadcastReceiver mBroadcastReceiver;

	private static GCMManager mInstance;

	private GCMManager(Context context, IGCMListener listener) {
		mContext = context;
		mListener = listener;
		mGcm = GoogleCloudMessaging.getInstance(mContext);

		// register broadcast receiver
		mBroadcastReceiver = new GcmBroadcastReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(GCM_INTENT_ACTION);
		String packageName = context.getApplicationContext().getPackageName();
		intentFilter.addCategory(packageName);
		context.registerReceiver(mBroadcastReceiver, intentFilter, GCM_PERMISSION, null);
	}

	public void registerGCM(String senderID) {
		String deviceToken = getRegistrationId();
		if (deviceToken != null && !deviceToken.isEmpty())
			mListener.onGCMRegistrationSuccess(deviceToken);
		registerGCMInBackground(senderID);
	}

	private void registerGCMInBackground(final String senderID) {
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				try {
					if (mGcm == null) {
						mGcm = GoogleCloudMessaging.getInstance(mContext);
					}
					String regid = mGcm.register(senderID);
					mListener.onGCMRegistrationSuccess(regid);
					storeRegistrationId(regid);
				}
				catch (IOException ex) {
					String msg = "Error :" + ex.getMessage();
					mListener.onGCMRegistrationFailed(msg);
				}
				return null;
			}
		}.execute();
	}

	public String getRegistrationId() {
		final SharedPreferences prefs = getGCMPreferences();
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if (registrationId.isEmpty()) {
			return "";
		}
		// Check if app was updated; if so, it must clear the registration ID
		// since the existing regID is not guaranteed to work with the new
		// app version.
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(mContext);
		if (registeredVersion != currentVersion) {
			return "";
		}
		return registrationId;
	}

	private void storeRegistrationId(String regId) {
		final SharedPreferences prefs = getGCMPreferences();
		int appVersion = getAppVersion(mContext);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}

	private SharedPreferences getGCMPreferences() {
		return mContext.getSharedPreferences(GCM_PREFERENCE, Context.MODE_PRIVATE);
	}

	private static int getAppVersion(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionCode;
		}
		catch (NameNotFoundException e) {
			// should never happen
			throw new RuntimeException("Could not get package name: " + e);
		}
	}

	public void unRegisterBroadcastReceiver() {
		mContext.unregisterReceiver(mBroadcastReceiver);
	}

	public static final GCMManager getInstance(Context context, IGCMListener listener) {
		if (mInstance != null)
			return mInstance;

		if (!checkPlayServices(context))
			return null;

		mInstance = new GCMManager(context, listener);
		return mInstance;
	}

	public static boolean checkPlayServices(Context context) {
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
		if (resultCode != ConnectionResult.SUCCESS) {
			return false;
		}
		return true;
	}

	public class GcmBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Explicitly specify that GcmIntentService will handle the intent.
			Bundle extras = intent.getExtras();
			String messageType = mGcm.getMessageType(intent);

			if (!extras.isEmpty()) { // has effect of unparcelling Bundle
				/*
				 * Filter messages based on message type. Since it is likely that GCM will be extended in the future
				 * with new message types, just ignore any message types you're not interested in, or that you don't
				 * recognize.
				 */
				if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
				}
				else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
					// If it's a regular GCM message, do some work.
				}
				else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
					mListener.onGCMNotificationReceived(intent);
				}
			}
		}
	}
}
