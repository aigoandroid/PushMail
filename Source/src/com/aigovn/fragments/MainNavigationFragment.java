package com.aigovn.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.aigovn.pushmail.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



public class MainNavigationFragment extends Fragment {

	/* parent option menu in navigation */
	@SuppressWarnings("unused")
	private OnFragmentDoneLoadingListener mListener;
	private LinearLayout optionLeftMenu;


	protected void processHandlerMessage(Message msg) {
		switch (msg.what) {
			
		}
		//super.processHandlerMessage(msg);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		//super.onCreate(savedInstanceState);

		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View parent = inflater.inflate(R.layout.left_menu_fragment, container, false);
		optionLeftMenu = (LinearLayout) parent.findViewById(R.id.option_left_menu);

		// status profile
		
		return parent;
	}

	

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	
	public void onClickActivityOption(final View view) {
		/* delay change home content for smooth side menu animation */
		Handler handler = new Handler(getActivity().getMainLooper());
		//int delayTime = Constant.CLOSE_MENU_DELAY_TIME;
	}

	public void changeHome(int viewId, long kardId) {
		
	}


	public void onViewClick(View v) {
	}


	// set listener for call back function
	public void setOnFragmentDoneLoadingListener(OnFragmentDoneLoadingListener listener) {
		this.mListener = listener;
	}

	// interface for call back function when left fragment init successfuly
	public static interface OnFragmentDoneLoadingListener {
		void OnFragmentDoneLoading();
	}

	
}