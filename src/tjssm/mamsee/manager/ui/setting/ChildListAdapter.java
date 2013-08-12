package tjssm.mamsee.manager.ui.setting;


import java.util.ArrayList;

import tjssm.mamsee.manager.R;
import tjssm.mamsee.manager.R.id;
import tjssm.mamsee.manager.R.layout;
import tjssm.mamsee.manager.http.ChildApp;
import tjssm.mamsee.manager.http.ChildInfo;
import tjssm.mamsee.manager.mregister.MemberRegister;
import tjssm.mamsee.manager.ui.CustomDialogActivity;
import tjssm.mamsee.manager.ui.LoginActivity;
import tjssm.mamsee.manager.ui.MainActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class ChildListAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	String[] mChildName;
	Button mShowInfo;
	Button mDeleteChildInfo;
	int[] mIcon;
	ArrayList<ChildInfo> mChildInfo;
	LayoutInflater inflater;
	String m_name;
	String c_id;
	String last_acc_date;
	String isrouted;
 
	public ChildListAdapter(Context context, ArrayList<ChildInfo> childInfo) {
		this.context = context;
		this.mChildInfo = childInfo;
	}

	@Override
	public int getCount() {
		return mChildInfo.size();
	}
	
	@Override
	public Object getItem(int position) {
		return mChildInfo.get(position).m_child_name;
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View itemView = inflater.inflate(R.layout.setting_cmember_list, parent,
					false);
			
			m_name = mChildInfo.get(position).m_child_name;
			c_id = mChildInfo.get(position).m_c_id;
			last_acc_date = mChildInfo.get(position).m_last_acc_date;
			isrouted = mChildInfo.get(position).m_is_routed;
			TextView txtName = (TextView) itemView.findViewById(R.id.clist_child_name);
			txtName.setText(m_name);
			Button btnShowInfo = (Button) itemView.findViewById(R.id.clist_btn_showinfo);
			Button btnDelete = (Button) itemView.findViewById(R.id.clist_btn_delete);
			btnShowInfo.setText("정보");
			btnDelete.setText("해제");
			btnShowInfo.setOnClickListener(new View.OnClickListener() {
            @Override
	            public void onClick(View view) {
	            	Intent i = new Intent(context, InfoDialog.class);
	            	i.putExtra("TITLE", "Message");
	            	i.putExtra("MSG", "이름 : "+m_name+
	            				"\r\n"+"최근사용시간 : "+last_acc_date+
	            				"\r\n"+"루팅상태 : "+isrouted);
	            	context.startActivity(i);
	            }
			});
			btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
	            public void onClick(View view) {
	            	Intent i = new Intent(context, DeleteDialog.class);
	            	i.putExtra("PID", MainActivity.cur_p_id);
	            	i.putExtra("CID", c_id);
	            	context.startActivity(i);
	            }
			});
			
			
		/*	
			String m_name = mchildApp.get(position).m_app_name;
			String m_utime = mchildApp.get(position).m_used_time;			
			byte[] real_img = mchildApp.get(position).m_img;
			byte[] decoded_img = Base64.decode(real_img, Base64.DEFAULT);
			
			Bitmap bmp = BitmapFactory.decodeByteArray(decoded_img, 0, decoded_img.length);
			
			
			TextView txtName = (TextView) itemView.findViewById(R.id.app_list_name);
			TextView txtOption = (TextView) itemView.findViewById(R.id.app_list_option);
			
			txtName.setText(m_name);
			txtOption.setText(m_utime);
			//imgIcon.setImageResource(mIcon[position]);
			try{
				if(decoded_img != null){
					ImageView imgIcon = (ImageView) itemView.findViewById(R.id.app_list_icon);
					imgIcon.setImageBitmap(bmp);
					Log.d("MMM", "AppArrayAdapter bmp OK : "+ decoded_img.length);
				}
				else 
					Log.d("MMM", "AppArrayAdapter bmp Null");
			}catch(Exception e){
				Log.d("MMM", "AppArrayAdapter err");
			}*/
		return itemView;//
	}

}
