package com.example.android.binarie;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ima = new ImageView(this);
        ima.setImageResource(R.drawable.fond);

        ima.setAdjustViewBounds(true);

        FrameLayout mFrame = new FrameLayout(this);

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
        // Stop the Timer
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(createClockTickIntent(context));
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Create the Timer
        Context context = getApplicationContext();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), 60 * 1000, createClockTickIntent(context));
    }

    void updateClock(){

        Calendar rightNow = Calendar.getInstance();

        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
        int currentMinute = rightNow.get(Calendar.MINUTE);
        currentHour=12;
        currentMinute=35;





        updateBin("h0_8", currentHour%10 , 8, "R.color.cJaune8");
        updateBin("h0_4", currentHour%10 , 4, "R.color.cJaune4");
        updateBin("h0_2", currentHour%10 , 2, "R.color.cJaune2");
        updateBin("h0_1", currentHour%10 , 1, "R.color.cJaune1");

        
        updateBin("m1_4", currentMinute/10 , 4, "R.color.cVert4");
        updateBin("m1_2", currentMinute/10 , 2, "R.color.cVert2");
        updateBin("m1_1", currentMinute/10 , 1, "R.color.cVert1");
        
        updateBin("m0_8", currentMinute%10 , 8, "R.color.cBleu8");
        updateBin("m0_4", currentMinute%10 , 4, "R.color.cBleu4");
        updateBin("m0_2", currentMinute%10 , 2, "R.color.cBleu2");
        updateBin("m0_1", currentMinute%10 , 1, "R.color.cBleu1");

        updateBin("h1_1", 2 , 1, "R.color.cRouge1");
        updateBin("h1_2", 2 , 2, "R.color.cRouge2");


        //ima.invalidate();
        
    }

    void updateBin(String pName, int digit, int h, String col){
        VectorChildFinder vector = new VectorChildFinder(this, R.drawable.fond, ima);
        VectorDrawableCompat.VFullPath p1= vector.findPathByName(pName);
        //int mcol =  getResources().getColor(getResources().
        //        getIdentifier(col, "color", getPackageName()));
        VectorDrawableCompat.VFullPath p2= vector.findPathByName("h0_8");
        p2.setFillColor(Color.YELLOW);

        //Very Ugly
        digit = digit% (h*2);
        if(digit/h==1)
            p1.setFillColor(Color.RED);
        else
            p1.setFillColor(Color.BLUE);
        //ima.invalidate();

    }

}