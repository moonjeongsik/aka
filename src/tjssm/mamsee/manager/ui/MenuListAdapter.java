package tjssm.mamsee.manager.ui;


import tjssm.mamsee.manager.R;
import tjssm.mamsee.manager.R.id;
import tjssm.mamsee.manager.R.layout;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
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


public class MenuListAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	String[] mTitle;
	String[] mSubTitle;
	int[] mIcon;
	LayoutInflater inflater;
	
	
    public static boolean b_btn_square1=false;
    public static boolean b_btn_square2=false;
    public static boolean b_btn_square3=false;
    public static boolean b_btn_square4=false;
    
    
	public MenuListAdapter(Context context, String[] title, String[] subtitle,
			int[] icon) {
		this.context = context;
		this.mTitle = title;
		this.mSubTitle = subtitle;
		this.mIcon = icon;
	}

	@Override
	public int getCount() {
		return mTitle.length;
	}
	
	@Override
	public Object getItem(int position) {
		return mTitle[position];
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	

	public View getView(int position, View convertView, ViewGroup parent) {
		TextView txtTitle;
		TextView txtSubTitle;
		ImageView imgIcon;
		View itemView = null;
		// Declare Variables
		if(position == 0) {

			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			itemView = inflater.inflate(R.layout.drawer_sub_title, parent,
					false);
			txtTitle = (TextView) itemView.findViewById(R.id.content_tv1);
			txtTitle.setText(mTitle[position]);
		}
		else if(position < 4) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			itemView = inflater.inflate(R.layout.drawer_list_item, parent,
					false);
			txtTitle = (TextView) itemView.findViewById(R.id.title);
			imgIcon = (ImageView) itemView.findViewById(R.id.icon);
			txtTitle.setText(mTitle[position]);
			imgIcon.setImageResource(mIcon[position]);
		}
		else if(position == 4) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			itemView = inflater.inflate(R.layout.drawer_sub_title, parent,
					false);
			txtTitle = (TextView) itemView.findViewById(R.id.content_tv1);
			txtTitle.setText(mTitle[position]);

		}
		else if(position == 5) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			itemView = inflater.inflate(R.layout.drawer_list_item_square, parent,
					false);
			
			Button sq_btn1 = (Button) itemView.findViewById(R.id.slide_b1);
			Button sq_btn2 = (Button) itemView.findViewById(R.id.slide_b2);
			Button sq_btn3 = (Button) itemView.findViewById(R.id.slide_b3);
			Button sq_btn4 = (Button) itemView.findViewById(R.id.slide_b4);
			
			sq_btn1.setText("App");
			sq_btn2.setText("Web");
			sq_btn3.setText("Chat");
			sq_btn4.setText("dic");
			sq_btn1.setOnClickListener(btnOnClickListener);
			sq_btn2.setOnClickListener(btnOnClickListener);
			sq_btn3.setOnClickListener(btnOnClickListener);
			sq_btn4.setOnClickListener(btnOnClickListener);
		}
		else if(position == 6) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			itemView = inflater.inflate(R.layout.drawer_sub_title, parent,
					false);
			txtTitle = (TextView) itemView.findViewById(R.id.content_tv1);
			txtTitle.setText(mTitle[position]);
		}
		else if(position > 6){

			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			itemView = inflater.inflate(R.layout.drawer_list_item, parent,
					false);
			txtTitle = (TextView) itemView.findViewById(R.id.title);
			imgIcon = (ImageView) itemView.findViewById(R.id.icon);
			txtTitle.setText(mTitle[position]);
			imgIcon.setImageResource(mIcon[position]);
		}
			return itemView;
	}
	
	private Button.OnClickListener btnOnClickListener = new Button.OnClickListener(){		

		@Override

		public void onClick(View v) {
			
			switch(v.getId()) {
			case R.id.slide_b1:
				b_btn_square1 = true;
				break;
			case R.id.slide_b2:
				b_btn_square2 = true;
				break;
			case R.id.slide_b3:
				b_btn_square3 = true;
				break;			
			case R.id.slide_b4:
				b_btn_square4 = true;
				break;
			}
		}

	};

}
