package tjssm.mamsee.manager.ui.setting;

import tjssm.mamsee.manager.R;
import tjssm.mamsee.manager.http.DeleteChildMember;
import tjssm.mamsee.manager.ui.MainActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class DeleteDialog extends Activity {

	DeleteChildMember mDeleteMember;
	String p_id;
	String c_id;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting_cdelete_dialog);
        mDeleteMember = new DeleteChildMember();
        Intent i = getIntent();
        p_id = i.getExtras().get("PID").toString();
        c_id = i.getExtras().get("CID").toString();
        TextView tv_title = (TextView)findViewById(R.id.cdelete_title);
        TextView tv1 = (TextView)findViewById(R.id.cdelete_contents);
       	tv_title.setText("Message");
       	tv1.setText("자녀 등록을 해제합니다");
       	
       	Button btn_ok = (Button)findViewById(R.id.cdelete_btn_ok);
       	btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	mDeleteMember.DelMember(p_id, c_id);
            	
            	Log.d("DeleteDialog", "pid:"+p_id+", c_id:"+c_id);
            	finish();
            }
		});
       	Button btn_cancel = (Button)findViewById(R.id.cdelete_btn_cancel);
       	btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	finish();
            }
		});
	}
	
}
