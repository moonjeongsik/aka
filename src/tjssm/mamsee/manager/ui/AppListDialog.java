package tjssm.mamsee.manager.ui;

import tjssm.mamsee.manager.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class AppListDialog extends Dialog implements
    android.view.View.OnClickListener, OnCheckedChangeListener {

  private final static int OPTION_PASS 	= 0;
  private final static int OPTION_BLOCK 	= 1;
  private final static int OPTION_TIME 	= 2;
  public final static int OPTION_START	= 0;
  public final static int OPTION_FINISH	= 1;
  
  public Activity mActivity;
  public Dialog dlg;
  public Button yes, no;
  private RadioGroup ColGroup;
  private String mApp_name;
  private String mPackage_name;
  private String mC_id;
  private int mOption;
  public static int mStart_HourOfDay;
  public static int mStart_Minute;
  public static int mFinish_HourOfDay;
  public static int mFinish_Minute;
   
  
  private LinearLayout dateLayout;
  private LinearLayout tLimitOption;
  private static Button bStart_time;
  private static Button bFinish_time;
  private TextView des;
  //private CheckBox ck_timeterm;
  //private CheckBox ck_timelimit;


  
  public AppListDialog(Activity a, String app_name, String package_name, String c_id) {
	  	super(a);
	  	this.mActivity = a;
	  	this.mApp_name = app_name;
	  	this.mPackage_name = package_name;
	  	this.mC_id = c_id;
	  	this.mOption = 0;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.applist_dialog);
	    
	    TextView title =  (TextView)findViewById(R.id.title_applist);
	    //date_tbl =  (Button)findViewById(R.id.date_select_table);
	    
	    RadioGroup ColGroup = (RadioGroup)findViewById(R.id.rgroup_applist);
	    ColGroup.setOnCheckedChangeListener(this);

	    
	    tLimitOption = (LinearLayout) this.findViewById(R.id.timelimit_option); 
	    tLimitOption.setOrientation(LinearLayout.HORIZONTAL);
	    
	    //CheckBox ck_timeterm;
	    //CheckBox ck_timelimit;
	    
	    
	    
	    
	    dateLayout = (LinearLayout) this.findViewById(R.id.date_select_table); 
	    dateLayout.setOrientation(LinearLayout.VERTICAL);
	    
	    bStart_time = new Button(mActivity);
	    bStart_time.setText("시작 시간");
	    bStart_time.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DateTimePicker timePicker_dlg = new DateTimePicker(mActivity, OPTION_START);
				timePicker_dlg.show(); 
				Log.d("AppListDialog", "start time");
			}
		});
	    bFinish_time = new Button(mActivity);
	    bFinish_time.setText("종료 시간");
	    bFinish_time.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DateTimePicker timePicker_dlg = new DateTimePicker(mActivity, OPTION_FINISH);
				timePicker_dlg.show(); 
				Log.d("AppListDialog", "Finish time");
			}
	    });
	    
	    des	= new TextView(mActivity);
	    des.setText("date table");
	    
	    title.setText(mApp_name);
	    yes = (Button) findViewById(R.id.btn_yes);
	    no = (Button) findViewById(R.id.btn_no);
	    yes.setOnClickListener(this);
	    no.setOnClickListener(this);
  }


  
  
  
  @Override
	 public void onCheckedChanged(RadioGroup group, int checkedId) {
	  // TODO Auto-generated method stub
	  
	  switch(checkedId){
	  case R.id.r_app_pass:
		  mOption = OPTION_PASS;
		  dateLayout.removeAllViews();
	   break;
	  case R.id.r_app_block:
		  mOption = OPTION_BLOCK;
		  dateLayout.removeAllViews();
	   break;
	  case R.id.r_app_time:
		  mOption = OPTION_TIME;
		  dateLayout.addView(des);
		  dateLayout.addView(bStart_time);
		  dateLayout.addView(bFinish_time);
		  break;
	  }
	 }


  @Override
  public void onClick(View v) {
	    switch (v.getId()) {
	    case R.id.btn_yes:
	    	if(mOption == OPTION_PASS || mOption == OPTION_BLOCK)
	    		Send_Message_OPTION(0);
	    	else if(mOption == OPTION_TIME)
	    		Send_Message_TIME("0","0");
	    	break;
	    case R.id.btn_no:
	    	dismiss();
	    	break;
	    default:
	    	break;
	    }
	    dismiss();
  }
  
  private void Send_Message_OPTION(int mOption){
	  //send option to server
  }
  private void Send_Message_TIME(String start_time, String finish_time){
	  //send time option to server
  }
  
  public static void UpdateSelectedTime(int sel, int hourOfDay, int minute){
	  if(sel == OPTION_START) {
		  mStart_HourOfDay = hourOfDay; 
		  mStart_Minute = minute;
		  String sTime = ConvertTimeFomat(mStart_HourOfDay, mStart_Minute);
		  bStart_time.setText(sTime);
	  }
	  else if(sel == OPTION_FINISH) {
		  mFinish_HourOfDay = hourOfDay; 
		  mFinish_Minute = minute;		 
		  String fTime = ConvertTimeFomat(mFinish_HourOfDay, mFinish_Minute); 
		  bFinish_time.setText(fTime);
	  }
  }
  
  private static String ConvertTimeFomat(int hour, int minute) {
//	  00:00 ~ 12:00 오전 12:00 =< ~ <24:00 오후
	  String h;
   	  String m = String.format("%02d", minute);
	  
	  if(hour >= 0 && hour < 12) {
		  h = String.format("%02d", hour);
		  return h+"시  "+m+"분" + "   오전";
	  }
	  else if(hour >= 12 && hour < 24){
		  if(hour == 12) {
			  h = String.format("%02d", hour);
			  return h+"시  "+m+"분" + "   오후";
		  }
	      else {
			  hour -= 12;
			  h = String.format("%02d", hour);
			  return h+"시  "+m+"분" + "   오후";
		  }
	  }
	  else if(hour == 24) {
		  hour -= 12;
		  h = String.format("%02d", hour);
		  return h+"시  "+m+"분" + "   오전";
	  }
	return null;
  }
  
  
}