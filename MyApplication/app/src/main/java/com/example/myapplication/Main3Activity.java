package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher;
import java.util.ArrayList;
import android.content.Intent;

public class Main3Activity extends AppCompatActivity implements ViewSwitcher.ViewFactory,View.OnTouchListener{
    private ImageSwitcher is;//声明ImageSwitcher布局
    private LinearLayout point_layout;//声明导航圆点的布局
    //图片id数组（需要与GridActivity中的图片资源数组一一对应）
    int[] images={R.drawable.h1,R.drawable.h2,R.drawable.h3,R.drawable.h4,R.drawable.h5,R.drawable.h6,R.drawable.h7,R.drawable.h8,R.drawable.h9};
    //实例化存储导航圆点的集合
    ArrayList<ImageView> points = new ArrayList<>();
    int index;//声明index，记录图片id数组下标
    float startX;//手指接触屏幕时X的坐标（演示左右滑动）
    float endX;//手指离开屏幕时的坐标（演示左右滑动）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        //获取GridActivity中设置的intent
        Intent intent = getIntent();
        //获取GridActivity中得到的图片下标，并随意设置默认值
        index = intent.getIntExtra("select",0);
        is = (ImageSwitcher) findViewById(R.id.is);
        is.setFactory(this);//通过工厂实现ImageSwitcher
        initpoint();
        is.setOnTouchListener(this);//设置触摸事件
    }
    //初始化导航圆点的方法
    private void initpoint() {
        point_layout= (LinearLayout) findViewById(R.id.point_layout);
        int count = point_layout.getChildCount();//获取布局中圆点数量
        for(int i =0;i<count;i++){
            //将布局中的圆点加入到圆点集合中
            points.add((ImageView) point_layout.getChildAt(i));
        }
        //设置GridActivity中选中图片对应的圆点状态为触摸实心状态
        points.get(index).setImageResource(R.mipmap.touched_holo);
    }
    //设选中图片对应的导航原点的状态
    public void setImageBackground(int selectImage) {
        for(int i=0;i<points.size();i++){
            //如果选中图片的下标等于圆点集合中下标的id，则改变圆点状态
            if(i==selectImage){
                points.get(i).setImageResource(R.mipmap.touched_holo);
            }else{
                points.get(i).setImageResource(R.mipmap.default_holo);
            }
        }
    }
    //实现ViewFactory的方法实例化imageView（这里未设置ImageView的属性）
    @Override
    public View makeView() {
        //实例化一个用于切换的ImageView视图
        ImageView iv = new ImageView(this);
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        //默认展示的第一个视图为images[index](主页面跳转过来的图片)
        iv.setImageResource(images[index]);
        return iv;
    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //按下屏幕
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            startX=event.getX();//获取按下屏幕时X轴的坐标
            //手指抬起
        }else if (event.getAction()==MotionEvent.ACTION_UP){
            endX=event.getX();
            //判断结束坐标大于起始坐标则为下一张（为避免误操作，设置30的判断区间）
            if(startX-endX>30){
                //三目运算判断当前图片已经为最后一张，则从头开始
                index = index+1<images.length?++index:0;
                //使用系统自带的切换出入动画效果（也可以像ViewFlipper中一样自定义动画效果）
                is.setInAnimation(this,android.R.anim.fade_in);
                is.setOutAnimation(this,android.R.anim.fade_out);

                //判断结束坐标小于于起始坐标则为上一张（为避免误操作，设置30的判断区间）
            }else if(endX-startX>30){
                //三目运算判断当前图片已经为第一张，则上一张为数组内最后一张图片
                index = index-1>=0?--index:images.length-1;
                is.setInAnimation(this,android.R.anim.fade_in);
                is.setOutAnimation(this,android.R.anim.fade_out);
            }
            //设置ImageSwitcher的图片资源
            is.setImageResource(images[index]);
            //调用方法设置圆点对应状态
            setImageBackground(index);
        }
        return true;
    }
}