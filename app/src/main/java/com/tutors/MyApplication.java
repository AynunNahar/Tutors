package com.tutors;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Princess on 8/8/2017.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
