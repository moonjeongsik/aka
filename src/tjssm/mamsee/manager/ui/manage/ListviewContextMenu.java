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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class ListviewContextMenu extends Dialog implements
    android.view.View.OnClickListener, OnCheckedChangeListener {

  public Activity mActivity;
  private Button bOK;
  private Button bCancel;
  private TimePicker timePicker;
  private int mHourOfDay;
  private int mMinute;
  private int mOption;
  private ListviewContextMenu mDateTimePicker;
  
  public ListviewContextMenu(Activity a) {
	  	super(a);
	  	this.mActivity = a;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.list_menu);
	    
	    /* RelativeLayout rlayout = (RelativeLayout)findViewById(R.id.relalist_layout);
	    RelativeLayout.LayoutParams paramtext = new RelativeLayout.LayoutParams(
    	RelativeLayout.LayoutParams.WRAP_CONTENT,
    	RelativeLayout.LayoutParams.WRAP_CONTENT);
	    //paramtext.setMargins(-50, -54, 0, 0); 
	    rlayout.setLayoutParams(paramtext);
	    */
	    
  }
  @Override
  public void onClick(View v) {
	  
  }
	
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		
	}

}



