package com.example.iet_events.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.util.Log;
import android.widget.Toast;

import com.example.iet_events.MainActivity;

public class AlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent notificationIntent = getIntent();
        Log.e("Info", notificationIntent.getStringExtra("Hour"));
        Intent alarmIntent = new Intent(AlarmClock.ACTION_SET_ALARM);
        alarmIntent.putExtra(AlarmClock.EXTRA_HOUR, Integer.parseInt(notificationIntent.getStringExtra("Hour")));
        alarmIntent.putExtra(AlarmClock.EXTRA_MINUTES, Integer.parseInt(notificationIntent.getStringExtra("Minutes")));
        alarmIntent.putExtra(AlarmClock.EXTRA_MESSAGE, "IET Meeting");
        if(alarmIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(alarmIntent);
        }else{
            Toast.makeText(AlarmActivity.this, "There is no app that supports alarms", Toast.LENGTH_LONG).show();
            startActivity(new Intent(AlarmActivity.this, MainActivity.class));
        }
        finish();
    }
}