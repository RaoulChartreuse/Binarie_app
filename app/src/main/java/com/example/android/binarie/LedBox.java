package com.example.android.binarie;

import android.graphics.Color;
import android.util.Log;
import android.widget.ImageView;

import com.devs.vectorchildfinder.VectorChildFinder;
import com.devs.vectorchildfinder.VectorDrawableCompat;

public class LedBox {

    private VectorDrawableCompat.VFullPath mPath;
    //couleur pour plus tard
    private int mh;
    private boolean mtype; //type minute ou heure
    private static final String TAG = "LedBox";
    private int mrang;

    public LedBox(String name, int h, int rang, boolean type, VectorChildFinder vector ){
        mPath = vector.findPathByName(name);
        mh = h;
        mtype = type;
        mrang = rang;

        Log.i(TAG, "Creation de LedBox Name " + name + " , Type :"+ mtype + " , h: " + h+
                ", rang : "+mrang+"\n");
    }

    public void fillLed(int h, int m){
        int digit;

        if (mtype) digit = h;
        else digit = m;
        if (mrang==1){
            digit = digit %100;
            digit = digit/10;
        }
        else digit = digit%10;

        digit = digit% (mh*2);
        if(digit/mh==1)
            mPath.setFillColor(Color.RED);
        else
            mPath.setFillColor(Color.WHITE);
    }
}
