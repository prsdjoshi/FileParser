package com.commodity.scrolltextviewdemo;

import android.graphics.Bitmap;

import rx.Observable;
import rx.Subscriber;

class MyResult {



    public String getName() {
        return name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }


    public MyResult(String name, Bitmap bitmap) {
        this.name = name;
        this.bitmap = bitmap;
    }

    private String name;
    private Bitmap bitmap;


}
