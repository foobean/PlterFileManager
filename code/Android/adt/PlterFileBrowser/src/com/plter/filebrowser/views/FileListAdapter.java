package com.plter.filebrowser.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.plter.filebrowser.R;

public class FileListAdapter extends BaseAdapter {


	public FileListAdapter(Context context) {
		setContext(context);
	}


	@Override
	public int getCount() {
		return al.size();
	}

	@Override
	public FileListCellData getItem(int position) {
		return al.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	public void add(FileListCellData object){
		al.add(object);
		notifyDataSetChanged();
	}

	public void add(int index,FileListCellData object){
		al.add(index, object);
		notifyDataSetChanged();
	}

	public void addAll(Collection<? extends FileListCellData> collection){
		al.addAll(collection);
		sortByFileName();
		notifyDataSetChanged();
	}

	public void addAll(FileListCellData[] objects){
		for (int i = 0; i < objects.length; i++) {
			add(objects[i]);
		}
		sortByFileName();
		notifyDataSetChanged();
	}


	public void remove(FileListCellData object){
		al.remove(object);
		notifyDataSetChanged();
	}
	
	private void sortByFileName(){
		Collections.sort(al);
	}


	public void remove(int index){
		al.remove(index);
		notifyDataSetChanged();
	}


	public void removeAll(){
		al.clear();
		notifyDataSetChanged();
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView==null) {
			convertView=LayoutInflater.from(getContext()).inflate(R.layout.file_list_cell, null);
		}
		
		ImageView ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
		TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
		TextView tvDes = (TextView) convertView.findViewById(R.id.tvDes);
		ImageView ivRightArr = (ImageView) convertView.findViewById(R.id.ivRightArr);
		
		FileListCellData data = getItem(position);
		
		ivIcon.setImageBitmap(data.getIcon());
		tvName.setText(data.getFileName());
		tvDes.setText(data.getDes());
		
		if (data.getFile().isFile()) {
			tvDes.setVisibility(View.VISIBLE);
			ivRightArr.setVisibility(View.GONE);
		}else{
			tvDes.setVisibility(View.GONE);
			ivRightArr.setVisibility(View.VISIBLE);
		}

		return convertView;
	}	

	public Context getContext() {
		return context;
	}


	private void setContext(Context context) {
		this.context = context;
	}


	private Context context=null;
	private final ArrayList<FileListCellData> al = new ArrayList<FileListCellData>();

}
