package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    EditText namer;
    EditText passwd;
    Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        namer = (EditText) findViewById(R.id.name);
        passwd = (EditText) findViewById(R.id.passwd);
        button1 = (Button) findViewById(R.id.button1);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, pwd;
                name = namer.getText().toString();
                pwd = passwd.getText().toString();
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("password",pwd);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}

