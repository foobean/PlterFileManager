package com.plter.filebrowser.tasks;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

public class DeleteFileTask extends AsyncTask<Void, Integer, Boolean> {

	
	
	public static DeleteFileTask DeleteFile(File file,OnProgressListener onProgressListener,OnResultListener onResultListener){
		DeleteFileTask task = new DeleteFileTask(file, onProgressListener, onResultListener);
		task.execute();
		return task;
	}
	
	
	public static interface OnProgressListener{
		
		/**
		 * @param type 进度类型，如果是查找，则progress为查找到的文件的个数，如果是删除，则progress为删除的个数
		 * @param num
		 * @param all
		 */
		void onProgress(int type,int num,int all);
	}
	
	
	public static interface OnResultListener{
		/**
		 * 
		 * @param suc 是否成功执行
		 */
		void onResult(boolean suc);
	}
	
	
	private DeleteFileTask(File file,OnProgressListener onProgressListener,OnResultListener onResultListener) {
		fileToBeDelete=file;
		this.onProgressListener=onProgressListener;
		this.onResultListener=onResultListener;
	}
	
	
	@Override
	protected Boolean doInBackground(Void... params) {
		if (fileToBeDelete.isFile()) {
			return fileToBeDelete.delete();
		}else if (fileToBeDelete.isDirectory()) {
			fileToBeDeleteList.clear();
			findFiles(fileToBeDelete);
			
			if (isCancelled()) {
				return false;
			}
			
			for (int i = 0; i < fileToBeDeleteList.size(); i++) {
				if (!fileToBeDeleteList.get(i).delete()) {
					return false;
				}
				publishProgress(DELETING,i+1,fileToBeDeleteList.size());
				
				if (isCancelled()) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		if (onResultListener!=null) {
			onResultListener.onResult(result);
		}
		super.onPostExecute(result);
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		if (onProgressListener!=null) {
			onProgressListener.onProgress(values[0], values[1],values[2]);
		}
		super.onProgressUpdate(values);
	}
	
	private void findFiles(File dir){
		
		if (isCancelled()) {
			return;
		}
		
		File[] children = dir.listFiles();
		
		if (children!=null) {
			for (File f : children) {				
				if (f.isFile()) {
					fileToBeDeleteList.add(f);					
					publishProgress(FINDING,fileToBeDeleteList.size(),fileToBeDeleteList.size());
				}else if (f.isDirectory()) {
					findFiles(f);
				}
			}
		}
		
		fileToBeDeleteList.add(dir);
		publishProgress(FINDING,fileToBeDeleteList.size(),fileToBeDeleteList.size());
	}
	
	private final List<File> fileToBeDeleteList = new ArrayList<File>();
	private File fileToBeDelete=null;
	private OnProgressListener onProgressListener=null;
	private OnResultListener onResultListener=null;
	public static final int FINDING=1,DELETING=2;
}
