package com.jasoncareter.onetick.mPresenter;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jasoncareter.onetick.R;
import com.jasoncareter.onetick.mView.homeWithBackDrop;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().add(R.id.parent ,new homeWithBackDrop()).commit() ;
        }

    }
}
