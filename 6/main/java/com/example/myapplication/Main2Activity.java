package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    TextView textView,textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView)findViewById(R.id.textView2) ;
        Bundle bundle = getIntent().getExtras();
        String name, pwd;
        if (bundle != null) {
            name = bundle.getString("name");
            pwd = bundle.getString("password");
            textView.setText(name);
            textView2.setText(pwd);
        }
    }
}

