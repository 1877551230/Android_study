package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String> list = new ArrayList<String>();
        list.add("JAVA编程");
        list.add("Android编程");
        list.add("ASP.NET编程");
        list.add("J2EE编程");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_single_choice,list);
        ListView li=(ListView)findViewById(R.id.listView);
        li.setAdapter(adapter);
        li.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        li.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?>parent,View view,int position,long id){
                Toast.makeText(getApplicationContext(),"选中了："+((TextView)view).getText().toString()+",id="+id,Toast.LENGTH_SHORT).show();
            }
        });
    }
}

