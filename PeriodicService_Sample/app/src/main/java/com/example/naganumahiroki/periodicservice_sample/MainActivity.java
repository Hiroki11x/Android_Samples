package com.example.naganumahiroki.periodicservice_sample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TimePicker tPicker;
    int notificationId;
    private PendingIntent alarmIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tPicker = (TimePicker)findViewById(R.id.timePicker);
        tPicker.setIs24HourView(true);
        Calendar setTimeCalendar = Calendar.getInstance();
        tPicker.setCurrentHour(setTimeCalendar.get(Calendar.HOUR_OF_DAY));

        Button startBtn = (Button)findViewById(R.id.start);
        Button stopBtn = (Button)findViewById(R.id.stop);

        stopBtn.setOnClickListener(myAlarmListener);
        startBtn.setOnClickListener(myAlarmListener);
    }

    View.OnClickListener myAlarmListener= new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.start:
                    setAlarmForSendInfo();
                    break;
                case R.id.stop:
                    AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    alarm.cancel(alarmIntent);
                    Toast.makeText(MainActivity.this, "通知をキャンセルしました!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void setAlarmForSendInfo(){
        Intent bootIntent = new Intent(MainActivity.this, AlarmBroadcastReceiver.class);
        bootIntent.putExtra("notificationId", System.currentTimeMillis());
        bootIntent.setType(""+ System.currentTimeMillis());
        alarmIntent = PendingIntent.getBroadcast(MainActivity.this, 0, bootIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarm = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        int hour = tPicker.getCurrentHour();
        int minute = tPicker.getCurrentMinute();

        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, hour);
        startTime.set(Calendar.MINUTE, minute);
        startTime.set(Calendar.SECOND, 0);
        long alarmStartTime = startTime.getTimeInMillis();

        alarm.set(
                AlarmManager.RTC_WAKEUP,
                alarmStartTime,
                alarmIntent
        );
        Toast.makeText(MainActivity.this, "通知セット完了!", Toast.LENGTH_SHORT).show();
    }

}
