package tjssm.mamsee.manager.localdb;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AppManageDBAdapter {

	private static final String LOG = "AMDB"; 
	private static final String TABLE_NAME = "appmanage"; 
	public static final String TABLE_NAME2 = "time_table";
	public AppManageDBHelper mPdbHelper = null;
	public SQLiteDatabase mDb = null;
	
	public Context context = null;
	
	public AppManageDBAdapter(Context _context) {
		this.context = _context;
	}

	public AppManageDBAdapter open() {
		
		if(mPdbHelper == null) {
			mPdbHelper = new AppManageDBHelper(context);
			//Log.d("PDB", "PDB OPEN SQL");
			//getReadableDatabase();		
		}
		return this;
	}
	public void close() {
		if(mPdbHelper != null) {
			//Log.d(LOG, "PDB My DB Closed!");
			mPdbHelper.close();
			mPdbHelper = null;
		}
	}
	//db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, package_name TEXT,"
	//		+ "manage_op INTEGER, total_time INTEGER, start_time INTEGER, finish_time INTEGER);");

	public boolean insertAppOption(String package_name, int manage_op, int total_time, int start_time, int finish_time) {
		
		if(mPdbHelper == null)
			return false;
		mDb = mPdbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("package_name", package_name);
		cv.put("manage_op", manage_op);
		cv.put("total_time", total_time);
		cv.put("start_time", start_time);
		cv.put("finish_time", finish_time);
		int rows = (int)mDb.insert(TABLE_NAME, null, cv);	
		
		if( rows > 0) {
			Log.d(LOG, "PDB Insert Success [ " + package_name + " ]");
			return true;
		}
		else {
			Log.d(LOG, "PDB Insert False");
			return false;
		}
	}
	public boolean updateAppOption(String package_name, int manage_op, int total_time, int start_time, int finish_time){
		
		if(mPdbHelper == null){
			return false;
		}
		mDb = mPdbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("manage_op", manage_op);
		cv.put("total_time", total_time);
		cv.put("start_time", start_time);
		cv.put("finish_time", finish_time);
		int rows = (int)mDb.update(TABLE_NAME, cv, "package_name=?", new String[] {package_name});		
		
		if( rows > 0) {
			Log.d(LOG, "PDB Update Success [ " + package_name + " ]");
			return true;
		}
		else {
			Log.d(LOG, "PDB Update False");
			return false;
		}
	}
	public boolean deleteAppOption(String package_name) {
	
		int rows = (int)mDb.delete(TABLE_NAME, "package_name=?", new String[] {package_name});
		
		if( rows > 0) {
			Log.d(LOG, "PDB Delete Success [ " + package_name + " ]");
			return true;
		}
		else {
			Log.d(LOG, "PDB Delete False");
			return false;
		}
	}
	
	public boolean searchIsInserted(String package_name) {
		String[] columns= {"_id", "package_name", "manage_op", "total_time", "start_time", "finish_time"};
		String[] parms={package_name};//
		Cursor cursor = null;
		mDb = mPdbHelper.getReadableDatabase();
		cursor = mDb.query(TABLE_NAME, columns, "package_name=?", parms, null, null, null);
		
		if(cursor == null) {
			Log.d(LOG, "PDB Search False");
			return false;
		}
		else {
			int manage_col = 	cursor.getColumnIndex("manage_op");
			int total_col = 	cursor.getColumnIndex("total_time");
			int start_col = 	cursor.getColumnIndex("start_time");
			int finish_col = 	cursor.getColumnIndex("finish_time");
				
			int mttime, mtstart, mtfinish;
			
			if(cursor.moveToNext()) {
				mttime = cursor.getInt(total_col);
				mtstart = cursor.getInt(start_col);
				mtfinish = cursor.getInt(finish_col);
				
				Log.d(LOG, "PDB Content=> package_name:"+package_name+
						", mttime:"+mttime+", mtstart:"+mtstart+", mtfinish:"+mtfinish);
				return true;
			}
			
			return false;
		}
	}
	public int[] searchAppOption(String package_name) {
		
		String[] columns= {"_id", "package_name", "manage_op", "total_time", "start_time", "finish_time"};
		String[] parms={package_name};//
		int[] arrOption = new int[4];
		Cursor cursor = null;
		mDb = mPdbHelper.getReadableDatabase();
		cursor = mDb.query(TABLE_NAME, columns, "package_name=?", parms, null, null, null);
		
		
		
		if(cursor == null) {
			Log.d(LOG, "PDB Search False");
			return null;
		}
		else {
			int manage_col = 	cursor.getColumnIndex("manage_op");
			int total_col = 	cursor.getColumnIndex("total_time");
			int start_col = 	cursor.getColumnIndex("start_time");
			int finish_col = 	cursor.getColumnIndex("finish_time");
				
			int moption, mttime, mtstart, mtfinish;
			
			if(cursor.moveToNext()) {
				moption = cursor.getInt(manage_col);
				mttime = cursor.getInt(total_col);
				mtstart = cursor.getInt(start_col);
				mtfinish = cursor.getInt(finish_col);
				
				Log.d(LOG, "PDB Content=> package_name:"+package_name+
						", mttime:"+mttime+", mtstart:"+mtstart+", mtfinish:"+mtfinish);
				arrOption[0] = moption;
				arrOption[1] = mttime;
				arrOption[2] = mtstart;
				arrOption[3] = mtfinish;
				
				return arrOption;
			}
			
			return null;
		}
	}
	public boolean insertTimeTable(String package_name, int start_time, int finish_time) {
		
		if(mPdbHelper == null)
			return false;
		mDb = mPdbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("package_name", package_name);
		cv.put("start_time", start_time);
		cv.put("finish_time", finish_time);
		int rows = (int)mDb.insert(TABLE_NAME2, null, cv);	
		
		if( rows > 0) {
			Log.d(LOG, "PDB Insert Success [ " + package_name + " ]");
			return true;
		}
		else {
			Log.d(LOG, "PDB Insert False");
			return false;
		}
	}
	public ArrayList<Integer> searchTimeTable(String package_name) {
		
		String[] columns= {"_id", "package_name", "start_time", "finish_time"};
		String[] parms={package_name};//
		ArrayList<Integer> arrOption = new ArrayList<Integer>();
		
		Cursor cursor = null;
		mDb = mPdbHelper.getReadableDatabase();
		cursor = mDb.query(TABLE_NAME2, columns, "package_name=?", parms, null, null, null);
		
		if(cursor == null) {
			Log.d(LOG, "PDB Search False");
			return null;
		}
		else {
			int start_col = 	cursor.getColumnIndex("start_time");
			int finish_col = 	cursor.getColumnIndex("finish_time");
			int mtstart, mtfinish;
				
			while(cursor.moveToNext()) {
				mtstart = cursor.getInt(start_col);
				mtfinish = cursor.getInt(finish_col);
				Log.d("AppListDialog", "DB :" + mtstart + ", "+mtfinish);
				arrOption.add(mtstart);
				arrOption.add(mtfinish);
			}
			Log.d("AppListDialog", "DBsize  :" + arrOption.size());
			return arrOption;
		}
	}
	public boolean searchTimeTableIsExist(String package_name) {
		
		String[] columns= {"_id", "package_name", "start_time", "finish_time"};
		String[] parms={package_name};//
		int[] arrOption = new int[2];
		Cursor cursor = null;
		mDb = mPdbHelper.getReadableDatabase();
		cursor = mDb.query(TABLE_NAME2, columns, "package_name=?", parms, null, null, null);
		
		if(cursor == null) {
			Log.d(LOG, "PDB Search False");
			return false;
		}
		else {
			int start_col = 	cursor.getColumnIndex("start_time");
			int finish_col = 	cursor.getColumnIndex("finish_time");
			int moption, mttime, mtstart, mtfinish;
				
			if(cursor.moveToNext()) {
				return true;
			}
			
			return false;
		}
	}
	public boolean searchTime(String package_name, int start_time, int finish_time) {
		
		String[] columns= {"_id", "package_name", "start_time", "finish_time"};
		String[] parms={package_name, ""+start_time, ""+finish_time};//
		int[] arrOption = new int[2];
		Cursor cursor = null;
		mDb = mPdbHelper.getReadableDatabase();
		cursor = mDb.rawQuery("SELECT package_name FROM "+TABLE_NAME2+" WHERE package_name='"+package_name
				+"' AND start_time='"+start_time+"' AND finish_time='"+finish_time+"'", null);
		//cursor = mDb.query(TABLE_NAME2, columns, "package_name=?, start_time=?, finish_time=?"
		//		, parms, null, null, null);
		
		if(cursor == null) {
			Log.d(LOG, "PDB Search False");
			return false;
		}
		else {
				
			if(cursor.moveToNext()) {
				return true;
			}
			else 
				Log.d("AppListDialog", "search time Err");
			
			return false;
		}
	}
	public boolean updateTimetable(String package_name,int start_time, int finish_time){
		
		if(mPdbHelper == null){
			return false;
		}
		mDb = mPdbHelper.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("start_time", start_time);
		cv.put("finish_time", finish_time);
		int rows = (int)mDb.update(TABLE_NAME2, cv, "package_name=?", new String[] {package_name});		
		
		if( rows > 0) {
			Log.d(LOG, "PDB Update Success [ " + package_name + " ]");
			return true;
		}
		else {
			Log.d(LOG, "PDB Update False");
			return false;
		}
	}
	public void deleteTimetable(String package_name, int start_time, int finish_time) {
		
		String sStarttime = start_time+"";
		String sFinishtime = finish_time+"";
		mDb = mPdbHelper.getWritableDatabase();
		//mDb.rawQuery("DELETE FROM "+TABLE_NAME2+" WHERE package_name='"+package_name
		//		+"' AND start_time='"+sStarttime+"' AND finish_time='"+sFinishtime+"'", null);
		String where = "package_name = ?"
				+ " AND start_time = ?"
				+ " AND finish_time = ?";
				String[] whereArgs = {package_name,sStarttime,sFinishtime};
		mDb.delete(TABLE_NAME2, where, whereArgs);
	}
	
}
