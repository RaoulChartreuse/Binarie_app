package com.example.android.binarie;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;

import java.util.Calendar;

public class BinarieActivity extends Activity {

    ImageView ima;
    public static String CLOCK_UPDATE = "com.example.Binarie.CLOCK_UPDATE";
    LedBox mLed[];

    //From stackoverflow
    ///https://stackoverflow.com/questions/5457186/best-way-to-update-textview-every-minute-on-the-minute
    BroadcastReceiver _broadcastReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ima = new ImageView(this);
        ima.setImageResource(R.drawable.fond);

        ima.setAdjustViewBounds(true);

        FrameLayout mFrame = new FrameLayout(this);

        String names[]= {"h1_1", "h1_2",
                        "h0_1", "h0_2","h0_4", "h0_8",
                        "m1_1", "m1_2","m1_4", "m1_8",
                        "m0_1", "m0_2","m0_4", "m0_8"
        };
        mLed = new LedBox[names.length];
        VectorChildFinder vector = new VectorChildFinder(this, R.drawable.fond, ima);
        for (int i=0; i<names.length; i++ ){
            mLed[i] = new LedBox(names[i], Integer.valueOf(names[i].substring(3)),
                    Integer.valueOf(names[i].substring(1,2)),
                    names[i].substring(0,1).equals("h"),  vector);
        }

        updateClock();


        mFrame.addView(ima);

        setContentView(mFrame);


    }


    private PendingIntent createClockTickIntent(Context context) {
        Intent intent = new Intent(CLOCK_UPDATE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }


    @Override
    protected void onStop(){
        super.onStop();
        Context context = getApplicationContext();

        if (_broadcastReceiver != null)
            unregisterReceiver(_broadcastReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Create the Timer
        Context context = getApplicationContext();

        //From stack overflow
        _broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0)
                    updateClock();
            }
        };

        registerReceiver(_broadcastReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    void updateClock(){

        Calendar rightNow = Calendar.getInstance();

        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
        int currentMinute = rightNow.get(Calendar.MINUTE);

        for ( LedBox led  : mLed )  led.fillLed(currentHour, currentMinute);



        ima.invalidate();
        
    }



}