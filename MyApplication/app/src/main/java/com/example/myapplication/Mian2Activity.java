package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.content.Intent;


public class Mian2Activity extends AppCompatActivity {
    private GridView gv;
    int[] images={R.drawable.h1,R.drawable.h2,R.drawable.h3,R.drawable.h4,R.drawable.h5,R.drawable.h6,R.drawable.h7,R.drawable.h8,R.drawable.h9};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mian2);
        gv= (GridView)findViewById(R.id.gv);
        gv.setAdapter(new MyAdapter());
        //设置单击GridView中每个item的单击事件
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //使用intend获取要交互的Activity，也就是将要跳转的界面
                Intent intent = new Intent(Mian2Activity.this,Main3Activity.class);
                //通过intent的putExtra方法获取点击图片的下标位置（用于Activity之间数据传输）
                intent.putExtra("select",position);
                //启动要交互的Activity（通过传入包含该Activity的intent）
                startActivity(intent);
            }
        });
    }
    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return images[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if(convertView==null){
                convertView=getLayoutInflater().inflate(R.layout.griditem_layout,null);
                vh= new ViewHolder();
                vh.iv= (ImageView) convertView.findViewById(R.id.imageView);
                convertView.setTag(vh);
            }
            vh= (ViewHolder) convertView.getTag();
            vh.iv.setImageResource(images[position]);
            return convertView;
        }
        class ViewHolder{
            ImageView iv;
        }
    }
}
