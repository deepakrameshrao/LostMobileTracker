package com.lost;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class VerifyPIN extends Activity {
	
	EditText verify_PIN;
	Button verify_done;
	String verify_pin;
	static SQLiteDatabase mobileTrackerDb;
	Cursor cursor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.verify_pin);
		
		mobileTrackerDb = openOrCreateDatabase("tracker", 0, null);
		cursor = mobileTrackerDb.query("track", null, null, null, null, null, null);
		verify_PIN = (EditText) findViewById(R.id.VerifyPIN);
        
		verify_done = (Button) findViewById(R.id.verifydone);
		verify_done.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				verify_pin = verify_PIN.getText().toString();
				String pin = cursor.getString(cursor.getColumnIndex("PIN"));
				Log.d("VerifyPIN","Deepak: PIN="+pin+" VPIN="+verify_pin);
				if(pin != null && verify_pin.equals(pin)){
					Log.d("VerifyPIN","Deepak: PIN correct");
				}
				else{
					Log.d("VerifyPIN","Deepak: PIN incorrect");
					String phoneNumber1 = cursor.getString(cursor.getColumnIndex("phonenum1"));
					String phoneNumber2 = cursor.getString(cursor.getColumnIndex("phonenum2"));
					android.telephony.SmsManager smsManager = android.telephony.SmsManager.getDefault();
					smsManager.sendTextMessage(phoneNumber1, null, "Hi..", null, null);
				}
			}
		});
	}
}
