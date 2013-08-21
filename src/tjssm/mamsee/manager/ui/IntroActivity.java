package tjssm.mamsee.manager.ui;

import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.content.Intent;
import android.os.Handler;
import tjssm.mamsee.manager.R;
import tjssm.mamsee.manager.R.layout;
import tjssm.mamsee.manager.R.menu;

public class IntroActivity extends Activity {

    Handler h;
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
      ///   	getActionBar().hide();
      //  }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_intro);
        h = new Handler();
        //h.postDelayed(irun, 10000);
        h.postDelayed(irun, 500);//
    }

    Runnable irun = new Runnable() {
        public void run() {

            Intent intent;
            intent = new Intent(IntroActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        h.removeCallbacks(irun);
    }

    
}
