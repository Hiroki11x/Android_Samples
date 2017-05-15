package com.example.naganumahiroki.getbatterystatus_sample;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by naganumahiroki on 2015/09/15.
 */
public class BatteryChangedBroadcastReceiver extends WakefulBroadcastReceiver {


    private int scale;
    private int level;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
            // 電池残量の最大値
            scale = intent.getIntExtra("scale", 0);
            // 電池残量
            level = intent.getIntExtra("level", 0);
        }

        //結果をログで出す
        Log.d("Battery Level",level +"/"+scale );
    }
}
