package tjssm.mamsee.manager.ui.manage;

import java.util.ArrayList;

import tjssm.mamsee.manager.R;
import tjssm.mamsee.manager.http.SetAppOption;
import tjssm.mamsee.manager.http.SetAppOptionTime;
import tjssm.mamsee.manager.localdb.AppManageDBAdapter;
import tjssm.mamsee.manager.ui.MainActivity;
import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.CheckBox;

@SuppressLint("NewApi")
public class AppListDialog extends Dialog implements
    android.view.View.OnClickListener, OnCheckedChangeListener, android.widget.CompoundButton.OnCheckedChangeListener {

  private final int TIMELIMITSETTING_ON = 10; 
  private final int TIMELIMITSETTING_OFF = 0; 
  private final int TIMETERMSETTING_ON = 100; 
  private final int TIMETERMSETTING_OFF = 0; 	
	 
  private final static int OPTION_PASS 	= 0;
  private final static int OPTION_BLOCK 	= 1;
  private final static int OPTION_TIME 	= 2;
  public final static int OPTION_START		= 0;
  public final static int OPTION_FINISH	= 1;
  public final static int OPTION_TOTAL		= 3;
  
  public Activity mActivity;
  public Dialog dlg;
  public Button yes, no;
  private RadioGroup ColGroup;
  private String mApp_name;
  private String mPackage_name;
  private String mC_id;
  private int mOption, savedmOption;
  public static int mStart_HourOfDay;
  public static int mStart_Minute;
  public static int mFinish_HourOfDay;
  public static int mFinish_Minute;
  public static int mTotal_HourOfDay;
  public static int mTotal_Minute;
  
  private static LinearLayout timeTermlayout;
  private LinearLayout dateLayout;
  private LinearLayout tLimitOption;
  private LinearLayout tTotalLimit;
  private static Button bStart_time;
  private static Button bFinish_time;
  private static Button bTotal_time;
  private static Button bInsert_time;
  private static ListView timeListView;
  private static ArrayAdapter<String> arrTimeListAdapter = null;
  private TextView tTimeTermTitle;
  private TextView tTimeLimitTitle;
  private CheckBox ck_timeterm;
  private CheckBox ck_timelimit;
  private int ftimeterm_checked=0;
  private int ftimelimit_checked=0;
  private static int curStartTime;
  private static int curFinishTime;
  private static int curTotalTime;
  private ArrayList<Integer> arrTimeTerm;
  private AppManageDBAdapter mDbAdapter;
  private ArrayList<Integer> applist;
  private ArrayList<String> str_timelist;
  private SetAppOption mSetAppOption;
  private SetAppOptionTime mSetAppOptionTime;
  
  public AppListDialog(Activity a, String app_name, String package_name, String c_id) {
	  	super(a);
	  	this.mActivity = a;
	  	this.mApp_name = app_name;
	  	this.mPackage_name = package_name;
	  	this.mC_id = c_id;
	  	
	  	//기존 옵션 불러오기
	  	mDbAdapter = new AppManageDBAdapter(mActivity);
    	mDbAdapter.open();
    	int[] arrOption = mDbAdapter.searchAppOption(mPackage_name);
    	if(arrOption != null) {
    		Log.d("AppListDialog", "1:"+arrOption[0]+",  2:"+arrOption[1]+",  3:"+arrOption[2]+",  4:"+arrOption[3]);
    		this.mOption = arrOption[0];
    		savedmOption 	= arrOption[0];
    		curTotalTime 	= arrOption[1];
	    	curStartTime 	= 0;
		    curFinishTime 	= 0;
        	//curStartTime = arrOption[2];
    	    //curFinishTime = arrOption[3];
    	}
    	else {
    		Log.d("AppListDialog", "err");
    	   	this.mOption 	= OPTION_PASS;
    	   	savedmOption 	= OPTION_PASS;
    	   	curTotalTime 	= 0;
	    	curStartTime 	= -1;
		    curFinishTime 	= -1;
    	}
	    arrTimeTerm = new ArrayList<Integer>();
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.applist_dialog);
	    
	    //Dialog 기본 UI 관련 
	    TextView title =  (TextView)findViewById(R.id.title_applist);
	    RadioGroup ColGroup = (RadioGroup)findViewById(R.id.rgroup_applist);
	    
	    RadioButton radio_pass  = (RadioButton)findViewById(R.id.r_app_pass);
	    RadioButton radio_block = (RadioButton)findViewById(R.id.r_app_block);
	    RadioButton radio_limit = (RadioButton)findViewById(R.id.r_app_time);
	    
	    ColGroup.setOnCheckedChangeListener(this);
	    title.setText(mApp_name);
	    yes = (Button) findViewById(R.id.btn_yes);
	    no = (Button) findViewById(R.id.btn_no);
	    yes.setOnClickListener(this);
	    no.setOnClickListener(this);
	    
	    
	    mSetAppOption = new SetAppOption();
	    mSetAppOptionTime = new SetAppOptionTime();
	    
	    //옵션에 따른 동적 메뉴 생성
	    MakeLimitOptionCheckBox();
	    MakeTimeLimitBar();
	    MakeTimeTermBar();
	    
	    if(savedmOption == OPTION_PASS) {
	    	radio_pass.setChecked(true);
		    radio_block.setChecked(false);
		    radio_limit.setChecked(false);
	    }
    	else if(savedmOption == OPTION_BLOCK) {
    		radio_pass.setChecked(false);
		    radio_block.setChecked(true);
		    radio_limit.setChecked(false);
    	}
    	else{
    		radio_pass.setChecked(false);
		    radio_block.setChecked(false);
		    radio_limit.setChecked(true);
		    Log.d("AppListDialog", "Aaa: "+ mOption);
		    if(savedmOption == (TIMELIMITSETTING_ON + TIMETERMSETTING_ON)) {
		    	bTotal_time.setText(ConvertTimeFormat(curTotalTime));
		    	//bStart_time.setText(ConvertTimeFormat(curStartTime));
		    	//bFinish_time.setText(ConvertTimeFormat(curFinishTime));
		    	ck_timeterm.setChecked(true);
			    ck_timelimit.setChecked(true); 
		    }
		    else if(savedmOption == TIMETERMSETTING_ON) {
		    	ck_timeterm.setChecked(true);
		    }
		    else if(savedmOption == TIMELIMITSETTING_ON) {
		    	bTotal_time.setText(ConvertTimeFormat(curTotalTime));
			    ck_timelimit.setChecked(true);
		    }
    	}
	    
	    
	    
  }
  private void MakeLimitOptionCheckBox() {
	    tLimitOption = (LinearLayout) this.findViewById(R.id.timelimit_option); 
	    tLimitOption.setOrientation(LinearLayout.HORIZONTAL);
	    ck_timeterm = new CheckBox(mActivity);
	    ck_timelimit = new CheckBox(mActivity);
	    ck_timeterm.setText("구간설정");
	    ck_timelimit.setText("시간설정");
	    ck_timeterm.setTextColor(Color.parseColor("#A9A9A9"));
	    ck_timelimit.setTextColor(Color.parseColor("#A9A9A9"));//Color.parseColor("#5DBCD2"));
	    ck_timeterm.setId(0);
	    ck_timelimit.setId(1);
	    ck_timeterm.setWidth(0);
	    ck_timelimit.setWidth(0);
	    ck_timeterm.setLayoutParams(new LinearLayout.LayoutParams(0,
	    	    LayoutParams.WRAP_CONTENT, 1));
	    ck_timelimit.setLayoutParams(new LinearLayout.LayoutParams(0,
	    	    LayoutParams.WRAP_CONTENT, 1));
	    ck_timelimit.setOnCheckedChangeListener(this);
	    ck_timeterm.setOnCheckedChangeListener(this);
	    
  }
  private void MakeTimeLimitBar() {
	    tTotalLimit = (LinearLayout) this.findViewById(R.id.total_timelimit);
	    tTotalLimit.setOrientation(LinearLayout.VERTICAL);
	    tTimeTermTitle = new TextView(mActivity);
	    tTimeTermTitle.setText("하루 사용 시간 설정");
	    tTimeTermTitle.setTextSize(15);
	    tTimeTermTitle.setTextColor(Color.parseColor("#5DBCD2"));
	    
	    bTotal_time = new Button(mActivity);
    	if(curTotalTime != 0)
    		bTotal_time.setText(ConvertTimeFormat(curTotalTime));
    	else 
    		bTotal_time.setText("사용 시간 설정");
	    bTotal_time.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
	    		LayoutParams.WRAP_CONTENT));
	    bTotal_time.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DateTimePicker timePicker_dlg = new DateTimePicker(mActivity, OPTION_TOTAL);
				timePicker_dlg.show(); 
			}
		});
  }



  private void MakeTimeTermBar() {
	    dateLayout = (LinearLayout) this.findViewById(R.id.date_select_table); 
	    dateLayout.setOrientation(LinearLayout.VERTICAL);
	    tTimeLimitTitle = new TextView(mActivity);
	    tTimeLimitTitle.setText("사용 구간 설정");
	    tTimeLimitTitle.setPadding(10, 0, 0, 0);
	    tTimeLimitTitle.setTextColor(Color.parseColor("#5DBCD2"));
	    tTimeLimitTitle.setTextSize(15);
	    bStart_time = new Button(mActivity);
	    bStart_time.setText("시작 시간");
	    bStart_time.setLayoutParams(new LinearLayout.LayoutParams(0,
	    	    LayoutParams.WRAP_CONTENT, 1));
	    bStart_time.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DateTimePicker timePicker_dlg = new DateTimePicker(mActivity, OPTION_START);
				timePicker_dlg.show();
				//Log.d("AppListDialog", "start time");
			}
		});
	    bFinish_time = new Button(mActivity);
	    bFinish_time.setText("종료 시간");
	    bFinish_time.setLayoutParams(new LinearLayout.LayoutParams(0,
	    	    LayoutParams.WRAP_CONTENT, 1));
	    bFinish_time.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DateTimePicker timePicker_dlg = new DateTimePicker(mActivity, OPTION_FINISH);
				timePicker_dlg.show(); 
				//Log.d("AppListDialog", "Finish time");
			}
	    });
	    bInsert_time = new Button(mActivity);
	    bInsert_time.setText("추가");
	    bInsert_time.setLayoutParams(new LinearLayout.LayoutParams(0,
	    	    LayoutParams.WRAP_CONTENT, 1));
	    bInsert_time.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean checkErr = false;
				if(curStartTime == curFinishTime) {
					checkErr = true;
					ToastAll(mActivity, "잘못된 시간입니다.");
				}
				if(mDbAdapter.searchTime(mPackage_name, curStartTime, curFinishTime) == true) {
					checkErr = true;
					ToastAll(mActivity, "이미 등록하신 시간입니다.");
				}
				/*if(curStartTime > curFinishTime) {
					checkErr = true;
					ToastAll(mActivity, "잘못된 시간입니다");
				}*/
				
				if(checkErr == false) {
					
					if(curStartTime < 0 && curFinishTime < 0) {
						ToastAll(mActivity, "시간을 입력해주세요.");
						return;
					}
					
					mDbAdapter.insertTimeTable(mPackage_name, curStartTime, curFinishTime);
				   	String sm = String.format("%02d", curStartTime%60);
				  	String sh = String.format("%02d", curStartTime/60);
				  	String fm = String.format("%02d", curFinishTime%60);
				  	String fh = String.format("%02d", curFinishTime/60);
				  	String stime = sh +":"+sm;
				  	String ftime = fh +":"+fm;
					ToastAll(mActivity, stime+"~"+ftime+"이 등록되었습니다.");
					updateTimeList();
				}
				/*if(mDbAdapter.searchTimeTableIsExist(mPackage_name) == false) {
					if(checkErr == false)
						mDbAdapter.insertTimeTable(mPackage_name, curStartTime, curFinishTime);
					Log.d("AppListDialog", "checkErr"+checkErr);
				}
				else { 
					if(checkErr == false)
						mDbAdapter.updateTimetable(mPackage_name, curStartTime, curFinishTime);
				}*/
			}
	    });
	    
	    
	    timeListView = new ListView(mActivity);
	   
	    boolean bbb = mDbAdapter.searchTimeTableIsExist(mPackage_name);
	    Log.d("AppListDialog", "isbool?:"+bbb);
	    applist = mDbAdapter.searchTimeTable(mPackage_name);
	    if(applist == null)
	    	Log.d("AppListDialog", "list null");
	    else 
	    	Log.d("AppListDialog", "list num :" + applist.size());
	    
	    str_timelist = new ArrayList<String>();
	    for(int i=0; i< (applist.size()/2) ; i++) {
		   	String sm = String.format("%02d", applist.get(i*2)%60);
		  	String sh = String.format("%02d", applist.get(i*2)/60);
		  	String fm = String.format("%02d", applist.get(i*2+1)%60);
		  	String fh = String.format("%02d", applist.get(i*2+1)/60);
		  	String stime = sh +":"+sm;
		  	String ftime = fh +":"+fm;
		  	Log.d("AppListDialog", "stime :"+stime+", ftime:"+ftime);
		  	str_timelist.add(stime+"  ~  "+ftime);
	    }
	    
	    Log.d("AppListDialog", "list Load complete");
	    //str_timelist.add("10:20  ~  11:20");
	    
	    if(str_timelist.size() > 4) {
		    timeListView.setLayoutParams(new LinearLayout
		    		.LayoutParams(LayoutParams.MATCH_PARENT,220));
	    }
	    arrTimeListAdapter = new ArrayAdapter(mActivity, 
	    		R.layout.timetermlist, str_timelist);
	    timeListView.setAdapter(arrTimeListAdapter);
	    //registerForContextMenu(timeListView);
	 /*   timeListView.setOnCreateContextMenuListener
	    (
	      new View.OnCreateContextMenuListener() 
	      {
	            public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) 
	            {
	            	AdapterContextMenuInfo mi =(AdapterContextMenuInfo) menuInfo;
	            	menu.add(0, 0, 0, "삭제");                
	            }
	       }
	    );
	   */ 
	    timeListView.setOnItemClickListener(listViewClickListener);
        // 리스트뷰의 하단이 보여지도록 설정한다.
	    //timeListView.setSelection(timeListView.getCount() - 1);
	    
	    timeTermlayout = new LinearLayout(mActivity);
	    timeTermlayout.setOrientation(LinearLayout.HORIZONTAL);
	    //timeTermlayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,
	    //	    LayoutParams.WRAP_CONTENT));
	    timeTermlayout.addView(bStart_time);
	    timeTermlayout.addView(bFinish_time);
	    timeTermlayout.addView(bInsert_time);
	    
	   
  }
  
  OnItemClickListener listViewClickListener = new OnItemClickListener()
  {
		@SuppressLint("NewApi")
		@Override
		public void onItemClick(AdapterView<?> parentView, View clickedView, final int position, long id) {

			//timeListView.startActionMode(modeCallBack);
   	        //view.setSelected(true);
			String[] menu_str = new String[]{"삭제"};
			
			new AlertDialog.Builder(mActivity).setTitle("")
			.setItems(menu_str, new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog, int which) {
					String[] temp= ((String) timeListView.getItemAtPosition(position)).split("~");
					Log.d("AppListDialog", ""+applist.get(position*2) + ", "+applist.get(position*2+1));
					mDbAdapter.deleteTimetable(mPackage_name, applist.get(position*2), applist.get(position*2+1));
					ToastAll(mActivity, timeListView.getItemAtPosition(position)+"이 삭제되었습니다");
					updateTimeList();
				}
			}).setNegativeButton("",null).show();
			
		}
  };
  
  public void updateTimeList() {
	  	applist = mDbAdapter.searchTimeTable(mPackage_name);
	  	
	  	if(applist == null)
	    	Log.d("AppListDialog", "list null");
	    else 
	    	Log.d("AppListDialog", "list num :" + applist.size());
	    str_timelist.clear();
	    for(int i=0; i< (applist.size()/2) ; i++) {
		   	String sm = String.format("%02d", applist.get(i*2)%60);
		  	String sh = String.format("%02d", applist.get(i*2)/60);
		  	String fm = String.format("%02d", applist.get(i*2+1)%60);
		  	String fh = String.format("%02d", applist.get(i*2+1)/60);
		  	String stime = sh +":"+sm;
		  	String ftime = fh +":"+fm;
		  	Log.d("AppListDialog", "stime :"+stime+", ftime:"+ftime);
		  	str_timelist.add(stime+"  ~  "+ftime);
	    }
	    arrTimeListAdapter = new ArrayAdapter(mActivity, 
	    		R.layout.timetermlist, str_timelist);
	    if(str_timelist.size() > 4) {
		    timeListView.setLayoutParams(new LinearLayout
		    		.LayoutParams(LayoutParams.MATCH_PARENT,220));
	    }
	    arrTimeListAdapter = new ArrayAdapter(mActivity, 
	    		R.layout.timetermlist, str_timelist);
	    timeListView.setAdapter(arrTimeListAdapter);
  }
  
  
  @Override
	 public void onCheckedChanged(RadioGroup group, int checkedId) {
	  // TODO Auto-generated method stub
	  
	  switch(checkedId){
	  case R.id.r_app_pass:
		  mOption = OPTION_PASS;
		  tLimitOption.removeAllViews();
		  tTotalLimit.removeAllViews();
  		  dateLayout.removeAllViews();
	   break;
	  case R.id.r_app_block:
		  mOption = OPTION_BLOCK;
		  tLimitOption.removeAllViews();
		  tTotalLimit.removeAllViews();
  		  dateLayout.removeAllViews();
		  break;
	  case R.id.r_app_time:
		  mOption = OPTION_TIME;
		  tLimitOption.addView(ck_timelimit);
		  tLimitOption.addView(ck_timeterm);
		  //check확인, 
		  if(ck_timeterm.isChecked()) {
			  	ftimeterm_checked = TIMETERMSETTING_ON;
				dateLayout.addView(tTimeLimitTitle);
				dateLayout.addView(timeTermlayout);
				dateLayout.addView(timeListView);
		  }
		  if(ck_timelimit.isChecked()) {
			  	ftimelimit_checked = TIMELIMITSETTING_ON;
				tTotalLimit.addView(tTimeTermTitle);
			    tTotalLimit.addView(bTotal_time);	
		  }
		  
		  
		  Log.d("AppListDialog", "TT check");
		  break;
	  }
	 }

  @Override
  public void onClick(View v) {
	    switch (v.getId()) {
	    case R.id.btn_yes:
	    	int option=OPTION_PASS;
	    	if(mOption == OPTION_PASS) {
	    		option = OPTION_PASS;
	    		curTotalTime = 0;
	    		curStartTime = 0;
	    		curFinishTime = 0;
	    	}
	    	else if(mOption == OPTION_BLOCK) {
	    		option = OPTION_BLOCK;
	    		curTotalTime = 0;
	    		curStartTime = 0;
	    		curFinishTime = 0;
	    	}
	    	else if(mOption == OPTION_TIME) {

	    		if(ftimeterm_checked == TIMETERMSETTING_ON && ftimelimit_checked == TIMELIMITSETTING_ON) {
	    			mOption = TIMELIMITSETTING_ON + TIMETERMSETTING_ON;
	    			ck_timeterm.setChecked(true);
	    			ck_timelimit.setChecked(true);
	    		}
	    		else if(ftimeterm_checked == TIMETERMSETTING_ON) {
	    			mOption = TIMETERMSETTING_ON;
	    			ck_timeterm.setChecked(true);
	    		}
	    		else if(ftimelimit_checked == TIMELIMITSETTING_ON) {
	    			mOption = TIMELIMITSETTING_ON;
	    			ck_timelimit.setChecked(true);
	    		}
	    		else {
	    			mOption = OPTION_TIME;
	    		}
	    	}
	    	boolean isinserted = mDbAdapter.searchIsInserted(mPackage_name);
	    	if(isinserted == true) {
	    		mDbAdapter.updateAppOption(mPackage_name, mOption, curTotalTime, curStartTime, curFinishTime);
	    		//Log.d("AppListDialog", "update 1:"+mOption+",  2:"+curTotalTime+",  3:"+curStartTime+",  4:"+curFinishTime);
	    	}
	    	else {
	    		mDbAdapter.insertAppOption(mPackage_name, mOption, curTotalTime, curStartTime, curFinishTime);
	    		//Log.d("AppListDialog", "insert 1:"+mOption+",  2:"+curTotalTime+",  3:"+curStartTime+",  4:"+curFinishTime);
	    	}
	    	Send_Option_Data(mOption, curTotalTime, applist);
	    	
	    	break;
	    case R.id.btn_no:
	    	
	    	break;
	    default:
	    	break;
	    }
	    dismiss();
  }
  
  private void Send_Option_Data(int mOption, int total_limit_time, ArrayList<Integer> arr_time_term ){
	  int arr_size = arr_time_term.size();
	  
	  mSetAppOptionTime.DelChildAppOptionTime(MainActivity.cur_child_id, mPackage_name);
	  for(int i=0; i<(arr_size/2); i++) {
		  Log.d("SetAppOption", arr_time_term.get(i*2)+", "+arr_time_term.get(i*2+1));
		  mSetAppOptionTime.SetChildAppOptionTime(MainActivity.cur_child_id,
				  mPackage_name, arr_time_term.get(i*2), arr_time_term.get(i*2+1));
	  }
	  mSetAppOption.SetChildAppList(MainActivity.cur_child_id, mPackage_name, curTotalTime, mOption);
  }
  
  public static void UpdateSelectedTime(int sel, int hourOfDay, int minute){
	  
   	  String m = String.format("%02d", minute);
	  String h = String.format("%02d", hourOfDay);
	  String time = h +" : "+m;
	  
	  if(sel == OPTION_START) {
		  mStart_HourOfDay = hourOfDay; 
		  mStart_Minute = minute;
		  //String sTime = ConvertTimeFomat(mStart_HourOfDay, mStart_Minute);
		  bStart_time.setText(time);
		  curStartTime = hourOfDay*60 + minute;
	  }
	  else if(sel == OPTION_FINISH) {
		  mFinish_HourOfDay = hourOfDay; 
		  mFinish_Minute = minute;		 
		  //String fTime = ConvertTimeFomat(mFinish_HourOfDay, mFinish_Minute); 
		  bFinish_time.setText(time);
		  curFinishTime = hourOfDay*60 + minute;
	  }
	  else if(sel == OPTION_TOTAL) {
		  mTotal_HourOfDay = hourOfDay; 
		  mTotal_Minute = minute;
		  //String fTime = ConvertTimeFomat(mTotal_HourOfDay, mTotal_Minute); 
		  //String tTime = hourOfDay+"시  "+minute+"분";
		  bTotal_time.setText(time);
		  curTotalTime = hourOfDay*60 + minute;
	  }
  }
  private String ConvertTimeFormat(int minute) {
	  String time = null;
	  int h = minute/60;
	  int m = minute%60;
	  String sh = String.format("%02d", h);
	  String sm = String.format("%02d", m);
	  return sh +" : "+sm;
  }

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		
		if(buttonView.getId() == 0) {//term
			if (isChecked) {      
				ftimeterm_checked = TIMETERMSETTING_ON;
				dateLayout.addView(tTimeLimitTitle);
				dateLayout.addView(timeTermlayout);
				dateLayout.addView(timeListView);
				//Log.d("AppListDialog", "ft term  on");
			}
			else {             
				ftimeterm_checked = TIMETERMSETTING_OFF;
				dateLayout.removeAllViews();
				//Log.d("AppListDialog", "ft term  off");
			}
		}
		else if(buttonView.getId() == 1) {//limit
			if (isChecked) {
				ftimelimit_checked = TIMELIMITSETTING_ON;
				tTotalLimit.addView(tTimeTermTitle);
			    tTotalLimit.addView(bTotal_time);	
			    //Log.d("AppListDialog", "ft limit on");
			}
			else {
				ftimelimit_checked = TIMELIMITSETTING_OFF;
			    tTotalLimit.removeAllViews();
			    //Log.d("AppListDialog", "ft limit off");
			}
		}
	}
  
 

