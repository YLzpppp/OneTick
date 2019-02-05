package com.jasoncareter.onetick.mPresenter;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jasoncareter.onetick.R;
import com.jasoncareter.onetick.mView.homeWithBackDrop;


public class MainActivity extends AppCompatActivity  {
    private int widthPixels; //宽度（像素）
    private int heightPixels; //高度 （像素）
    private float density ; // 屏幕密度
    private int densityDpi ; // 屏幕密度DPI  （ density per inch  |   standard160dpi in android
    private DisplayMetrics displayMetrics = new DisplayMetrics() ;
    private float wdp;
    private float hdp;

    public void setdp(float wdp ,float hdp){
        this.wdp = wdp;
        this.hdp = hdp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取设备屏幕信息，传给 {@link   fragment_home_with_back_drop.xml }
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        widthPixels = displayMetrics.widthPixels ;
        heightPixels = displayMetrics.heightPixels ;
        density = displayMetrics.density ;
        densityDpi = displayMetrics.densityDpi ;
        float widthdp = widthPixels/density ;
        float heightdp = heightPixels/density;
        setdp(widthdp , heightdp);

        homeWithBackDrop homefragment = (homeWithBackDrop) getSupportFragmentManager().findFragmentById(R.id.home_fragment);



        //禁用应用内屏幕旋转
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.parent ,new homeWithBackDrop()).commit() ;
        }

    }

    public String AcommunicateF( ) {
        return String.valueOf(wdp)+"  x  " +String.valueOf(hdp) ;
    }
}


