package tjssm.mamsee.manager.ui;

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
	    Calendar c;
	    c = Calendar.getInstance(); 
	    int curHourOfDay = c.get(Calendar.HOUR_OF_DAY); 
	    int curMinute = c.get(Calendar.MINUTE);   
		UpdateTimeChecker(curHourOfDay, curMinute);
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



/*
import java.util.Calendar;

import tjssm.mamsee.manager.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker.OnTimeChangedListener;


public class DateTimePicker extends LinearLayout {

 public interface OnDateTimeChangedListener {
  void onDateTimeChanged(DateTimePicker view, int year, int monthOfYear,
    int dayOfMonth, int hourOfDay, int minute);
 }

 private OnDateTimeChangedListener onDateTimeChangedListener;
 private final DatePicker datePicker;
 private final CheckBox enableTimeCheckBox;
 private final TimePicker timePicker;

 public DateTimePicker(final Context context) {
  this(context, null);
 }

 public DateTimePicker(final Context context, final AttributeSet attrs) {
  super(context, attrs);

  // Layout
  LayoutInflater inflater = (LayoutInflater) context
    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  inflater.inflate(R.layout.datetime_picker, this, true);

  // Attribute
  Calendar calendar = Calendar.getInstance();
  TypedArray a = context.obtainStyledAttributes(attrs,
    R.styleable.DataTimePicker);
  final int _currentYear = a.getInt(R.styleable.DataTimePicker_year,
    calendar.get(Calendar.YEAR));
  final int _currentMonth = a.getInt(R.styleable.DataTimePicker_month,
    calendar.get(Calendar.MONTH));
  final int _currentDay = a.getInt(R.styleable.DataTimePicker_day,
    calendar.get(Calendar.DAY_OF_MONTH));
  final int _currentHour = a.getInt(R.styleable.DataTimePicker_hour,
    calendar.get(Calendar.HOUR_OF_DAY));
  final int _currentMinute = a.getInt(R.styleable.DataTimePicker_minute,
    calendar.get(Calendar.MINUTE));

  // DatePicker
  datePicker = (DatePicker) findViewById(R.id._babukuma_datetime_picker_date_picker);
  datePicker.init(_currentYear, _currentMonth, _currentDay,
    new OnDateChangedListener() {

     @Override
     public void onDateChanged(final DatePicker view,
       final int year, final int monthOfYear,
       final int dayOfMonth) {
      if (onDateTimeChangedListener != null) {
       onDateTimeChangedListener.onDateTimeChanged(
         DateTimePicker.this, year, monthOfYear,
         dayOfMonth, timePicker.getCurrentHour(),
         timePicker.getCurrentMinute());
      }
     }
    });

  // Enable Time checkbox
  enableTimeCheckBox = (CheckBox) findViewById(R.id._babukuma_datetime_picker_enable_time);
  enableTimeCheckBox
    .setOnCheckedChangeListener(new OnCheckedChangeListener() {
     @Override
     public void onCheckedChanged(CompoundButton buttonView,
       boolean isChecked) {
      timePicker.setEnabled(isChecked);
      timePicker.setVisibility((enableTimeCheckBox
        .isChecked() ? View.VISIBLE : View.INVISIBLE));
     }
    });

  // TimePicker
  timePicker = (TimePicker) findViewById(R.id._babukuma_datetime_picker_time_picker);
  timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
   @Override
   public void onTimeChanged(final TimePicker view,
     final int hourOfDay, final int minute) {
    if (onDateTimeChangedListener != null) {
     onDateTimeChangedListener.onDateTimeChanged(
       DateTimePicker.this, datePicker.getYear(),
       datePicker.getMonth(), datePicker.getDayOfMonth(),
       hourOfDay, minute);
    }
   }
  });
  timePicker.setCurrentHour(_currentHour);
  timePicker.setCurrentMinute(_currentMinute);
  timePicker.setEnabled(enableTimeCheckBox.isChecked());
  timePicker.setVisibility((enableTimeCheckBox.isChecked() ? View.VISIBLE
    : View.INVISIBLE));
 }

 public void setOnDateTimeChangedListener(
   OnDateTimeChangedListener onDateTimeChangedListener) {
  this.onDateTimeChangedListener = onDateTimeChangedListener;
 }

 public void updateDateTime(int year, int monthOfYear, int dayOfMonth,
   int currentHour, int currentMinute) {
  datePicker.updateDate(year, monthOfYear, dayOfMonth);
  timePicker.setCurrentHour(currentHour);
  timePicker.setCurrentMinute(currentMinute);
 }

 public void updateDate(int year, int monthOfYear, int dayOfMonth) {
  datePicker.updateDate(year, monthOfYear, dayOfMonth);
 }

 public void setIs24HourView(final boolean is24HourView) {
  timePicker.setIs24HourView(is24HourView);
 }

 public int getYear() {
  return datePicker.getYear();
 }

 public int getMonth() {
  return datePicker.getMonth();
 }

 public int getDayOfMonth() {
  return datePicker.getDayOfMonth();
 }

 public int getCurrentHour() {
  return timePicker.getCurrentHour();
 }

 public int getCurrentMinute() {
  return timePicker.getCurrentMinute();
 }

 public boolean enableTime() {
  return enableTimeCheckBox.isChecked();
 }
}

*/
