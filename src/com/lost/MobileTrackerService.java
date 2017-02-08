package com.lost;


import android.app.AlertDialog;
import android.app.Service;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MobileTrackerService extends Service {

	TelephonyManager telManager;
	static String phoneNumber1;
	static String phoneNumber2;
	static String IMSI;
	static String IMEI;
  	static SQLiteDatabase mobileTrackerDb;
  	static boolean flag = false;
	static Cursor cur;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("Service", "Deepak: Inside oncreate");
			
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		telManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		mobileTrackerDb = openOrCreateDatabase("tracker", 0, null);
		mobileTrackerDb.execSQL("CREATE TABLE IF NOT EXISTS track(id integer, IMSI text, IMEI text, phonenum1 text, phonenum2 text, PIN text);");
		cur = mobileTrackerDb.query("track", null, null, null, null, null, null);
		if(cur.getCount() <= 0){
			Intent in = new Intent("android.lost.intent");
			in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Log.d("Service", "Deepak: Inside if");
			startActivity(in);
		}
		else{
			cur.moveToFirst();
			Toast.makeText(this, cur.getString(cur.getColumnIndex("IMSI")), Toast.LENGTH_LONG).show();
			if(!(cur.getString(cur.getColumnIndex("IMSI")).equals(telManager.getSubscriberId()))){
				Toast.makeText(this, "SIM has been changed", Toast.LENGTH_LONG).show();
				Intent in = new Intent("android.lost.verify");
				in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Log.d("Service", "Deepak: Inside else");
				startActivity(in);
				
			}
		}
		
		stopSelf();
		
	}
	
}
