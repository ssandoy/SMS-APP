package com.example.ssandoy.s236305_mappe2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

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
        PendingIntent pintent = PendingIntent.getService(this,	0,	i,	PendingIntent.FLAG_UPDATE_CURRENT);


        cal.set(Calendar.MINUTE, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("minute", 1));
        cal.set(Calendar.HOUR_OF_DAY, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("hour", 1));
        cal.set(Calendar.SECOND, 0);
        AlarmManager alarm  = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        long time = 0;
        if(cal.getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) //CHECK IF TIME IS IN THE PAST, THEN EXECUTE NEXT DAY
        {
            time = cal.getTimeInMillis() + 1000*60*60*24;
            cal.setTimeInMillis(time);
        } else {
            time = cal.getTimeInMillis();
        }

        alarm.setRepeating(AlarmManager.RTC_WAKEUP, time, AlarmManager.INTERVAL_DAY, pintent);
        return Service.START_NOT_STICKY;
    }
}