//토스트 중복방지를 위해 설정 토스트가 안보일때 false 보일때 true 기본설정은 당연히 false
	boolean flag = false;
	public void ToastAll(Context context, String case_toast) {

			 LayoutInflater inflater = (LayoutInflater) context
					 	.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			 
			 View layout = inflater.inflate(R.layout.custom_toast,
					 (ViewGroup) findViewById(R.id.gggg));
			//ImageView image = (ImageView) layout.findViewById(R.id.imageView1);
			 TextView text = (TextView) layout.findViewById(R.id.textView111);
			 Toast mToast = new Toast(context.getApplicationContext());
			//image.setImageResource(R.drawable.ic_launcher);
			 text.setText(case_toast);
			 mToast.setGravity(Gravity.CENTER_VERTICAL, 0, 80);
			 mToast.setDuration(Toast.LENGTH_SHORT);	
			 mToast.setView(layout);         
			 if(flag == false){
				 flag = true;
				 mToast.show();
				 new Handler().postDelayed(new Runnable() {
					 @Override
					 public void run() {
						 flag = false;
					 }
				 }, 2000);//토스트 켜져있는 시간동안 핸들러 지연 대충 숏이 2초 조금 넘는거 같다.
			 } 
			 else{
				 Log.e("","토스트 켜져있음");
			 }
		 }
	
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
	    //AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	    //menu.add(Menu.NONE, 0, 0, "삭제1");
	    MenuInflater inflater = mActivity.getMenuInflater();
	    inflater.inflate(R.menu.list_menu, menu);
	    
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		

		
		return true;
	}
	
	/*
	@SuppressLint("NewApi")
	private ActionMode.Callback modeCallBack = new ActionMode.Callback() {

		
		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			return false;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			
		}

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
   	        MenuInflater inflater = mode.getMenuInflater();
		    inflater.inflate(R.menu.list_menu, menu);
			return false;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}
			
	};

	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		 mode.setTitle("Options");
		 mode.getMenuInflater().inflate(R.menu.list_menu, menu);
		 return true;
	}

	*/
	
	
 }
           











/*
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
}*/