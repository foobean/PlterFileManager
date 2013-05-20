package com.plter.filebrowser.views;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;

public class FileListCellData implements Comparable<FileListCellData>{


	public FileListCellData(Context context,File file,FileListAdapter adapter) {
		setContext(context);
		setAdapter(adapter);

		setFile(file);
		computeFileType();

		setFileName(file.getName());
		
		des=FileDes.getDes(getContext(), this);
	}


	@Override
	public int compareTo(FileListCellData another) {
		if (getFileName().length()<=0||another.getFileName().length()<=0) {
			return 0;
		}

		return getFileName().substring(0, 1).toLowerCase().charAt(0)-
				another.getFileName().substring(0, 1).toLowerCase().charAt(0);
	}	

	private void computeFileType(){
		if (!getFile().isDirectory()) {
			String name = getFile().getName();
			int start=name.lastIndexOf('.');

			if (start>-1) {
				setFileType(name.substring(start+1, name.length()).toLowerCase());				
			}else{
				setFileType("");
			}
		}else{
			setFileType("");
		}
	}


	public File getFile() {
		return file;
	}


	private void setFile(File file) {
		this.file = file;
	}

	public Bitmap getIcon(){

		if (icon==null) {
			icon = FileIcons.getFileIcon(getContext(), this);
		}

		return icon;
	}

	public String getFileName() {
		return fileName;
	}


	private void setFileName(String name) {
		this.fileName = name;
	}

	public String getFileType() {
		return fileType;
	}

	private void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Context getContext() {
		return context;
	}

	private void setContext(Context context) {
		this.context = context;
	}

	@Override
	public String toString() {
		return getFileName();
	}

	/**
	 * 取得描述信息
	 * @return
	 */
	public String getDes() {
		return des;
	}

	public FileListAdapter getAdapter() {
		return adapter;
	}


	private void setAdapter(FileListAdapter adapter) {
		this.adapter = adapter;
	}

	private File file=null;
	private String fileName=null;
	private Bitmap icon=null;
	private String fileType="";
	private Context context=null;
	private String des="";
	private FileListAdapter adapter=null;
}
