package tjssm.mamsee.manager.ui;


// SET_APP_OPTION - 앱 제한 설정 push  메시지
// 



import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import tjssm.mamsee.manager.R;
import tjssm.mamsee.manager.http.ChildApp;
import tjssm.mamsee.manager.http.ChildInfo;
import tjssm.mamsee.manager.http.GetAppList;
import tjssm.mamsee.manager.http.GetChildList;
import tjssm.mamsee.manager.localdb.AppManageDBHelper;
import tjssm.mamsee.manager.ui.info.ChildAppInfo;
import tjssm.mamsee.manager.ui.info.ChildChatInfo;
import tjssm.mamsee.manager.ui.info.ChildWebInfo;
import tjssm.mamsee.manager.ui.manage.ManageApp;
import tjssm.mamsee.manager.ui.manage.ManageChat;
import tjssm.mamsee.manager.ui.manage.ManageDic;
import tjssm.mamsee.manager.ui.manage.ManageWeb;
import tjssm.mamsee.manager.ui.setting.SettingAccount;
import tjssm.mamsee.manager.ui.setting.SettingChild;
import tjssm.mamsee.manager.ui.setting.SettingNotification;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.OnNavigationListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.support.v4.view.GravityCompat;
import android.text.Html;

@SuppressLint("NewApi")
public class MainActivity extends SherlockFragmentActivity implements OnNavigationListener {
	
	// Declare Variable
	public static String cur_child_id;
	public static String cur_p_id;
	private String cur_child_name;
	private String cur_child_date;
	private String cur_child_route;
	
	public static ArrayList<ChildInfo> arrChildInfo;
	DrawerLayout mDrawerLayout;
	ListView mDrawerList;
	ActionBarDrawerToggle mDrawerToggle;
	MenuListAdapter mMenuAdapter;
	static ArrayAdapter<CharSequence> list;
	static ArrayList<CharSequence> mChildList;
	ArrayList<CharSequence> mPageMoveList;
	String[] title;
	String[] subtitle;
	
	int[] icon;
	Fragment manage_app = new ManageApp();
	Fragment manage_web = new ManageWeb();
	Fragment manage_chat = new ManageChat();
	Fragment manage_dic = new ManageDic();
	Fragment setting_account = new SettingAccount();
	Fragment setting_child = new SettingChild();
	Fragment setting_noti = new SettingNotification();
	Fragment child_app_info = new ChildAppInfo();
	Fragment child_web_info = new ChildWebInfo();
	Fragment child_chat_info = new ChildChatInfo();
			
	Handler h;
	
