package tjssm.mamsee.manager.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import tjssm.mamsee.manager.http.SetAppOption.TH_GetAppList;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.NetworkOnMainThreadException;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("NewApi")
public class GetChildList extends Activity {

	String memail;
	TH_GetAppList th_get;
	public GetChildList() {
	}
	
	public ArrayList<ChildInfo> Get_Child_List(String email) {
		this.memail 	 		= email;
		th_get = new TH_GetAppList();
		th_get.start();
		try{
			th_get.join();
		}catch(Exception e){
			Log.d("SetAppOption", "err");
		}
		return th_get.GetList();
	}
	
    public ArrayList<ChildInfo> SubSetList() {
		
    
    	String url = "http://210.118.64.173/mam_pget_child_list.php?p_id="+memail;
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		HttpResponse response = null;
		
		ArrayList<ChildInfo> mChildInfoList;
		mChildInfoList = new ArrayList<ChildInfo>();
		
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringBuilder html = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			for(;;) {
				String line = br.readLine();
				if( line == null ) break;
				html.append(line + '\n');
			}
			br.close();	
		
		}catch(Exception e) {}
    	
		String json_from_server = html.toString();
		try {
			JSONArray ja = new JSONArray(json_from_server);
			for(int i=0; i<(ja.length() - 1); i++) {
				JSONObject query_result = ja.getJSONObject(i);
				String c_id = query_result.getString("c_id");
				String child_name = query_result.getString("child_name");
				String last_acc_date = query_result.getString("last_acc_date");
				String is_routed = query_result.getString("is_routed");
				mChildInfoList.add(new ChildInfo(c_id, child_name, last_acc_date, is_routed));
			}
		}catch(Exception e) {
			Log.d("TJSSM","Exception e");
		}
		
		return mChildInfoList;
	}
    
    class TH_GetAppList extends Thread {
    	ArrayList<ChildInfo> rt;
		public void run() {
			
			try{
				rt = SubSetList();
			}
			catch(Exception e){
				Log.d("GetAppList", "err");
			}
		}
		public ArrayList<ChildInfo> GetList() {
			return rt;
		}

	}
    
}
