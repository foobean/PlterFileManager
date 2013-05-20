package com.plter.filebrowser.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public class ResTool {

	
	public static Bitmap getBitmap(Context context,int resId){
		return ((BitmapDrawable)(context.getResources().getDrawable(resId))).getBitmap();
	}

}
