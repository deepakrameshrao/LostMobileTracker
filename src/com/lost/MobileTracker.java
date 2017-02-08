package com.lost;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ResourceCursorAdapter;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MobileTracker extends Activity {
	
	EditText phoneNumberView1;
	EditText phoneNumberView2;
	EditText PINView;
	Button doneButton;
	String phoneNumber1;
	String phoneNumber2;
	String PIN;

	static SQLiteDatabase mobileTrackerDb;
	TelephonyManager tm;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        Log.d("Deepak", "IMSI = "+tm.getSubscriberId());
        Toast.makeText(this, "IMSI = "+tm.getSubscriberId(), Toast.LENGTH_LONG).show();
        Toast.makeText(this, "MSISDN = "+tm.getLine1Number(), Toast.LENGTH_LONG).show();
        Toast.makeText(this, "IMEI = "+tm.getDeviceId(), Toast.LENGTH_LONG).show();
        Toast.makeText(this, "SW ID = "+tm.getDeviceSoftwareVersion(), Toast.LENGTH_LONG).show();
        Toast.makeText(this, "NW op = "+tm.getNetworkOperatorName(), Toast.LENGTH_LONG).show();
        
        
        
        
        mobileTrackerDb = openOrCreateDatabase("tracker", 0, null);
        mobileTrackerDb.execSQL("CREATE TABLE IF NOT EXISTS track(id integer, IMSI text, IMEI text, phonenum1 text, phonenum2 text, PIN text);");
        
        phoneNumberView1 = (EditText) findViewById(R.id.PhoneNumber1);
        phoneNumberView2 = (EditText) findViewById(R.id.PhoneNumber2);
        PINView = (EditText) findViewById(R.id.PIN);
        
        doneButton = (Button) findViewById(R.id.donebutton);
        
        doneButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				phoneNumber1 = phoneNumberView1.getText().toString();
				phoneNumber2 = phoneNumberView2.getText().toString();
				PIN = PINView.getText().toString();
				
				ContentValues values = new ContentValues();
				values.put("id", 0);
				values.put("IMSI", tm.getSubscriberId());
				values.put("IMEI", tm.getDeviceId());
				values.put("phonenum1", phoneNumber1);
				values.put("phonenum2", phoneNumber2);
				values.put("PIN", PIN);
				mobileTrackerDb.insert("track", null, values);
				finish();
			}
		});
        
    }
    
    
}