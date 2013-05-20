package com.plter.filebrowser.tools;

import java.io.File;

import com.plter.filebrowser.views.FileListCellData;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;


public class FileOpenHelper {


	public static void openFile(Context context,FileListCellData data){
		try{				
			context.startActivity(getFileOpenIntent(data));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static Intent getFileOpenIntent(FileListCellData data){

		String type=data.getFileType();
		File file = data.getFile();

		/* 依扩展名的类型决定MimeType */
		if(type.equals("m4a")||type.equals("mp3")||type.equals("mid")||
				type.equals("xmf")||type.equals("ogg")||type.equals("wav")){
			return getAudioFileIntent(file);
		}else if(type.equals("3gp")||type.equals("mp4")){
			return getVideoFileIntent(file);
		}else if(type.equals("jpg")||type.equals("gif")||type.equals("png")||
				type.equals("jpeg")||type.equals("bmp")){
			return getImageFileIntent(file);
		}else if(type.equals("apk")){
			return getApkFileIntent(file);
		}else if(type.equals("ppt")||type.equals("pptx")){
			return getPptFileIntent(file);
		}else if(type.equals("xls")||type.equals("xlsx")){
			return getExcelFileIntent(file);
		}else if(type.equals("doc")||type.equals("docx")){
			return getWordFileIntent(file);
		}else if(type.equals("pdf")){
			return getPdfFileIntent(file);
		}else if(type.equals("chm")){
			return getChmFileIntent(file);
		}else if(type.equals("txt")){
			return getTextFileIntent(file);
		}else{
			return getAllIntent(file);
		}
	}

	//Android获取一个用于打开APK文件的intent
	public static Intent getAllIntent(File file ) {

		Intent intent = new Intent();  
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
		intent.setAction(android.content.Intent.ACTION_VIEW);  
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri,"*/*"); 
		return intent;
	}
	//Android获取一个用于打开APK文件的intent
	public static Intent getApkFileIntent( File file ) {

		Intent intent = new Intent();  
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
		intent.setAction(android.content.Intent.ACTION_VIEW);  
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri,"application/vnd.android.package-archive"); 
		return intent;
	}

	//Android获取一个用于打开VIDEO文件的intent
	public static Intent getVideoFileIntent( File file ) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "video/*");
		return intent;
	}

	//Android获取一个用于打开AUDIO文件的intent
	public static Intent getAudioFileIntent( File file ){

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "audio/*");
		return intent;
	}

	//Android获取一个用于打开Html文件的intent   
	public static Intent getHtmlFileIntent( File file ){

		Uri uri = Uri.fromFile(file);
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.setDataAndType(uri, "text/html");
		return intent;
	}

	//Android获取一个用于打开图片文件的intent
	public static Intent getImageFileIntent( File file ) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "image/*");
		return intent;
	}

	//Android获取一个用于打开PPT文件的intent   
	public static Intent getPptFileIntent( File file ){  

		Intent intent = new Intent("android.intent.action.VIEW");   
		intent.addCategory("android.intent.category.DEFAULT");   
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
		Uri uri = Uri.fromFile(file);   
		intent.setDataAndType(uri, "application/vnd.ms-powerpoint");   
		return intent;   
	}   

	//Android获取一个用于打开Excel文件的intent   
	public static Intent getExcelFileIntent( File file ){  

		Intent intent = new Intent("android.intent.action.VIEW");   
		intent.addCategory("android.intent.category.DEFAULT");   
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
		Uri uri = Uri.fromFile(file);   
		intent.setDataAndType(uri, "application/vnd.ms-excel");   
		return intent;   
	}   

	//Android获取一个用于打开Word文件的intent   
	public static Intent getWordFileIntent( File file ){  

		Intent intent = new Intent("android.intent.action.VIEW");   
		intent.addCategory("android.intent.category.DEFAULT");   
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
		Uri uri = Uri.fromFile(file);   
		intent.setDataAndType(uri, "application/msword");   
		return intent;   
	}   

	//Android获取一个用于打开CHM文件的intent   
	public static Intent getChmFileIntent( File file ){   

		Intent intent = new Intent("android.intent.action.VIEW");   
		intent.addCategory("android.intent.category.DEFAULT");   
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
		Uri uri = Uri.fromFile(file);   
		intent.setDataAndType(uri, "application/x-chm");   
		return intent;   
	}   

	//Android获取一个用于打开文本文件的intent   
	public static Intent getTextFileIntent( File file){   

		Intent intent = new Intent("android.intent.action.VIEW");   
		intent.addCategory("android.intent.category.DEFAULT");   
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   

		Uri uri2 = Uri.fromFile(file);   
		intent.setDataAndType(uri2, "text/plain");   
		return intent;   
	}  
	//Android获取一个用于打开PDF文件的intent   
	public static Intent getPdfFileIntent( File file ){   

		Intent intent = new Intent("android.intent.action.VIEW");   
		intent.addCategory("android.intent.category.DEFAULT");   
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
		Uri uri = Uri.fromFile(file);   
		intent.setDataAndType(uri, "application/pdf");   
		return intent;   
	}
}
