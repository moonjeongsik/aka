package tjssm.mamsee.manager.ui;


import tjssm.mamsee.manager.R;
import tjssm.mamsee.manager.R.layout;

import com.actionbarsherlock.app.SherlockFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ManageApp extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.manage_app, container, false);
		
		return rootView;
	}

}
