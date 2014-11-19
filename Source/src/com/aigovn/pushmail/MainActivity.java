package com.aigovn.pushmail;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity implements INavigation {
	
	FragmentManager fMan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.main_activity);
		
		fMan = getFragmentManager();
		InboxFragment fragment = new InboxFragment();
		setHomeContent(fragment);
	}
	
	@Override
	public void onBackPressed() {
		if(fMan.getBackStackEntryCount() == 1){
			finish();
		}else{
			super.onBackPressed();
		}
	}

	@Override
	public void setHomeContent(Fragment fragment){
		String fragName = fragment.getClass().getName();
		FragmentTransaction fTrans = fMan.beginTransaction();
		fTrans.add(R.id.home_content, fragment, fragName);
		fTrans.addToBackStack(fragName);
		fTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		fTrans.commit();
	}

	@Override
	public void showNavigationMenu() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void navigateForward(Fragment frag) {
		String fragName = frag.getClass().getName();
		FragmentTransaction fTrans = fMan.beginTransaction();
		fTrans.replace(R.id.home_content, frag, fragName);
		fTrans.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
		fTrans.addToBackStack(fragName);
		fTrans.commit();
	}

	@Override
	public void navigateBackward(String fragName) {
		FragmentManager fMan = getFragmentManager();
		fMan.popBackStack(fragName, 0);
	}
	
	
}
