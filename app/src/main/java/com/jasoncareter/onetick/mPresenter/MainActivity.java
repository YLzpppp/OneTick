package com.jasoncareter.onetick.mPresenter;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jasoncareter.onetick.R;
import com.jasoncareter.onetick.mView.homeWithBackDrop;

import java.security.cert.TrustAnchor;


public class MainActivity extends AppCompatActivity  implements NavigationHost{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeWithBackDrop homefragment = (homeWithBackDrop) getSupportFragmentManager().findFragmentById(R.id.home_fragment);

        //禁用应用内屏幕旋转
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.parent ,new homeWithBackDrop()).commit() ;
        }

    }

    @Override
    public void navigateToFrag(Fragment fragment ,boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.parent ,fragment);
        if( addToBackStack ){
            transaction.addToBackStack(null) ;
        }
        transaction.commit() ;
    }

}


