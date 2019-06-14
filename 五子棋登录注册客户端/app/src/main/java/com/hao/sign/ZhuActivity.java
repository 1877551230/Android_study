package com.hao.sign;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Looper;
import android.support.annotation.BinderThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hao.gamefivechess.MainActivity;
import com.hao.gamefivechess.R;
import com.hao.login.Main2Activity;
import com.hao.login.Mysqlist;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

public class ZhuActivity extends AppCompatActivity {

    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";//验证密码是否有特殊符号或长度不满6位
    private SQLiteDatabase sdb;
    private Mysqlist mys;
    String loginUrl="http://192.168.43.99:8080/regist.jsp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu);
        Button Signin = (Button)findViewById(R.id.Signin);

        mys = new Mysqlist(this, "zhu_c", null, 1);//使用halper创建数据库
        sdb = mys.getWritableDatabase();

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
new Thread(new Runnable() {
    @Override
    public void run() {
        EditText phonenumber = (EditText) findViewById(R.id.phonenumber);
        EditText password2 = (EditText) findViewById(R.id.password2);
        EditText password3 = (EditText) findViewById(R.id.password3);

        String name = phonenumber.getText().toString().trim();
        String pass = password2.getText().toString().trim();
        String pass3 = password3.getText().toString().trim();
        final String r = regist(name,pass,pass3);


            if (name == null || "".equals(name) || pass == null || "".equals(pass) || pass3 == null || "".equals(pass3)) {
                Looper.prepare();Toast.makeText(ZhuActivity.this, "账号与密码不能为空", Toast.LENGTH_SHORT).show();Looper.loop();
            } else {
                String number = phonenumber.getText().toString();
                boolean judge = isMobile(number);
                String pa = password2.getText().toString();
                boolean judge1 = isPassword(pa);
                if (judge == false) {
                    Looper.prepare(); Toast.makeText(ZhuActivity.this, "请输入正确的电话号码", Toast.LENGTH_SHORT).show();Looper.loop();
                }
                if (judge == true&&judge1 == false) {

                        Looper.prepare();Toast.makeText(ZhuActivity.this, "密码最少6位，不能有特殊符号", Toast.LENGTH_SHORT).show();Looper.loop();
                }
                if (judge == true && judge1 == true) {
                    if (pass.equals(pass3)) {

                        if(filterHtml(r).equals("ok")) {
                            Intent intent2 = new Intent(ZhuActivity.this, Main2Activity.class);
                            startActivity(intent2);//启动跳转
                            Looper.prepare();
                            Toast.makeText(ZhuActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            sdb.execSQL("insert into user_mo(name,pass)values('" + name + "','" + pass + "')");

                            Looper.loop();

                        }
                        else{Looper.prepare();
                            Toast.makeText(ZhuActivity.this, "注册失败（服务器故障或该帐号已被注册）", Toast.LENGTH_SHORT).show();
                            Looper.loop();}
                    } else {
                        Looper.prepare();Toast.makeText(ZhuActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();Looper.loop();
                    }

                }
            }


    }
}).start();
            }
        });
    }
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }
    public static boolean isMobile(String number) {
 /*
 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
 联通：130、131、132、152、155、156、185、186
 电信：133、153、180、189、（1349卫通）
 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
 */
        String num = "[1][35789]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }
    public String regist(String username,String psd,String re_pwd){
        String msg="";
        try {
            URL url = new URL(loginUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setConnectTimeout(500); httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Charset", "UTF-8");

            httpURLConnection.connect();

            //post请求传递参数
            String data = "name=" + username+"&pass=" +psd+"&pass3="+re_pwd;   //参数之间用&连接
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


