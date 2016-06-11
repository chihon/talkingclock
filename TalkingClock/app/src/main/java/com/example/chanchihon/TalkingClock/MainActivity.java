package com.example.chanchihon.TalkingClock;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    AlarmManager manager;
    TimePicker timePicker;
    TextView update;
    Context context;
    PendingIntent pending_intent;
    Button play,record,stop;
    MediaRecorder audioRecord;
    String outputFile = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play=(Button)findViewById(R.id.play);
        record=(Button)findViewById(R.id.record);
        stop=(Button)findViewById(R.id.stop);
        play.setEnabled(false);
        stop.setEnabled(false);
        outputFile= Environment.getExternalStorageDirectory().getAbsolutePath()+"/recording.3gp";
        audioRecord=new MediaRecorder();
        audioRecord.setAudioSource(MediaRecorder.AudioSource.MIC);
        audioRecord.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        audioRecord.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        audioRecord.setOutputFile(outputFile);
        this.context = this;


        timePicker = (TimePicker) findViewById(R.id.timePicker);

        manager = (AlarmManager) getSystemService(ALARM_SERVICE);

        update = (TextView) findViewById(R.id.update);

        final Calendar calendar = Calendar.getInstance();

        Button off = (Button) findViewById(R.id.off);

        final Intent my_intent = new Intent(this.context, receiver.class);

        record.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                try
                {
                    audioRecord.prepare();
                    audioRecord.start();
                }
                catch (IllegalStateException | IOException e)
                {
                    e.printStackTrace();
                }

                record.setEnabled(false);
                stop.setEnabled(true);

                Toast.makeText(MainActivity.this, "Recording Starting...", Toast.LENGTH_SHORT).show();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                audioRecord.stop();
                audioRecord.release();
                audioRecord=null;

                stop.setEnabled(false);
                play.setEnabled(true);

                Toast.makeText(MainActivity.this, "Audio Record Succesfully", Toast.LENGTH_SHORT).show();

            }
        });

        play.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) throws IllegalArgumentException,SecurityException,IllegalStateException
            {
                MediaPlayer mediaPlayer=new MediaPlayer();
                try
                {
                    mediaPlayer.setDataSource(outputFile);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                try
                {
                    mediaPlayer.prepare();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                mediaPlayer.start();

            }
        });

        assert off != null;
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_alarm_text("alarm off!");

                manager.cancel(pending_intent);


                my_intent.putExtra("extra","off");
                sendBroadcast(my_intent);
            }

        });

        Button on = (Button) findViewById(R.id.on);

        assert on != null;
        on.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                calendar.set(Calendar.MINUTE, timePicker.getMinute());

                Log.i("clickon", "OK?");

                set_alarm_text("alarm set to "+timePicker.getHour()+":"+timePicker.getMinute());


                my_intent.putExtra("extra","on");

                pending_intent = PendingIntent.getBroadcast(MainActivity.this, 0, my_intent,PendingIntent.FLAG_UPDATE_CURRENT);

                manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pending_intent);
            }

        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void set_alarm_text(String output) {
        update.setText(output);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.chanchihon.TalkingClock/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.chanchihon.TalkingClock/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
