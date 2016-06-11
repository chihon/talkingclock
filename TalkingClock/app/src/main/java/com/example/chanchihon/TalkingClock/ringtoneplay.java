package com.example.chanchihon.TalkingClock;

import android.annotation.TargetApi;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

public class ringtoneplay extends Service {


    MediaPlayer song;
    int id;
    boolean running;
    String outputFile = null;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        String state = intent.getExtras().getString("extra");

        Log.e("state??", state);
        outputFile= Environment.getExternalStorageDirectory().getAbsolutePath()+"/recording.3gp";


        assert state != null;
        switch (state){
            case "on":
                id = 1;
                break;
            case "off":
                id = 0;
                break;
            default:
                id = 0;
                break;
        }

        if(!this.running && id == 1){
            Log.e("here??", "1");
            //song  = MediaPlayer.create(this, R.raw.ne);
            try
            {
                song.setDataSource(outputFile);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            try
            {
                song.prepare();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            song.start();
            //song.start();
            this.running = true;
            this.id = 0;
            }
        else if(this.running && id == 0){
            Log.e("here??", "2");
            song.stop();
            song.reset();
            this.running = false;
            this.id = 0;
        }
        else if(!this.running && id == 0){
            Log.e("here??", "3");
            this.running = false;
            this.id = 0;
            }
        else if(this.running && id == 1){
            Log.e("here??", "4");
            this.running = true;
            this.id = 1;
        }
        else;

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.running = false;
    }


}
