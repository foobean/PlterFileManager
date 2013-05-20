package com.plter.filebrowser.views;

import java.io.File;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.plter.filebrowser.MainActivity;
import com.plter.filebrowser.R;
import com.plter.filebrowser.data.Config;
import com.plter.filebrowser.tasks.DeleteFileTask;
import com.plter.filebrowser.tools.FileOpenHelper;
import com.plter.lib.android.java.anim.AnimationStyle;
import com.plter.lib.android.java.controls.HeaderItemData;
import com.plter.lib.android.java.controls.IViewController;
import com.plter.lib.android.java.controls.ViewController;
import com.plter.lib.android.java.controls.ViewControllerEvent;
import com.plter.lib.java.event.EventListener;
import com.plter.lib.java.utils.NumTool;

@SuppressLint("ViewConstructor")
public class FileListView extends FrameLayout {


	public final EventListener<ViewControllerEvent> navigateToListener=new EventListener<ViewControllerEvent>() {

		@Override
		public boolean onReceive(Object target, ViewControllerEvent event) {
			Config.setCurrentDir(currentDir.getAbsolutePath(), getContext());

			if (currentAllFilesVisible!=Config.isShowAllFiles(getContext())) {
				currentAllFilesVisible=!currentAllFilesVisible;

				refreshFileListData();
			}
			return true;
		}
	};


