package com.plter.filebrowser.views;

import android.content.Context;
import android.graphics.Bitmap;

import com.plter.filebrowser.R;
import com.plter.filebrowser.tools.ResTool;

public class FileIcons {

	
	public static Bitmap getFileIcon(Context context,FileListCellData data){
		
		if (data.getFile().isFile()) {
			
			if (data.getFileType().equals("swf")) {
				return ResTool.getBitmap(context, R.drawable.document_swf);
			}else if (data.getFileType().equals("doc")||data.getFileType().equals("docx")) {
				return ResTool.getBitmap(context, R.drawable.word);
			}else if (data.getFileType().equals("xls")||data.getFileType().equals("xlsx")) {
				return ResTool.getBitmap(context, R.drawable.xlsx);
			}else if (data.getFileType().equals("ppt")||data.getFileType().equals("pptx")) {
				return ResTool.getBitmap(context, R.drawable.ppt);
			}else if (data.getFileType().equals("mp3")) {
				return ResTool.getBitmap(context, R.drawable.mp3);
			}else if (data.getFileType().equals("mp4")) {
				return ResTool.getBitmap(context, R.drawable.mp4);
			}else if (data.getFileType().equals("jpg")||data.getFileType().equals("jpeg")) {
				return ResTool.getBitmap(context, R.drawable.jpg);
			}else if (data.getFileType().equals("png")) {
				return ResTool.getBitmap(context, R.drawable.png);
			}else if (data.getFileType().equals("pdf")) {
				return ResTool.getBitmap(context, R.drawable.pdf);
			}else if (data.getFileType().equals("apk")) {
				return ResTool.getBitmap(context, R.drawable.pkg_icon);
			}
			
			return ResTool.getBitmap(context, R.drawable.file);
		}else{
			return ResTool.getBitmap(context, R.drawable.dir);
		}
	}
}
