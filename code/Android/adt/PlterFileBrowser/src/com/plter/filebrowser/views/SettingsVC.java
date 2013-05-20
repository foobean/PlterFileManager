package com.plter.filebrowser.views;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;

import com.plter.filebrowser.R;
import com.plter.filebrowser.data.Config;
import com.plter.lib.android.java.anim.AnimationStyle;
import com.plter.lib.android.java.controls.HeaderItemData;
import com.plter.lib.android.java.controls.ViewController;

public class SettingsVC extends ViewController {

	public SettingsVC(Context context) {
		super(context,R.layout.settings_vc);
		setAnimationStyle(AnimationStyle.FLIP_HORIZONTAL);
		
		//init header
		gvHeader=(GridView) findViewByID(R.id.gvSettingsHeader);
		adapterHeader=new CustomHeaderAdapter(getContext());
		adapterHeader.add(new HeaderItemData(getContext(), R.string.back, R.drawable.back, HeaderActions.ACTION_FILE_BROWSER));
		gvHeader.setAdapter(adapterHeader);
		gvHeader.setOnItemClickListener(headerItemClickListener);
		
		//init other ui
		cbShowAllFiles=(CheckBox) findViewByID(R.id.cbShowAllFiles);
		cbShowAllFiles.setChecked(Config.isShowAllFiles(getContext()));
		cbShowAllFiles.setOnCheckedChangeListener(cbShowAllFileCheckedChangeListener);
	}
	
	
	private final OnItemClickListener headerItemClickListener=new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			
			HeaderItemData data = adapterHeader.getItem(position);
			System.out.println(data.getAction());
			switch (data.getAction()) {
			case HeaderActions.ACTION_FILE_BROWSER:
				getPrevious().popViewController(true);
				break;
			default:
				break;
			}
		}
	};
	
	private final OnCheckedChangeListener cbShowAllFileCheckedChangeListener=new CompoundButton.OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			Config.setShowAllFiles(getContext(), isChecked);
		}
	};
	
	private CustomHeaderAdapter adapterHeader=null;
	private GridView gvHeader=null;
	private CheckBox cbShowAllFiles=null;

}
