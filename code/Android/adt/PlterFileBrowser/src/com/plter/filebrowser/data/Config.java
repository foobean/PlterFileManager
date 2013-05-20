package com.plter.filebrowser.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Config {

	
	

	public static String getCurrentDir(Context context) {
		return getSharedPreferences(context).getString("dirPath", null);
	}

	public static void setCurrentDir(String currentDir,Context context) {
		Editor e = getEditor(context);
		e.putString("dirPath", currentDir);
		e.commit();
	}
	
	
	private static SharedPreferences getSharedPreferences(Context context){
		return context.getSharedPreferences("filebrowser", Context.MODE_PRIVATE);
	}
	
	private static Editor getEditor(Context context){
		return getSharedPreferences(context).edit();
	}
	
	
	public static boolean isShowAllFiles(Context context) {
		return getSharedPreferences(context).getBoolean("showAllFiles", false);
	}

	public static void setShowAllFiles(Context context,boolean value) {
		Editor e = getEditor(context);
		e.putBoolean("showAllFiles", value);
		e.commit();
	}
}
