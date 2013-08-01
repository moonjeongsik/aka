package tjssm.mamsee.manager.http;

import android.graphics.Bitmap;


public class ChildApp {
	
	   	public String m_app_name;
	   	public String m_used_time;
	    public byte[] m_img;
	 	
	   	ChildApp(String app_name, String used_time, byte[] img){
	   			this.m_app_name  = app_name;
	   			this.m_used_time = used_time;
	   			this.m_img = img;
	   	}
	

}