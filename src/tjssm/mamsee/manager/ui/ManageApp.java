package tjssm.mamsee.manager.ui;


import java.util.ArrayList;
import java.util.List;

import tjssm.mamsee.manager.R;
import tjssm.mamsee.manager.R.layout;

import com.actionbarsherlock.app.SherlockFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ManageApp extends SherlockFragment {
	
	ArrayList<AppList> appArrList = new ArrayList<AppList>();
	private ListView mAppList;
	private AppArrayAdapter mArrayAdapter;
	private AppList appList;
	
	
	@Override
	 public void onActivityCreated(Bundle savedInstanceState) {
	  super.onActivityCreated(savedInstanceState);
	   
	  	displayListView();
	   
	 }
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if (container == null) {
		       return null;
		}
		View view = inflater.inflate(R.layout.manage_app, container, false);
		return view;
	}
	private void displayListView() {
		 
		  AppArrayAdapter mArrayAdapter;
		  String [] mName = {"first", "second", "third"};
		  String [] mTime = {"sub1", "sub2", "sub3"};
		  
		  
		  int [] icon = new int[] {R.drawable.collections_cloud, R.drawable.collections_cloud, R.drawable.collections_cloud};
		  //mArrayAdapter = new AppArrayAdapter(getSherlockActivity(), mName, mTime, icon);
		  mArrayAdapter = new AppArrayAdapter(getSherlockActivity(), MainActivity.arrChildApp);
		  ListView listView = (ListView) getView().findViewById(R.id.appListView);
		  
		  listView.setAdapter(mArrayAdapter);
		  listView.setTextFilterEnabled(true);
		  listView.setOnItemClickListener(new OnItemClickListener() {
			   public void onItemClick(AdapterView<?> parent, View view,
			     int position, long id) {
				   
				   
				   
			   }
		  });
	}
	

}
