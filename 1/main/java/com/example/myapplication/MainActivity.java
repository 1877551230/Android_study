package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private EditText username;
    private EditText passwd;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView() {
        button = (Button) findViewById(R.id.button);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        username = (EditText) findViewById(R.id.username);
        passwd = (EditText) findViewById(R.id.passwd);
        output();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              // if (username.getText().toString().equals("SHOWR886") && passwd.getText().toString().equals("123456")) {
                    input();
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(intent);
                    finish();
               // } else {
                    //Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                //}
            }
        });
    }

    private void output() {
        SharedPreferences shared = getSharedPreferences("mypsd", MODE_PRIVATE);
        String name1 = shared.getString("name", "");
        String psd1 = shared.getString("psd", "");
        boolean ischecked1 = shared.getBoolean("isChecked", false);
        username.setText(name1);
        passwd.setText(psd1);
        checkBox.setChecked(ischecked1);
    }

    private void input() {
        SharedPreferences.Editor edit = getSharedPreferences("mypsd", MODE_PRIVATE).edit();
        if (checkBox.isChecked()) {
            edit.putString("name", username.getText().toString());
            edit.putString("psd", passwd.getText().toString());
            edit.putBoolean("isChecked", true);
        } else {
//            edit.clear();              //若选择全部清除就保留这行代码，注释以下三行
            edit.putString("name", username.getText().toString());//只存用户名
            edit.putString("psd", "");
            edit.putBoolean("isChecked", false);
        }
        edit.commit();

    }
}

