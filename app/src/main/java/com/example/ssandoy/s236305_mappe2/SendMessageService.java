package com.example.ssandoy.s236305_mappe2;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.Calendar;

public class SendMessageService extends Service {

    DBHandler db;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        db = new DBHandler(this);
        db.open();
        Calendar cal = Calendar.getInstance();

        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) +1; //INDEXED FROM 0-11
        Log.d("SMS-service",  "MONTH: " + month);
        Log.d("SMS-service", "DAY: " + day);

        Cursor cur = db.hasBirthday(month, day);

        SmsManager smsManager = SmsManager.getDefault();
        String message = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("message","");
        if(cur.moveToFirst()) {
            do{
                Log.d("SMS", "SENDING MESSAGE"); //TODO: WHY NOT LOGGED!?
                smsManager.sendTextMessage(cur.getString(cur.getColumnIndex(db.KEY_PH_NO)),null,message,null,null);
            } while (cur.moveToNext());
        }
        cur.close();
        db.close();

        return super.onStartCommand(intent, flags, startId);
    }
}
