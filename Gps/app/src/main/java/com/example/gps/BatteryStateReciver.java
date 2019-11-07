package com.example.gps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;



public class BatteryStateReciver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(intent.ACTION_BATTERY_CHANGED)) {
			int level = intent.getIntExtra("level", -1);
			Constants.currLevel = level;
			Log.i("当前手机电量为:", Constants.currLevel + "%");
		}

	}

}
