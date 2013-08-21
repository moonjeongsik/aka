package tjssm.mamsee.manager.ui.manage;

import java.util.Calendar;
import java.util.Date;

import tjssm.mamsee.manager.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class DateTimePicker extends Dialog implements
    android.view.View.OnClickListener, OnCheckedChangeListener {

  public Activity mActivity;
  private Button bOK;
  private Button bCancel;
  private TimePicker timePicker;
  private int mHourOfDay;
  private int mMinute;
  private int mOption;
  private DateTimePicker mDateTimePicker;
  
  public DateTimePicker(Activity a, int mOption) {
	  	super(a);
	  	this.mActivity = a;
	  	this.mOption = mOption;
  }
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.datetime_picker);
	    
	    mDateTimePicker = this;
	    bOK 	= (Button)findViewById(R.id.btn_ok);
	    bCancel = (Button)findViewById(R.id.btn_cancel);
	    bOK.setOnClickListener(this);
	    bCancel.setOnClickListener(this);
	    
	    // TimePicker
	    timePicker = (TimePicker) findViewById(R.id.time_picker);
	    timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
		     @Override
		     public void onTimeChanged(final TimePicker view,
		    		 final int hourOfDay, final int minute) {
	    	 		//Log.d("AppListDialog", "DataTimePicker, h:"+hourOfDay + ", m:"+minute);
	    	 		UpdateTimeChecker(hourOfDay, minute);
		      }
	     });
	    if(mOption != AppListDialog.OPTION_TOTAL) {
		    Calendar c;
		    c = Calendar.getInstance(); 
		    int curHourOfDay = c.get(Calendar.HOUR_OF_DAY); 
		    int curMinute = c.get(Calendar.MINUTE);   
			UpdateTimeChecker(curHourOfDay, curMinute);
	    }
	    else {
	    	int curHourOfDay = 0; 
		    int curMinute = 0;   
			UpdateTimeChecker(curHourOfDay, curMinute);
			timePicker.setIs24HourView(true);
	    }
		timePicker.setCurrentHour(0);
		timePicker.setCurrentMinute(0);
  }
  private void UpdateTimeChecker(int hourOfDay, int minute){
	  this.mHourOfDay = hourOfDay; 
	  this.mMinute = minute;
  }
  
  
  @Override
  public void onCheckedChanged(RadioGroup group, int checkedId) {
	  
	  
  }
	
	@Override
	public void onClick(View v) {
		 switch (v.getId()) {
		    case R.id.btn_ok:
		    	AppListDialog.UpdateSelectedTime(mOption, mHourOfDay, mMinute);
		    	dismiss();
		    	break;
		    case R.id.btn_cancel:
		    	dismiss();
		    	break;
		    default:
		    	break;
		    }
	}

}


