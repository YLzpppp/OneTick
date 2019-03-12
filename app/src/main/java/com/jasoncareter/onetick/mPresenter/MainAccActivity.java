package com.jasoncareter.onetick.mPresenter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.jasoncareter.onetick.R;
import com.jasoncareter.onetick.mModel.OTAccAdapter;
import com.jasoncareter.onetick.mModel.OTAccDataObject;
import com.jasoncareter.onetick.mModel.OTContentProvider;
import com.jasoncareter.onetick.mModel.OTContract;
import com.jasoncareter.onetick.mView.accountViewItemdecoration;

import java.io.IOException;
import java.util.ArrayList;

public class MainAccActivity extends AppCompatActivity {

    private static TextInputLayout platforminputlayout ;
    private static TextInputEditText platforminput;
    private static TextInputLayout accountinputlayout;
    private static TextInputEditText accountinput;
    private static TextInputEditText passwordinput;
    private static TextInputLayout passwordinputlayout;
    private String platform = "";
    private String account = "";
    private String password = "";

    public OTContentProvider contentProvider = new OTContentProvider();
    public OTContract contract = new OTContract();
    public OTContract.ACCOUNT_ENTRY account_entry = new OTContract.ACCOUNT_ENTRY();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_acc);

        ArrayList<OTAccDataObject> myacdata = new ArrayList<OTAccDataObject>();

        myacdata.add(new OTAccDataObject("QQ" ,"812068182","example"));
        myacdata.add(new OTAccDataObject("github","jasoncareter@gmail.com","example"));

        ImageView imagicButton= findViewById(R.id.acc_magic_tab) ;
        RecyclerView irecyclerView = findViewById(R.id.account_recyclerView );
        Button accDone = findViewById(R.id.acc_floating_done);
        Button accCancel=findViewById(R.id.acc_floating_cancel);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainAccActivity.this,LinearLayoutManager.VERTICAL ,false);
        ConstraintLayout accFloatingContainer = findViewById(R.id.acc_floating_container);
        RecyclerView accRecyclerView = findViewById(R.id.account_recyclerView);
        platforminputlayout = findViewById(R.id.acc_floating_platform_input_container);
        platforminput = findViewById(R.id.acc_floating_platform_input);
        accountinputlayout = findViewById(R.id.acc_floating_account_input_container);
        accountinput = findViewById(R.id.acc_floating_account_input);
        passwordinputlayout = findViewById(R.id.acc_floating_password_input_container);
        passwordinput = findViewById(R.id.acc_floating_password_input);


        irecyclerView.setHasFixedSize(true);
        irecyclerView.setLayoutManager(layoutManager);

        int decoMarginBot = getResources().getDimensionPixelSize(R.dimen.account_itemdeco_margin_bot );
        irecyclerView.addItemDecoration(new accountViewItemdecoration(decoMarginBot));

        irecyclerView.setAdapter(new OTAccAdapter(myacdata));
        //TODO : change the magic_tab touch Event Listener mode
        GestureDetector maigcButtonDetector = new GestureDetector(getApplicationContext() ,
                new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        MainAccActivity.this.finish();
                        overridePendingTransition(R.anim.reminderlist_show ,R.anim.mainactivity_exit);
                        return true;
                    }

                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        accRecyclerView.setVisibility(View.GONE);
                        accFloatingContainer.setVisibility(View.VISIBLE);
                        return true;
                    }
                });
        imagicButton.setOnTouchListener( new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return maigcButtonDetector.onTouchEvent(event);
            }
        });

        // set the Done button listening event
        accDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //TODO : set the acc_floating's visibility to GONE , handling the input source(texts include account and password) and bring RecyclerView to front
            // update the recyclerview first ,after that , we bring recyclerview back
                accRecyclerView.setVisibility(View.VISIBLE);
                accFloatingContainer.setVisibility(View.GONE);
                // update data
                platforminput.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }
                    @Override
                    public void afterTextChanged(Editable s) { platform = s.toString(); }
                });
                //account text
                accountinput.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) { }
                    @Override
                    public void afterTextChanged(Editable s) { account = s.toString(); }
                });
                //password text
                passwordinput.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) { }
                    @Override
                    public void afterTextChanged(Editable s) { password = s.toString() ; }
                });
                /*
                 * 将获取到的文本录入数据库
                 */
                Uri tableInser = contract.ACCOUNT_CONTENT_URI ;
                ContentValues values = new ContentValues();
                values.put( account_entry.ACCOUNT_PLATFORM , platform );
                values.put(account_entry.ACCOUNT_USERNAME , account );
                values.put(account_entry.ACCOUNT_PASSWORD , password );
                contentProvider.insert(tableInser , values);

                accountinput.getText().clear();
                platforminput.getText().clear();
                passwordinput.getText().clear();

                myacdata.add( new OTAccDataObject(platform, account,password));

            }
        });
        //set the Cancel button listening event
        accCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //TODO: set the acc_floating's visibility to GONE , clear the inputEditText,bring recyclerView to front
                accFloatingContainer.setVisibility(View.GONE);
                accRecyclerView.setVisibility(View.VISIBLE);

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
