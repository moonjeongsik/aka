package tjssm.mamsee.manager.ui.setting;

import tjssm.mamsee.manager.R;
import tjssm.mamsee.manager.R.layout;

import com.actionbarsherlock.app.SherlockFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingAccount extends SherlockFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.setting_account, container, false);
		return rootView;
	}
}
