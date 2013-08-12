package tjssm.mamsee.manager.ui.setting;

import tjssm.mamsee.manager.R;
import tjssm.mamsee.manager.R.layout;

import com.actionbarsherlock.app.SherlockFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingNotification extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.setting_notifi, container, false);
		return rootView;
	}

}
