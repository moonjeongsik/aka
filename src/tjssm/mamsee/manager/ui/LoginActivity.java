
package tjssm.mamsee.manager.ui;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import tjssm.mamsee.manager.R;
import tjssm.mamsee.manager.http.ChildInfo;
import tjssm.mamsee.manager.http.GetParentAccount;


@SuppressLint("NewApi")
public class LoginActivity extends Activity {

	public static final String TAG = "";
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "sp_sosa@naver.com:1234",
    		"foo@example.com:hello",
            "bar@example.com:world"
    };
    private UserLoginTask mAuthTask = null;
    private GetParentAccount mCheckAcc = null;
    // Values for email and password at the time of the login attempt.
    private String mEmail;
    private String mPassword;
    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    ProgressDialog dialog;
    Handler pro_dial;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
         	getActionBar().hide();
        }
        mCheckAcc = new GetParentAccount();
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    	dialog = new ProgressDialog(LoginActivity.this);
    	//dialog.setTitle("wait");
    	dialog.setMessage("�α��� ���Դϴ�...");	
    	dialog.setCancelable(true);	
    	pro_dial = new Handler();
    }
	
	Runnable irun = new Runnable() {
	    public void run() {
	    	dialog.show();
	    }
	};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    public void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        mEmail = mEmailView.getText().toString();
        mPassword = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;
        String err_msg = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(mPassword)) {
        	err_msg = "�н����带 �Է����ּ���";
        	cancel = true;
        } else if (mPassword.length() < 4) {
        	err_msg = "�н����尡 ª���ϴ�";
        	cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(mEmail)) {
        	err_msg = "Email�� �Է����ּ���";
        	cancel = true;
        } else if (!mEmail.contains("@")) {
        	err_msg = "�ùٸ� Email�� �Է����ּ���";
        	cancel = true;
        }

        if (cancel) {
        	Intent i = new Intent(this, CustomDialogActivity.class);
        	i.putExtra("MSG", err_msg);
        	startActivity(i);
        } else {
            mAuthTask = new UserLoginTask();
            mAuthTask.execute((Void) null);
        	Log.d("MJS", "OKOKOK");
        }
    }

   

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
        	String result;
        	Log.d("TJSSM",mEmail+","+mPassword);
            try {
                // Simulate network access.
            	pro_dial.postDelayed(irun, 10);
            	result = mCheckAcc.CheckParentAccount(mEmail, mPassword);
            	
            	MainActivity.arrChildInfo = mCheckAcc.GetParentChildList(mEmail);
            	//for(int i=0; i<MainActivity.arrChildInfo.size(); i++) {
            	//	Log.d("TJSSM", "PRINT- c_id:"+MainActivity.arrChildInfo.get(i).m_c_id+", name:"+MainActivity.arrChildInfo.get(i).m_child_name+
            	//				", date:"+MainActivity.arrChildInfo.get(i).m_last_acc_date+", is_routed:"+MainActivity.arrChildInfo.get(i).m_is_routed);
            	//}

            	
            	//Log.d("TJSSM", "TRY:"+result);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return false;
            }
            dialog.dismiss();
            
            if(result != null && result.equals("ACCORD")) {
            	Log.d("TJSSM", "ACCORD:"+result);
            	return true;
            }
            else 
            	Log.d("TJSSM", "DISCORD:"+result);
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            //showProgress(false);

            if (success) {
                Intent intent;
                intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                
            } else {
            	String err_msg = "ID �Ǵ� PW�� �߸��Ǿ����ϴ�";
            	Intent i = new Intent(LoginActivity.this, CustomDialogActivity.class);
            	i.putExtra("MSG", err_msg);
            	startActivity(i);
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            //showProgress(false);
        }
    }
}
