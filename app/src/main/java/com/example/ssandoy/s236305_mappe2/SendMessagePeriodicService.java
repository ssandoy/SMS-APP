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
        PendingIntent pintent = PendingIntent.getService(this,	0,	i,	0);

        //TODO: IMPLEMENT VALUES FROM SHAREDPREFERENCES

        cal.set(Calendar.MINUTE, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("min", 1));
        cal.set(Calendar.HOUR_OF_DAY, PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("time", 1));

        //TODO: LES OM BROADCASTRECEIVER OG IMPLEMENTER DETTE!

        AlarmManager alarm =  (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(), 1000*60*60*24,pintent); //Multiplikasjonen som utføres gjør at det utføres daglig

        return super.onStartCommand(intent, flags, startId); //TODO: DENNE? eller return Service.STAR_NOT_STICKY?
    }
}
