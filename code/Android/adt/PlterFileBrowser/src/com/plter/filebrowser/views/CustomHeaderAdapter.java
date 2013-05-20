package com.plter.filebrowser.views;

import com.plter.filebrowser.R;
import com.plter.lib.android.java.controls.HeaderItemData;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomHeaderAdapter extends com.plter.lib.android.java.controls.HeaderAdapter {

	public CustomHeaderAdapter(Context context) {
		super(context, R.layout.header_item);
	}

	@Override
	public void initListCell(int position, View listCell, ViewGroup parent) {
		TextView tv = (TextView) listCell.findViewById(R.id.tvLabel);
		ImageView ivIcon = (ImageView) listCell.findViewById(R.id.ivIcon);
		HeaderItemData data = getItem(position);

		tv.setText(data.getLabel());
		ivIcon.setImageResource(data.getIconId());
	}

}
