package com.aigovn.pushmail;

import android.app.Fragment;

public interface INavigation {
	public void showNavigationMenu();
	public void navigateForward(Fragment frag);
	public void navigateBackward(String fragName);
	public void setHomeContent(Fragment frag);
}
