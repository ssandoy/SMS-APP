package com.example.ssandoy.s236305_mappe2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Calendar;

public class SendMessagePeriodicService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Calendar cal = Calendar.getInstance();

        Intent i =	new	Intent(this, SendMessageService.class);
        PendingIntent pintent = PendingIntent.getService(this,	0,	i,	0);

        //TODO: SETTE HVER GANG CONFIRMMESSAGE TRYKKES PÅ
        cal.set(Calendar.MINUTE, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("minute", -1));
        cal.set(Calendar.HOUR_OF_DAY, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("hour", -1));
        Log.d("MINUTE", ""+PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("minute", -1));
        Log.d("HOUR", ""+ PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("hour", -1));

        AlarmManager alarm =  (AlarmManager)getSystemService(Context.ALARM_SERVICE); //TODO: SETREPEATING IS CALLED AT CREATION.
        alarm.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pintent); //TODO: setExact vs setReapeating SKRIV OM VALGET OG API19

        return super.onStartCommand(intent, flags, startId); //TODO: VS SERVICE_START NOT STICKY?
    }
}
