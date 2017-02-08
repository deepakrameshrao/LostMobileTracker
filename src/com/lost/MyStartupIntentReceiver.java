package com.lost;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;

public class MyStartupIntentReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent arg1) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(context, MobileTrackerService.class);
		context.startService(intent);
	}

}
