package com.hao.login;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Looper;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
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
    String loginUrl="http://192.168.43.99:8080/login.jsp";
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

        }
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   new Thread(new Runnable() {
                       @Override
                       public void run() {
                           EditText username=(EditText)findViewById(R.id.username);
                           EditText password=(EditText)findViewById(R.id.password);

                           String name1 = username.getText().toString().trim();
                           String pass1 = password.getText().toString().trim();
                           final String r=login(name1,pass1);
                               if(filterHtml(r).equals("ok")){
                               Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                               startActivity(intent);
                               Looper.prepare();
                               Toast.makeText(Main2Activity.this, "服务器登录成功", Toast.LENGTH_SHORT).show();Looper.loop();
                               }
                               if ((name1.equals(name) && pass1.equals(pass))){
                                   Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                                   startActivity(intent);
                                   Looper.prepare();
                                   Toast.makeText(Main2Activity.this, "离线登录成功", Toast.LENGTH_SHORT).show();Looper.loop();
                               }


                               else {
                                   Looper.prepare();Toast.makeText(Main2Activity.this, "账号与密码输入不正确", Toast.LENGTH_SHORT).show();Looper.loop();
                               }


                       }
                   }).start();





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
    public String login(String username,String psd){
        String msg="";
        try {
            URL url = new URL(loginUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setConnectTimeout(500); httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");

            httpURLConnection.connect();

            //post请求传递参数
            String data = "name1=" + username+"&pass1=" +psd;   //参数之间用&连接
            //向服务器发送数据（输出流）
            BufferedWriter writer=new BufferedWriter(
                    new OutputStreamWriter(httpURLConnection.getOutputStream(),"UTF-8"));
            writer.write(data);
            writer.close();
           /* httpURLConnection.setConnectTimeout(5000);httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");	//设置uft-8字符集
            httpURLConnection.connect();*/
            int code = httpURLConnection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream()));
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    msg+=line+"\n";
                }
            }
            //return msg;
        }catch (Exception e){
            e.printStackTrace();
        }
        return msg;
    }

    public String filterHtml(String source) { if(null == source){
        return "";
    }
        return source.replaceAll("</?[^>]+>","").trim();
    }

}