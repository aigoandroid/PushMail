package com.aigovn.google.gcm;

import android.content.Intent;

public interface IGCMListener {
	public void onGCMRegistrationSuccess(String deviceToken);

	public void onGCMRegistrationFailed(String message);

	public void onGCMNotificationReceived(Intent intent);
}
