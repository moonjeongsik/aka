package tjssm.mamsee.manager.ui.setting;

import java.util.ArrayList;

import tjssm.mamsee.manager.R;
import tjssm.mamsee.manager.R.layout;
import tjssm.mamsee.manager.http.ChildInfo;
import tjssm.mamsee.manager.http.GetChildList;
import tjssm.mamsee.manager.http.GetParentAccount;
import tjssm.mamsee.manager.ui.MainActivity;
import tjssm.mamsee.manager.ui.manage.AppArrayAdapter;
import tjssm.mamsee.manager.ui.manage.AppListDialog;

import com.actionbarsherlock.app.SherlockFragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SettingChild extends SherlockFragment {
	
	private ListView mChildList;
	private ChildListAdapter mArrayAdapter;
	private GetChildList mGetList;
	private ArrayList<ChildInfo> arrChildInfo;
	
	@Override
	 public void onActivityCreated(Bundle savedInstanceState) {
	  super.onActivityCreated(savedInstanceState);
	  	mGetList = new GetChildList();
	 }

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.setting_child, container, false);
		return rootView;
	}
	private void displayListView() {
		
		  MainActivity.arrChildInfo = new ArrayList<ChildInfo>();
		  MainActivity.arrChildInfo = mGetList.Get_Child_List(MainActivity.cur_p_id);
		  mArrayAdapter = new ChildListAdapter(getSherlockActivity(), MainActivity.arrChildInfo);
		  ListView listView = (ListView) getView().findViewById(R.id.settingChildList);
		  listView.setAdapter(mArrayAdapter);
		  listView.setTextFilterEnabled(true);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("DeleteDialog", "Onresume:"+MainActivity.cur_p_id);
		displayListView();
		/*ArrayList<ChildInfo> arrCh = mGetList.Get_Child_List(MainActivity.cur_p_id);
		for(int i=0; i < arrCh.size(); i++) {
			Log.d("DeleteDialog", ":"+arrCh.get(i).m_child_name);
		}*/
	}

}
