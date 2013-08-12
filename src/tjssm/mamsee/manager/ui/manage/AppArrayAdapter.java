package tjssm.mamsee.manager.ui.manage;


import java.util.ArrayList;

import tjssm.mamsee.manager.R;
import tjssm.mamsee.manager.R.id;
import tjssm.mamsee.manager.R.layout;
import tjssm.mamsee.manager.http.ChildApp;
import android.content.Context;
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


public class AppArrayAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	String[] mFisrtText;
	String[] mTimeText;
	int[] mIcon;
	ArrayList<ChildApp> mchildApp;
	LayoutInflater inflater;
    
/*	public AppArrayAdapter(Context context, String[] fTxt, String[] tTxt,
			int[] icon) {
		this.context = context;
		this.mFisrtText = fTxt;
		this.mTimeText = tTxt;
		this.mIcon = icon;
	}*/
	public AppArrayAdapter(Context context, ArrayList<ChildApp> childApp) {
		this.context = context;
		this.mchildApp = childApp;
		//	this.mFisrtText = fTxt;
	//	this.mTimeText = tTxt;
	//	this.mIcon = icon;
	}

	@Override
	public int getCount() {
		return mchildApp.size();
	}
	
	@Override
	public Object getItem(int position) {
		return mchildApp.get(position).m_app_name;
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View itemView = inflater.inflate(R.layout.child_app_list_item, parent,
					false);
			
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
			}
		return itemView;//
	}

}