	ProgressDialog prog_dialog;
	GetAppList mGetAppList;
	TH_Loading mTh_Loading;
	public static ArrayList<ChildApp> arrChildApp;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.drawer_main);
		getActionBar().setDisplayHomeAsUpEnabled(false);
		mGetAppList = new GetAppList();
		initChildInfo();
		initSlideMenuInfo();
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		if (savedInstanceState == null) {
				selectItem(0);
		}
		prog_dialog = new ProgressDialog(MainActivity.this);
		prog_dialog.setMessage("잠시 기다려주세요...");
		prog_dialog.setCancelable(true);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == android.R.id.home) {

			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
		}
		return super.onOptionsItemSelected(item);
	}
	
	// The click listener for ListView in the navigation drawer
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}
	
	static class AppTimeCompare implements Comparator<ChildApp> {
		private final Collator collator = Collator.getInstance();
		@Override
		public int compare(ChildApp lhs, ChildApp rhs) {
			Log.d("MMM", ""+lhs.m_long_time+",    "+rhs.m_long_time);
			return collator.compare(lhs.m_used_time, rhs.m_used_time);
		}
	}
	
	Runnable irun = new Runnable() {
	        public void run() {
	        	
	        	if(MenuListAdapter.b_btn_square1 == true)
	        	{
	        		arrChildApp = mGetAppList.GetChildAppList(cur_child_id);
	        		//시간 순서대로 정렬!
	        		Collections.sort(arrChildApp, new AppTimeCompare());;//arrChildApp.
	        		Collections.reverse(arrChildApp);
		    		for(int i = 0; i < arrChildApp.size(); i++) {
		    			Log.d("Main_GetAppList", arrChildApp.get(i).m_app_name);
		    			Log.d("Main_GetAppList", arrChildApp.get(i).m_used_time);
		    		}
					getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		    		getSupportActionBar().setDisplayShowTitleEnabled(true);
	        		getSupportActionBar().setTitle(getString(R.string.child_app_manage));
	        		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		    		ft.replace(R.id.content_frame, manage_app);
		    		ft.commit();
		    		mDrawerList.setItemChecked(1, true);
		    		mDrawerLayout.closeDrawer(mDrawerList);
		    		MenuListAdapter.b_btn_square1 = false;
	        	}
	        	else if(MenuListAdapter.b_btn_square2 == true)
	        	{
	        		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		    		getSupportActionBar().setDisplayShowTitleEnabled(true);
	        		getSupportActionBar().setTitle(getString(R.string.child_web_manage));
	        		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		    		ft.replace(R.id.content_frame, manage_web);
		    		ft.commit();
		    		mDrawerList.setItemChecked(1, true);
		    		mDrawerLayout.closeDrawer(mDrawerList);
		    		MenuListAdapter.b_btn_square2 = false;
	        	}
	        	else if(MenuListAdapter.b_btn_square3 == true)
	        	{
	        		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		    		getSupportActionBar().setDisplayShowTitleEnabled(true);
	        		getSupportActionBar().setTitle(getString(R.string.child_chat_manage));
	        		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		    		ft.replace(R.id.content_frame, manage_chat);
		    		ft.commit();
		    		mDrawerList.setItemChecked(1, true);
		    		mDrawerLayout.closeDrawer(mDrawerList);
		    		MenuListAdapter.b_btn_square3 = false;
	        	}
	        	else if(MenuListAdapter.b_btn_square4 == true)
	        	{
	        		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		    		getSupportActionBar().setDisplayShowTitleEnabled(true);
	        		getSupportActionBar().setTitle(getString(R.string.child_dic_manage));
		    		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		    		ft.replace(R.id.content_frame, manage_dic);
		    		ft.commit();
		    		mDrawerList.setItemChecked(1, true);
		    		mDrawerLayout.closeDrawer(mDrawerList);
		    		MenuListAdapter.b_btn_square4 = false;
	        	}
	        	h.postDelayed(irun, 30);//0.3초
	        }
	};
	
	public void selectItem(int position) {

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// Locate Position
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		getSupportActionBar().setDisplayShowTitleEnabled(true);
		switch (position) {
		case 1:
			ft.replace(R.id.content_frame, child_app_info);
			//getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>"+R.string.child_app_info+"</font>"));
   		    getSupportActionBar().setTitle(getString(R.string.child_app_info));
			break;
		case 2:
			ft.replace(R.id.content_frame, child_web_info);
			//getSupportActionBar().setTitle(Html.fromHtml("<font color='#000000'>"+R.string.child_web_info+"</font>"));
			getSupportActionBar().setTitle(getString(R.string.child_web_info));
			break;
		case 3:
			ft.replace(R.id.content_frame, child_chat_info);
		    getSupportActionBar().setTitle(getString(R.string.child_chat_info));
			break;
		case 7:////계정 관리
			ft.replace(R.id.content_frame, setting_account);
   		    getSupportActionBar().setTitle(getString(R.string.account_management));
			break;
		case 8:
			ft.replace(R.id.content_frame, setting_child);
   		    getSupportActionBar().setTitle(getString(R.string.child_management));
			break;
		case 9:
			ft.replace(R.id.content_frame, setting_noti);
   		    getSupportActionBar().setTitle(getString(R.string.setting));
			break;
		}
		ft.commit();
		mDrawerList.setItemChecked(position, true);
		// Close drawer
		mDrawerLayout.closeDrawer(mDrawerList);
		
	}
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggles
		mDrawerToggle.onConfigurationChanged(newConfig);
	}


	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// TODO Auto-generated method stub
		
		
		cur_child_id = arrChildInfo.get(itemPosition).m_c_id;
		cur_child_name = arrChildInfo.get(itemPosition).m_child_name;
		cur_child_date = arrChildInfo.get(itemPosition).m_last_acc_date;
 		cur_child_route = arrChildInfo.get(itemPosition).m_is_routed;
 		
 		Log.d("TJSSM", "cur id:"+cur_child_id+", cur name:"+cur_child_name + "cur date:"+cur_child_date+", cur route:"+cur_child_route);
		return false;
	}
	
	public void initChildInfo() {
		mChildList = new ArrayList<CharSequence>();
		for(int i=0; i<arrChildInfo.size(); i++) 
			mChildList.add(arrChildInfo.get(i).m_child_name);
		list = new ArrayAdapter<CharSequence>(this, R.layout.sherlock_spinner_dropdown_item, mChildList);
		//navigation list
        Context context = getSupportActionBar().getThemedContext();
        list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        getSupportActionBar().setListNavigationCallbacks(list, this);
        getSupportActionBar().setTitle(getString(R.string.child_app_info));
	}
	private void initSlideMenuInfo() {
		h = new Handler();
        h.postDelayed(irun, 30);//0.3초
		
		// Generate title//1,3이 타이틀, 0, 3 버튼 
		title = new String[] { "Information", getString(R.string.child_app_info), getString(R.string.child_web_info), getString(R.string.child_chat_info), "Management", "square_form", "Setting", getString(R.string.account_management), 
				getString(R.string.child_management), getString(R.string.setting)};
		subtitle = new String[] { "Information", "1", "2", "3", "Management", "square_form", "Setting", getString(R.string.account_management), 
				getString(R.string.child_management), getString(R.string.setting)};
		icon = new int[] { 0, R.drawable.app_icon, R.drawable.web_icon, R.drawable.chat_icon, 0, 0, 0, R.drawable.id_icon, R.drawable.child_icon,
				R.drawable.setting_icon };
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
					GravityCompat.START);
		mMenuAdapter = new MenuListAdapter(this, title, subtitle, icon);
		mDrawerList.setAdapter(mMenuAdapter);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
		//타이틀 색상
		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xffffffff));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				Log.d("TJSSM", "cLOSE DRAWER!");
				getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
				getSupportActionBar().setDisplayShowTitleEnabled(true);
			}
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				initChildInfo();
				Log.d("TJSSM", "oPEN DRAWER!");
				getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
				getSupportActionBar().setDisplayShowTitleEnabled(false);
			}
		};
		/*
		list = new ArrayAdapter<CharSequence>(this, R.layout.sherlock_spinner_dropdown_item, mChildList);
		//navigation list
        Context context = getSupportActionBar().getThemedContext();
        list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        getSupportActionBar().setListNavigationCallbacks(list, this);
        getSupportActionBar().setTitle(getString(R.string.child_app_info));
        */
	}
	 
    class TH_Loading extends Thread {
		
		public void run() {
			
			try{
				prog_dialog.show();
			}
			catch(Exception e){
				Log.d("MainActivity", "err");
			}
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("MainActivity", "MMM");
	}
	
}
