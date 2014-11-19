package com.aigovn.pushmail;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class InboxFragment extends Fragment implements OnClickListener {
	
	private Button btnMenu, btnEdit;
	private ListView listInbox;
	private INavigation mListener;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mListener = (INavigation)activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.inbox_fragment, container, false);
		
		btnMenu = (Button)view.findViewById(R.id.btn_menu);
		btnEdit = (Button)view.findViewById(R.id.btn_edit);
		listInbox = (ListView)view.findViewById(R.id.list_inbox);
		
		btnMenu.setOnClickListener(this);
		btnEdit.setOnClickListener(this);
		
		listInbox.setVisibility(View.GONE);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btn_menu:
			break;
		case R.id.btn_edit:
			mListener.navigateForward(new OutboxFragment());
			break;
		}
	}
	
	
}
