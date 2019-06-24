package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        View.OnClickListener listener =new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent("wust.zz.mybroadcast");
                sendBroadcast(intent);
                Log.d("msg","发送成功");
            }
        };
        button.setOnClickListener(listener);

    }

}

