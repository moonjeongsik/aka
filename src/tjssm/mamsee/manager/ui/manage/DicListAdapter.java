package tjssm.mamsee.manager.ui.manage;


import java.util.ArrayList;

import tjssm.mamsee.manager.R;
import tjssm.mamsee.manager.R.id;
import tjssm.mamsee.manager.R.layout;
import tjssm.mamsee.manager.http.ChildApp;
import android.R.color;
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


public class DicListAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	String[] mFisrtText;
	String[] mTimeText;
	int[] mIcon;
	ArrayList<DicWord> dicword;
	LayoutInflater inflater;
    
	public DicListAdapter(Context context, ArrayList<DicWord> m_dicWord) {
		this.context = context;
		this.dicword = m_dicWord;
	}

	@Override
	public int getCount() {
		return dicword.size();
	}
	
	@Override
	public Object getItem(int position) {
		return dicword.get(position).word;
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.manage_dic_list, parent,
				false);
		String m_word = dicword.get(position).word;
		String m_cate = dicword.get(position).category;			
		TextView tv_cate = (TextView) itemView.findViewById(R.id.dic_word_categori);
		TextView tv_word = (TextView) itemView.findViewById(R.id.dic_word_word);
		tv_cate.setText(m_cate);
		tv_word.setText(m_word);
			
		return itemView;
	}

}