	private final AdapterView.OnItemClickListener fileListItemClickListener=new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			FileListCellData data = adapter.getItem(arg2);
			open(data);
		}
	};


	private final OnItemLongClickListener fileListItemLongClickListener = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				final int position, long id) {
			new AlertDialog.Builder(getContext())
			.setTitle(R.string.options)
			.setItems(new CharSequence[]{
					getContext().getString(R.string.open),
					getContext().getString(R.string.delete)
			}, 
			new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0:
						open(adapter.getItem(position));
						break;
					case 1:
						deleteFile(position);
						break;
					}
				}
			})
			.show();

			return true;
		}
	};


	private final OnClickListener tvCurrentDir_clickListener=new OnClickListener() {

		@Override
		public void onClick(View v) {

			String str = tvCurrentDir.getText().toString();
			if (str.equals("/")) {
				return;
			}
			
			ArrayList<String> dirs = new ArrayList<String>();
			int index=0;
			while((index = str.indexOf('/', index)+1)>0){
				dirs.add(str.substring(0, index));
			}

			final String[] dirStrs = new String[dirs.size()];
			for (int i = 0; i < dirStrs.length; i++) {
				dirStrs[i]=dirs.get(i);
			}

			new AlertDialog.Builder(getContext())
			.setItems(dirStrs, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					File targetFile = new File(dirStrs[which]);
					if (!targetFile.getAbsolutePath().equals(currentDir.getAbsolutePath())) {
						changeDirTo(targetFile, AnimationStyle.CROSS_DISSOLVE);
					}
				}
			})
			.show();


		}
	};


	public FileListView(MainActivity context,IViewController viewController,String dirPath) {
		super(context);
		setViewController(viewController);

		currentAllFilesVisible=Config.isShowAllFiles(getContext());
		currentDirPath=dirPath;

		//init root view
		rootView=LayoutInflater.from(getContext()).inflate(R.layout.file_list_view, null);
		addView(rootView, -1, -1);

		//init ui
		tvCurrentDir=(TextView) rootView.findViewById(R.id.tvCurrentDir);
		tvCurrentDir.setOnClickListener(tvCurrentDir_clickListener);
		tvStatus=(TextView) rootView.findViewById(R.id.tvStatus);

		fileListView=(ListView) rootView.findViewById(R.id.lvFileList);
		adapter=new FileListAdapter(getContext());
		fileListView.setAdapter(adapter);
		fileListView.setOnItemClickListener(fileListItemClickListener);
		fileListView.setOnItemLongClickListener(fileListItemLongClickListener );

		refreshFileListData();
		initHeader();
	}


	private void initHeader(){
		gvHeader=(GridView) rootView.findViewById(R.id.gvFileBrowserViewHeader);
		gvHeaderAdapter=new CustomHeaderAdapter(getContext());

		//init data
		gvHeaderAdapter.add(new HeaderItemData(getContext(),R.string.fl_header_back_dir, R.drawable.back_dir,HeaderActions.ACTION_BACK));
		gvHeaderAdapter.add(new HeaderItemData(getContext(),R.string.fl_header_parent_dir, R.drawable.parent_dir,HeaderActions.ACTION_PARENT));
		//		gvHeaderAdapter.add(new HeaderItemData(getContext(),R.string.fl_header_root_dir, R.drawable.home,HeaderActions.ACTION_HOME));
		gvHeaderAdapter.add(new HeaderItemData(getContext(),R.string.fl_header_sdcard_dir, R.drawable.sdcard,HeaderActions.ACTION_SD_CARD));
		//		gvHeaderAdapter.add(new HeaderItemData(getContext(),R.string.fl_header_apps, R.drawable.apps,HeaderActions.ACTION_APPS));
		gvHeaderAdapter.add(new HeaderItemData(getContext(),R.string.fl_header_settings, R.drawable.settings,HeaderActions.ACTION_SETTINGS));

		gvHeader.setAdapter(gvHeaderAdapter);
		gvHeader.setOnItemClickListener(gvHeaderItemClickListener);
	}

	private final AdapterView.OnItemClickListener gvHeaderItemClickListener=new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			if(currentDir==null){
				return;
			}

			switch (gvHeaderAdapter.getItem(arg2).getAction()) {
			case HeaderActions.ACTION_BACK:
				((MainActivity)getContext()).onBackPressed();
				break;
			case HeaderActions.ACTION_PARENT:

				if (currentDir.getParentFile()!=null) {
					changeDirTo(currentDir.getParentFile(),AnimationStyle.PUSH_LEFT_TO_RIGHT);
				}else{
					Toast.makeText(getContext(), R.string.current_dir_is_root_dir, Toast.LENGTH_SHORT).show();
				}
				break;
			case HeaderActions.ACTION_HOME:
				if (currentDir.getParentFile()!=null) {
					changeDirTo(new File("/"), AnimationStyle.CROSS_DISSOLVE);
				}
				break;
			case HeaderActions.ACTION_SD_CARD:
				if (!currentDir.getAbsolutePath().equals(Environment.getExternalStorageDirectory().getAbsolutePath())) {
					changeDirTo(Environment.getExternalStorageDirectory(), AnimationStyle.CROSS_DISSOLVE);
				}
				break;
			case HeaderActions.ACTION_APPS:
				if (appsVC==null) {
					appsVC=new AppsVC(getContext());
				}
				getViewController().pushViewController(appsVC, true);
				break;
			case HeaderActions.ACTION_SETTINGS:
				getViewController().pushViewController(new SettingsVC(getContext()), true);
				break;
			default:
				break;
			}
		}
	};

	private AppsVC appsVC=null;


	private void deleteFile(final int position){
		final ProgressDialog pd = ProgressDialog.show(getContext(), 
				getContext().getText(R.string.deleting), 
				getContext().getText(R.string.please_wait));
		//		pd.setCancelable(true);


		final DeleteFileTask task = DeleteFileTask.DeleteFile(adapter.getItem(position).getFile(), 
				new DeleteFileTask.OnProgressListener() {

			@Override
			public void onProgress(int type, int num, int all) {
				switch (type) {
				case DeleteFileTask.DELETING:
					pd.setTitle(getContext().getText(R.string.deleting));
					pd.setMessage(String.format("%s,%d/%d", 
							getContext().getText(R.string.deleting),
							num,all));
					break;
				case DeleteFileTask.FINDING:
					pd.setTitle(getContext().getText(R.string.finding));
					pd.setMessage(String.format("%s%d%s", 
							getContext().getText(R.string.finded),
							num,
							getContext().getText(R.string.items)));
					break;
				}
			}
		}, new DeleteFileTask.OnResultListener() {

			@Override
			public void onResult(boolean suc) {
				pd.dismiss();

				if (suc) {
					adapter.remove(position);
				}else{
					new AlertDialog.Builder(getContext())
					.setTitle(R.string.hello)
					.setMessage(R.string.failed_to_delete)
					.setPositiveButton("OK", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							//
						}
					})
					.show();
				}
			}
		});

		pd.setOnCancelListener(new DialogInterface.OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				task.cancel(true);
			}
		});
	}


	private void refreshFileListData(){

		adapter.removeAll();

		if (currentDirPath==null) {
			currentDirPath=Config.getCurrentDir(getContext());
		}
		if (currentDirPath==null) {
			currentDirPath=Environment.getExternalStorageDirectory().getAbsolutePath();
		}


		currentDir = new File(currentDirPath);
//		tvCurrentDir.setText(currentDirPath);
		tvCurrentDir.setText(Html.fromHtml("<u>"+currentDirPath+"</u>"));

		File[] children=null;
		if (currentDir.exists()&&(children=currentDir.listFiles())!=null) {

			long allFileSize=0;

			ArrayList<FileListCellData> data = new ArrayList<FileListCellData>();

			for (File file : children) {

				if (!currentAllFilesVisible) {
					if (file.getName().charAt(0)!='.') {
						data.add(new FileListCellData(getContext(),file,adapter));
					}
				}else{
					data.add(new FileListCellData(getContext(),file,adapter));
				}

				if (file.isFile()) {
					allFileSize+=file.length();
				}
			}

			adapter.addAll(data);

			tvStatus.setText(data.size()+" "+getContext().getString(R.string.items)+", " +
					+ NumTool.bytesToMB(allFileSize)+" MB");
		}else{
			tvStatus.setText(" ");
		}
	}

	private void open(FileListCellData data){
		if (data.getFile().isDirectory()) {
			changeDirTo(data.getFile(),AnimationStyle.PUSH_RIGHT_TO_LEFT);
		}else{
			FileOpenHelper.openFile(getContext(),data);
		}
	}


	private void changeDirTo(final File dir,int animStyle){
		FileListView view = new FileListView((MainActivity) getContext(), null, dir.getAbsolutePath());
		ViewController vc = new ViewController(view);
		vc.navigateTo.add(view.navigateToListener);

		view.setViewController(vc);

		vc.setAnimationStyle(animStyle);
		getViewController().pushViewController(vc, true);
	}


	public IViewController getViewController() {
		return viewController;
	}
	public void setViewController(IViewController viewController) {
		this.viewController = viewController;
	}

	private ListView fileListView=null;
	private IViewController viewController=null;
	private FileListAdapter adapter=null;
	private View rootView=null;
	private GridView gvHeader=null;
	private File currentDir=null;
	private TextView tvCurrentDir=null;
	private TextView tvStatus=null;
	private CustomHeaderAdapter gvHeaderAdapter=null;
	private boolean currentAllFilesVisible=false;
	private String currentDirPath=null;

}
