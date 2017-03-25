package com.example.ankit.findyourfellow;

/**
 * Created by Ankit on 2017-03-12.
 */


import android.app.Application;

import com.firebase.client.Firebase;

public class FindYourFellow extends Application{

    @Override
    public void onCreate()
    {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
