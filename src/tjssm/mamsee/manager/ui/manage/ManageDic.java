package tjssm.mamsee.manager.ui.manage;

import java.util.ArrayList;
import java.util.List;

import tjssm.mamsee.manager.R;
import tjssm.mamsee.manager.R.layout;
import tjssm.mamsee.manager.ui.MainActivity;

import com.actionbarsherlock.app.SherlockFragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ManageDic extends SherlockFragment implements OnScrollListener {
	
	
	public static final String[] CATEGORY_TEXT = {"욕설", "폭력", "음란", "기타"};
	
	private ArrayList<DicWord> m_dic_word;
	private ArrayList<DicWord> fisrt_search_list;
	private ArrayList<DicWord> temp_list_view;
	private DicListAdapter dic_list_adapter;
	private ListView listView;
	private EditText eb_search;
	private boolean mLockListView;
	private int list_view_num;
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	  super.onActivityCreated(savedInstanceState);
	  	InitFunc();
	}
	private void InitFunc(){
		
		eb_search = (EditText)getView().findViewById(R.id.dic_add_edit);
		eb_search.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				Log.d("ManageDic", "이벤트1");
			}
			@Override
			public void afterTextChanged(Editable s) {
				Log.d("ManageDic", "이벤트2");
				FindWordList(0);
			}
		});
		list_view_num = 10;
		mLockListView = false;
		fisrt_search_list = new ArrayList<DicWord>();
		temp_list_view = new ArrayList<DicWord>();
		m_dic_word = new ArrayList<DicWord>();
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"개새끼"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"썅년"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"시팔넘"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"니기미"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[3],"마약"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[3],"자살"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"씨발"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"존나"));		
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"개새끼"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"썅년"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"시팔넘"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"니기미"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[3],"마약"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[3],"자살"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"씨발"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"존나"));	
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"개새끼"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"썅년"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"시팔넘"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"니기미"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[3],"마약"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[3],"자살"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"씨발"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"존나"));	
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"개새끼"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"개새끼"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"개새끼"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"개새끼"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"개새끼"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"개새끼"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"개새끼"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"개새끼"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"개새끼"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"개새끼"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"개새끼"));
		m_dic_word.add(new DicWord(CATEGORY_TEXT[0],"개새끼"));
		
		
		
		
		if(list_view_num > m_dic_word.size())
			list_view_num = m_dic_word.size();
		for(int i=0; i<list_view_num; i++){
			temp_list_view.add(m_dic_word.get(i));
		}
		dic_list_adapter = new DicListAdapter(getSherlockActivity(), temp_list_view);
		listView = (ListView) getView().findViewById(R.id.dic_list_view);
		View footer = getSherlockActivity().getLayoutInflater().inflate(R.layout.footer, null, false);
		footer.findViewById(R.id.btn_footer).setOnClickListener(mClickListener);
		
		
		listView.addFooterView(footer);
		listView.setOnScrollListener(this);
		listView.setAdapter(dic_list_adapter);
		listView.setTextFilterEnabled(true);
		listView.setOnItemClickListener(new OnItemClickListener() {
			   public void onItemClick(AdapterView<?> parent, View view,
				  int position, long id) {
				   //  AppListDialog aldialog = new AppListDialog(getSherlockActivity(), app_name, package_name, c_id);
				   //  aldialog.show(); 
			   }
		  });
	}
	private View.OnClickListener mClickListener = new View.OnClickListener() {
        
        @Override
        public void onClick(View v) {
        	
        	FindWordList(10);
        	 Log.i("ManageDic", "footer clicked");
        }
    };
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.manage_dic, container, false);
		return rootView;
	}

	private void addWord(ArrayList<DicWord> wordList, DicWord mDicWord, String searchKeyword) {//searchkeyword-editbox

		boolean isAdd = false;
	
		if ((searchKeyword != null) && ("".equals(searchKeyword.trim()) == false)) {
	
			String iniName = HangulUtils.getHangulInitialSound(mDicWord.word,
					searchKeyword);
	
			if (iniName.indexOf(searchKeyword) >= 0) {
				isAdd = true;
			}
		} else {
			isAdd = true;
		}
	
		if (isAdd) {
			wordList.add(new DicWord(mDicWord.category, mDicWord.word));
		}
	}
	
	private void AddWordList(){
		
		String searchKeyword = eb_search.getText().toString();
		
		if ((searchKeyword != null) && ("".equals(searchKeyword.trim()) == false)){
	//		for(int i=temp_list_view.size(); i<list_view_num; list_view_num++)
				//temp_list_view.add(object)
		}
		else {
			
		}
	}
	private void FindWordList(int add_list_size) {
		
		int prelist_view_num = 0;
		if(add_list_size == 0){
			list_view_num = 10; //0이면 다시 처음 사이즈로 초기화;
			temp_list_view = new ArrayList<DicWord>();
		}
		else 
		{
			prelist_view_num = list_view_num;
			list_view_num += add_list_size;
		}
		
		String searchKeyword = eb_search.getText().toString();
		
		if ((searchKeyword != null) && ("".equals(searchKeyword.trim()) == false)){
			fisrt_search_list = new ArrayList<DicWord>();
			
			Log.d("ManageDic", "searchKeyword:"+searchKeyword);
			for (int i=0; i<m_dic_word.size(); i++) {
				addWord(fisrt_search_list, m_dic_word.get(i), searchKeyword);
			}
			
			//temp_list_view = new ArrayList<DicWord>();
			if(list_view_num > fisrt_search_list.size())
				list_view_num = fisrt_search_list.size();
			
			Log.d("ManageDic", "size:"+list_view_num+", :"+fisrt_search_list.size());
			
			for(int i=prelist_view_num; i<list_view_num; i++){
				temp_list_view.add(fisrt_search_list.get(i));
			}
			
			if(add_list_size == 0){
				dic_list_adapter = new DicListAdapter(getSherlockActivity(), temp_list_view);	
				//dic_list_adapter.notifyDataSetChanged();
				listView.setAdapter(dic_list_adapter);
			}
			else {
				dic_list_adapter.notifyDataSetChanged();
			}
			
		}
		else {
			//temp_list_view = new ArrayList<DicWord>();
			prelist_view_num = list_view_num;
			list_view_num += add_list_size;
			if(list_view_num > m_dic_word.size())
				list_view_num = m_dic_word.size();
			for(int i=prelist_view_num; i<list_view_num; i++){
				temp_list_view.add(m_dic_word.get(i));
				Log.d("ManageDic", "content:"+m_dic_word.get(i).word);
			}
			//dic_list_adapter = new DicListAdapter(getSherlockActivity(), temp_list_view);
			
			dic_list_adapter.notifyDataSetChanged();
			//listView.setAdapter(dic_list_adapter);
		}
		
	}
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

		 // 현재 가장 처음에 보이는 셀번호와 보여지는 셀번호를 더한값이
	    // 전체의 숫자와 동일해지면 가장 아래로 스크롤 되었다고 가정합니다.
	    int count = totalItemCount - visibleItemCount;

	    if(firstVisibleItem >= count && totalItemCount != 0
	      && mLockListView == false)
	    {
	      Log.i("ManageDic", "Loading next items");
	    }  
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
