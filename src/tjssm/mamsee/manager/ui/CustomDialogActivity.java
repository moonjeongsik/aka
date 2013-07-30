package tjssm.mamsee.manager.ui;

import tjssm.mamsee.manager.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.TextView;

public class CustomDialogActivity extends Activity {

	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_activity);
        Intent i = getIntent();
        String msg = i.getExtras().get("MSG").toString();
        
        TextView tv1 = (TextView)findViewById(R.id.cdialog_text);
        tv1.setText(msg);
    }
	
}
