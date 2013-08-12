package tjssm.mamsee.manager.mregister;

import tjssm.mamsee.manager.R;
import tjssm.mamsee.manager.http.RegisterMember;
import tjssm.mamsee.manager.ui.CustomDialogActivity;
import tjssm.mamsee.manager.ui.LoginActivity.UserLoginTask;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;



public class MemberRegister extends Activity {

	String RegisterMsg = null;
	RegisterMember regMember;
	String m_email, m_pw, m_name;
	EditText edit_email, edit_pw, edit_name;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.member_register);
		
		edit_email  = (EditText)findViewById(R.id.member_email);
		edit_pw	 	= (EditText)findViewById(R.id.member_pw);
		edit_name   = (EditText)findViewById(R.id.member_name);

		regMember = new RegisterMember();
		findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	m_email = edit_email.getText().toString();
        		m_pw	= edit_pw.getText().toString();
        		m_name	= edit_name.getText().toString();
        		String err_msg=null;
        		boolean cancel=false;
        		 // Check for a valid password.
                if (TextUtils.isEmpty(m_pw)) {
                	err_msg = "�н����带 �Է����ּ���";
                	cancel = true;
                } else if (m_pw.length() < 4) {
                	err_msg = "�н����尡 ª���ϴ�";
                	cancel = true;
                }

                // Check for a valid email address.
                if (TextUtils.isEmpty(m_pw)) {
                	err_msg = "Email�� �Է����ּ���";
                	cancel = true;
                } else if (!m_email.contains("@")) {
                	err_msg = "�ùٸ� Email�� �Է����ּ���";
                	cancel = true;
                }

                if (cancel) {
                	Intent i = new Intent(MemberRegister.this, CustomDialogActivity.class);
                	i.putExtra("MSG", err_msg);
                	i.putExtra("TITLE", "Error");
                	startActivity(i);
                } else {
                	String result = regMember.SendMemberInfo(m_email, m_pw, m_name);
                	Log.d("MemberRegister", "->"+result);
                	if(result.equals("YOU CANT REGISTER")) {
                    	Intent i = new Intent(MemberRegister.this, CustomDialogActivity.class);
                    	i.putExtra("MSG", "�ߺ��� email�Դϴ�.");
                    	i.putExtra("TITLE", "Error");
                    	startActivity(i);                		
                	}
                	else if(result.equals("NEW ACCOUNT")) {
                		Intent i = new Intent(MemberRegister.this, CustomDialogActivity.class);
                    	i.putExtra("MSG", "������ �����Ǿ����ϴ�");
                    	i.putExtra("TITLE", "�˸�");
                    	startActivity(i);
                    	finish();
                	}
                }
            }
        });
		
		
		
		
		
	}

	
	
}
