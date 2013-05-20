package com.plter.filebrowser.views;

import com.plter.lib.java.utils.NumTool;

import android.content.Context;
import android.content.pm.PackageManager;

public class FileDes {

	public static String getDes(Context context,FileListCellData data){
		if (data.getFile().isFile()) {
			
			StringBuffer des = new StringBuffer();
			
			if (data.getFileType().equals("apk")) {
				des.append(context.getPackageManager().getPackageArchiveInfo(data.getFile().getAbsolutePath(), PackageManager.GET_ACTIVITIES).packageName);
				des.append("\n");
			}
			
			long size = data.getFile().length();

			if (size>1024*1024) {
				des.append(NumTool.bytesToMB(size)+" MB");
			}else if (size>1024) {
				des.append(NumTool.bytesToKB(size)+" KB");
			}else{
				des.append(size+" B");
			}
			
			return des.toString();
		}
		
		return "";
	}
	
}
