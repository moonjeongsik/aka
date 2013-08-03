package tjssm.mamsee.manager.http;

import android.graphics.Bitmap;


public class ChildApp {
	
	   	public String m_app_name;
	   	public String m_package_name;
	   	public String m_used_time;
	   	public long m_long_time;
	   	public byte[] m_img;

	    
	 	
	   	ChildApp(String app_name, String package_name, String used_time, long time, byte[] img){
	   			this.m_app_name  = app_name;
	   			this.m_package_name = package_name;
	   			this.m_used_time = used_time;
	   			this.m_img = img;
	   			this.m_long_time = time;
	   	}
	

}