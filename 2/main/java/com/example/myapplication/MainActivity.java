package com.example.myapplication;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ProgressBar p;
    private String percentValue = "0%";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        p = (ProgressBar) findViewById(R.id.progressBar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(0);
            }
        });
    }
    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            if (p.getProgress()<100){
                handler.postDelayed(runnable,500);
            }
            else{
                Toast.makeText(MainActivity.this,"加载完成", Toast.LENGTH_SHORT).show();
            }
        }
    };
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            p.setProgress(p.getProgress()+1);
            handler.sendEmptyMessage(0);
        }
    };
}
