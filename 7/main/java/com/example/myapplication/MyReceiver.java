package com.example.myapplication;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("wust.zz.mybroadcast")){
            Bundle bundle = intent.getExtras();
            float a =(float)(Math.random()*80);
            Toast.makeText(context,a+"℃", Toast.LENGTH_SHORT).show();
        }

    }
}

