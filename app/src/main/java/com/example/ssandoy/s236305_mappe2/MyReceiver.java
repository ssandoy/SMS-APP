package com.example.ssandoy.s236305_mappe2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, SendMessagePeriodicService.class);
        context.startService(startServiceIntent);
    }
}
