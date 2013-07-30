package tjssm.mamsee.manager.http;

public class ChildInfo {
	
	   	public String m_c_id;
	   	public String m_child_name;
	   	public String m_last_acc_date;
	   	public String m_is_routed;	
	 	
	   	ChildInfo(String c_id, String child_name, String last_acc_date, String is_routed){
	   			m_c_id = c_id;
	   			m_child_name = child_name;
	   			m_last_acc_date = last_acc_date;
	   			m_is_routed = is_routed;
		}
	

}