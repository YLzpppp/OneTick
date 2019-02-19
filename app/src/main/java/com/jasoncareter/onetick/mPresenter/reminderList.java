package com.jasoncareter.onetick.mPresenter;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.BoringLayout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jasoncareter.onetick.R;
import com.jasoncareter.onetick.mModel.OTRemAdapter;
import com.jasoncareter.onetick.mModel.OTRemDataObject;
import com.jasoncareter.onetick.mView.itemtouchcallback;
import com.jasoncareter.onetick.mView.reminderViewItemdecoration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 *   mostly are saved reminders , and some to-do things on the top of this page , sorted by setted time
 *   function:
 *      show reminders
 *      modify single item through fullscreen dialog or other ways
 *      check all the items , sort by time the user setted
 *      contains quick delete button
 */
public class reminderList extends AppCompatActivity {

    private static final String TAG = reminderList.class.getName();

    private  String string;
    private StringBuilder str = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list );

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        ArrayList<OTRemDataObject> arrayList = new ArrayList<OTRemDataObject>();
        arrayList.add( new OTRemDataObject("6 30"," ","get up "));
        arrayList.add(new OTRemDataObject("7 30","","go to school"));
        arrayList.add( new OTRemDataObject("6 30"," ","get up "));
        arrayList.add(new OTRemDataObject("7 30","","go to school"));
        arrayList.add( new OTRemDataObject("6 30"," ","get up "));
        arrayList.add(new OTRemDataObject("7 30","","go to school"));
        arrayList.add( new OTRemDataObject("6 30"," ","get up "));
        arrayList.add(new OTRemDataObject("7 30","","go to school"));
        arrayList.add( new OTRemDataObject("6 30"," ","get up "));
        arrayList.add(new OTRemDataObject("7 30","","go to school"));


        RecyclerView recyclerView = findViewById(R.id.recyclerview );
        recyclerView.setHasFixedSize(true);



        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext() , 1 , GridLayout.VERTICAL , false );
        recyclerView.setLayoutManager(gridLayoutManager);
        int marbot = getApplicationContext().getResources().getDimensionPixelSize(R.dimen.reminder_itemdeco_margin_bot );
        int top = marbot/3 ;
        recyclerView.addItemDecoration(new reminderViewItemdecoration(marbot,top));
        OTRemAdapter adapter = new OTRemAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(new itemtouchcallback());



        ImageView imageView= findViewById(R.id.magic_tab) ;
        Button cancel_button = (Button)findViewById(R.id.new_reminder_cancel );
        Button done_button = findViewById(R.id.new_reminder_done );
        LinearLayout newreminderContainer = findViewById(R.id.new_reminder );


        GestureDetector magicTabDetector = new GestureDetector(getApplicationContext(),
                new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        reminderList.this.finish();
                        overridePendingTransition(R.anim.mainactivity_show , R.anim.reminderlist_exit );
                        return true;
                    }

                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        newreminderContainer.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE );
                        return true;
                    }
                });

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return magicTabDetector.onTouchEvent(event);
            }
        });


        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newreminderContainer.setVisibility(View.GONE );
                recyclerView.setVisibility(View.VISIBLE );
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newreminderContainer.setVisibility(View.GONE );
                recyclerView.setVisibility(View.VISIBLE );
            }
        });
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ImageButton imagicButton = (ImageButton)findViewById(R.id.magic_tab );
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


