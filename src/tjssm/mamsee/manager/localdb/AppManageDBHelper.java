	 
package tjssm.mamsee.manager.localdb;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class AppManageDBHelper extends SQLiteOpenHelper {

	public static final String TAG = "PDB"; 
	public static final String DATABASE_NAME = "mamsee.db";
	public static final String TABLE_NAME = "appmanage";
	public static final String TABLE_NAME2 = "time_table";
	public AppManageDBHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, package_name TEXT,"
				+ "manage_op INTEGER, total_time INTEGER, start_time INTEGER, finish_time INTEGER);");
		
		Log.d("AppListDialog", "UUUU");
		db.execSQL("CREATE TABLE " + TABLE_NAME2 + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, package_name TEXT,"
				+ "start_time INTEGER, finish_time INTEGER);");
		Log.d("AppListDialog", "UUTT");		
		
		
/*
 		ContentValues cv = new ContentValues();
		cv.put("app_name", "opera");
	    cv.put("package_name", "com.opera.browser");
		cv.put("option", 0);
		cv.put("start_time", 0);
		cv.put("finish_time", 0);
		cv.put("run_time", 0);
		db.insert("blackapps", null, cv);
		Log.d(TAG, "Insert opera");
		
		//firefox �߰�
		cv.put("app_name", "firefox");
		cv.put("package_name", "org.mozilla.firefox");
		cv.put("option", 0);
		cv.put("start_time", 0);
		cv.put("finish_time", 0);
		cv.put("run_time", 0);
		db.insert("blackapps", null, cv);
		Log.d(TAG, "Insert firefox");
*/		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("PDB DROP TABLE IF EXISTS blackapps");
		onCreate(db);
	}





}

