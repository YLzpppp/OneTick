package com.jasoncareter.onetick.mPresenter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.jasoncareter.onetick.R;
import com.jasoncareter.onetick.mModel.OTAccAdapter;
import com.jasoncareter.onetick.mModel.OTAccDataObject;
import com.jasoncareter.onetick.mView.accountViewItemdecoration;

import java.util.ArrayList;

public class MainAccActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_acc);

        ArrayList<OTAccDataObject> myacdata = new ArrayList<OTAccDataObject>();

        myacdata.add(new OTAccDataObject("QQ" ,"812068182","123456"));
        myacdata.add(new OTAccDataObject("gmail","jasoncareter@gmail.com","12345678"));
        myacdata.add(new OTAccDataObject("github","jasoncareter@gmail.com","123"));

        RecyclerView irecyclerView = findViewById(R.id.account_recyclerView );
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainAccActivity.this,LinearLayoutManager.VERTICAL ,false);

        irecyclerView.setHasFixedSize(true);
        irecyclerView.setLayoutManager(layoutManager);

        int decoMarginBot = getResources().getDimensionPixelSize(R.dimen.account_itemdeco_margin_bot );
        irecyclerView.addItemDecoration(new accountViewItemdecoration(decoMarginBot));

        irecyclerView.setAdapter(new OTAccAdapter(myacdata));

        ImageView imageView= findViewById(R.id.acc_magic_tab) ;

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainAccActivity.this.finish();
                overridePendingTransition(R.anim.mainactivity_show , R.anim.reminderlist_exit );
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        ImageButton imagicButton = (ImageButton)findViewById(R.id.acc_magic_tab );
        float magicTabHeight = getResources().getDimensionPixelSize(R.dimen.magic_tab_height );

        AnimatorSet magS = new AnimatorSet();

        ObjectAnimator magA = ObjectAnimator.ofFloat( imagicButton ,"alpha",0f,1f);
        magA.setDuration(100);
        ObjectAnimator magT = ObjectAnimator.ofFloat(imagicButton,"translationY",magicTabHeight ,0f);
        magT.setDuration(600);
        magT.setInterpolator(new AccelerateDecelerateInterpolator());

        magS.setStartDelay(280);
        magS.playTogether(magA,magT);
        magS.start();
    }
}
