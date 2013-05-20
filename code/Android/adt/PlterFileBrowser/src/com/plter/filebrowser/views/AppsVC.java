package com.plter.filebrowser.views;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.plter.filebrowser.R;
import com.plter.lib.android.java.anim.AnimationStyle;
import com.plter.lib.android.java.controls.HeaderItemData;
import com.plter.lib.android.java.controls.ViewController;

public class AppsVC extends ViewController {

	private final OnItemClickListener gvHeaderItemClickListener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			HeaderItemData data = headAdapter.getItem(position);
			
			switch (data.getAction()) {
			case HeaderActions.ACTION_BACK:
				getPrevious().popViewController(true);
				break;
			default:
				break;
			}
		}
	};
	
	
	
	public AppsVC(Context context) {
		super(context, R.layout.apps_vc);
		setAnimationStyle(AnimationStyle.COVER_VERTICAL);
		
		gvAppsHeader=(GridView) findViewByID(R.id.gvAppsHeader);
		headAdapter=new CustomHeaderAdapter(getContext());
		gvAppsHeader.setAdapter(headAdapter);
		
		headAdapter.add(new HeaderItemData(getContext(), R.string.back, R.drawable.back, HeaderActions.ACTION_BACK));
		headAdapter.add(new HeaderItemData(getContext(), R.string.installed, R.drawable.installed, HeaderActions.ACTION_INSTALLED));
		headAdapter.add(new HeaderItemData(getContext(), R.string.running, R.drawable.running, HeaderActions.ACTION_RUNNING));
		
		gvAppsHeader.setOnItemClickListener(gvHeaderItemClickListener);
	}

	
	private GridView gvAppsHeader=null;
	private CustomHeaderAdapter headAdapter=null;
}
