package com.example.chanchihon.TalkingClock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class receiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("hello","yo");

        String get_string = intent.getExtras().getString("extra");

        Log.e("key?",get_string);
        Intent service_intent = new Intent(context,ringtoneplay.class);

        service_intent.putExtra("extra",get_string);
        context.startService(service_intent);
    }
}
