package com.hao.login;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hao.gamefivechess.MainActivity;
import com.hao.gamefivechess.R;
import com.hao.sign.ZhuActivity;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {


    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";//验证密码是否有特殊符号或长度不满6位
    private SQLiteDatabase w;
    private SQLiteDatabase r;
    private Mysqlist mys;
    private List<St> mdata;
    private String name;
    private String pass;
    public static  boolean Youke=false;
    public boolean getYouke(){
        return Youke;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        mys = new Mysqlist(this, "zhu_c", null, 1);//使用halper创建数据库
        Button login = (Button)findViewById(R.id.login);
        Button sign = (Button)findViewById(R.id.sign);
        final Button youke = (Button)findViewById(R.id.youke);
        r = mys.getReadableDatabase();
        w = mys.getWritableDatabase();
        mdata = new ArrayList<St>();
        Cursor query = r.rawQuery("select * from user_mo", null);
        while (query.moveToNext()) {
            int index1 = query.getColumnIndex("name");
            int index2 = query.getColumnIndex("pass");
            name = query.getString(index1);
            pass = query.getString(index2);
            mdata.add(new St(0, name, pass));


            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditText username=(EditText)findViewById(R.id.username);
                    EditText password=(EditText)findViewById(R.id.password);

                    String name1 = username.getText().toString().trim();
                    String pass1 = password.getText().toString().trim();
                    if (name1.equals(name)&&pass1.equals(pass)){
                        Toast.makeText(Main2Activity.this,"登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(Main2Activity.this,"账号与密码输入不正确",Toast.LENGTH_SHORT).show();
                    }








                }
            });
            sign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(Main2Activity.this, ZhuActivity.class);
                    startActivity(intent1);
                }
            });
            youke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Youke=true;
                    Intent intent1 = new Intent(Main2Activity.this, MainActivity.class);
                    startActivity(intent1);
                }
            });

        }

    }
}