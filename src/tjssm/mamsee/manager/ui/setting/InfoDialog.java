package tjssm.mamsee.manager.ui.setting;

import tjssm.mamsee.manager.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;

public class InfoDialog extends Activity {

	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting_info_dialog);
        Intent i = getIntent();
        String msg = i.getExtras().get("MSG").toString();
        String title = i.getExtras().get("TITLE").toString();
        TextView tv_title = (TextView)findViewById(R.id.cdelete_title);
        TextView tv1 = (TextView)findViewById(R.id.cdelete_contents);
        tv1.setText(msg);
       	tv_title.setText(title);
	}
	
}
