package com.plter.filebrowser;

import android.os.Bundle;
import android.widget.Toast;

import com.plter.filebrowser.views.FileListView;
import com.plter.lib.android.java.controls.ViewControllerActivity;
import com.tencent.exmobwin.MobWINManager;
import com.tencent.exmobwin.Type;

public class MainActivity extends ViewControllerActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        
        //AD
        MobWINManager.init(this,Type.MOBWIN_BANNER);
        
        
        flv=new FileListView(this, this, null);
        setContentView(flv);
        
        getRootViewController().navigateTo.add(flv.navigateToListener);
    }
    
    @Override
    public void onBackPressed() {
    	if (getTopViewController()!=getRootViewController()) {
			popTopViewController(true);
			
			canQuit=false;
		}else{
			if (!canQuit) {
				Toast.makeText(this, R.string.press_back_btn_again_to_quit_the_app, Toast.LENGTH_LONG).show();
				canQuit=true;
			}else{
				finish();
			}
		}
    }
    
    private boolean canQuit=false;
    
    private FileListView flv;
    
    @Override
    protected void onDestroy() {
    	MobWINManager.destroy();
    	super.onDestroy();
    }
}
