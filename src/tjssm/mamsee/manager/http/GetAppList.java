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
public class GetAppList extends Activity {

	public static final String SERVER_IP = "210.118.64.173"; 
	
	private String c_id;
	private String c_page;
	private String c_date;
	TH_GetAppList th_getAppList;
	ArrayList<ChildApp> mChildAppList;
	
	public GetAppList() {
	}
	
	public ArrayList<ChildApp> GetChildAppList(String m_c_id) {
		this.c_id 	 = m_c_id;
		this.c_page  = null;
		this.c_date  = null;
		mChildAppList = new ArrayList<ChildApp>();
		th_getAppList = new TH_GetAppList();
		th_getAppList.start();
		try{
			
			th_getAppList.join();
		}catch(Exception e){
			Log.d("GetAppList", "err");
		}
		return mChildAppList;
	}
    public void SubGetList(String m_c_id) {
    	

		
		String url = "http://210.118.64.173/mam_get_applist.php"
				+"?c_id=" + c_id + "&page=" + c_page +"&date="+c_date;
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		HttpResponse response = null;
		
		
		
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
			//Log.d("TJSSM", ja.length()+"=->"+json_from_server);//w_option	
			for(int i=0; i<(ja.length() - 1); i++) {
				JSONObject query_result = ja.getJSONObject(i);
				String app_name = query_result.getString("app_name");
				String used_time = query_result.getString("used_time");
				String icon_image = query_result.getString("icon_image");
				byte[] byteArray = icon_image.getBytes();
				
				
				
				//Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
				mChildAppList.add(new ChildApp(app_name, SecToStr( Integer.parseInt(used_time) ), byteArray));
				Log.d("TJSSM", "child_name:"+app_name+",  used_time:"+used_time);
				
			//	Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
			}
		}catch(Exception e) {
			Log.d("TJSSM","Exception e");
		}
	}
    
    class TH_GetAppList extends Thread {
		
		public void run() {
			
			try{
				SubGetList(c_id);
			}
			catch(Exception e){
				Log.d("GetAppList", "err");
			}
		}

	}
    public String SecToStr(int second)
	{
		String str="";
		int hour;
		int min;
		int sec;
		
		hour = second / 3600;
		
		min = second;
		min = min / 60;
		min = min % 60;
		
		sec = second % 60;
		
		String h = String.format("%02d", hour);
		String m = String.format("%02d", min);
		String s = String.format("%02d", sec);
		str = h + ":" + m + ":" + s;
		
		return str; 
	}
    
}
