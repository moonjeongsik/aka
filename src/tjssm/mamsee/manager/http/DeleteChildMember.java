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
public class DeleteChildMember extends Activity {

	public static final String SERVER_IP = "210.118.64.173"; 
	private String TAG 		= "DeleteChildMember"; 
	private String m_c_id;
	private String m_p_id;
	
	TH_GetAppList th_getAppList;
	
	public DeleteChildMember() {
		
	}
	
	public void DelMember(String p_id, String c_id) {
		this.m_c_id 	 	= c_id;
		this.m_p_id 		= p_id;
		
		th_getAppList = new TH_GetAppList();
		th_getAppList.start();
		try{
			th_getAppList.join();
		}catch(Exception e){
			Log.d(TAG, "err");
		}
	}
	
    public void SubDelMember() {
		
		String url = "http://210.118.64.173/mam_delete_childmember.php"
				+"?c_id=" + m_c_id + "&p_id=" + m_p_id;
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
		
		} catch(Exception e) {}
		
		String json_from_server = html.toString();
		String result = null;
		try {
				JSONArray ja = new JSONArray(json_from_server);
				JSONObject json_msg = ja.getJSONObject(0);
				result = json_msg.getString("RESULT");
				Log.d(TAG, "result:"+result);
		}catch(JSONException e) {
			Log.d(TAG,"Exception e");
		}
	}
    
    class TH_GetAppList extends Thread {
		
		public void run() {
			
			try{
				SubDelMember();
			}
			catch(Exception e){
				Log.d(TAG, "err");
			}
		}

	}
    
}
