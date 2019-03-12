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
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.BoringLayout;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jasoncareter.onetick.R;
import com.jasoncareter.onetick.mModel.OTRemAdapter;
import com.jasoncareter.onetick.mModel.OTRemData;
import com.jasoncareter.onetick.mModel.OTRemDataObject;
import com.jasoncareter.onetick.mView.itemtouchcallback;
import com.jasoncareter.onetick.mView.reminderViewItemdecoration;

import java.io.FileNotFoundException;
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

    public static ArrayList<OTRemDataObject> arrayList = new ArrayList<OTRemDataObject>();
    private static final String TAG = reminderList.class.getName();
    private int hourofday = 0;
    private int mminute = 0;
    private boolean timeChoosed = false ;
    private OTRemData otRemData  = new OTRemData(reminderList.this);
    private  boolean JSExit = false ;
    private Calendar c = Calendar.getInstance();
    private int iyear = c.YEAR ;
    private int imonth = c.MONTH;
    private int iday = c.DAY_OF_MONTH ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list );

        // here we create a brand new JsonFile for the very first use
        // View.post ( Runnable ) , View.postDelayed( Runnable ) ,  Activity.runOnUiThread( Runnable )
        new Thread(new Runnable() {
            @Override
            public void run() {
                if ( !JSExit ){
                    try {
                        JSExit = true ;
                        otRemData.createJsonFile();
                        otRemData.initJson();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


        // set window flag to make status bar transparent . Based on the build version. the minmum version -- api 19 ( android 4.0 )

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        // TODO:  Init  and  bind views
        ImageView magicTab= findViewById(R.id.magic_tab) ;
        Button cancel_button = (Button)findViewById(R.id.new_reminder_cancel );
        Button done_button = findViewById(R.id.new_reminder_done );
        LinearLayout newreminderContainer = findViewById(R.id.new_reminder );
        RecyclerView recyclerView = findViewById(R.id.recyclerview );
        TimePicker timePicker = findViewById(R.id.new_reminder_timepicker);
        TextInputLayout mainTextLayout = findViewById(R.id.new_reminder_maintext_layout);
        TextInputEditText mainText = findViewById(R.id.new_reminder_maintext);
        TextInputEditText placeText = findViewById(R.id.new_reminder_place);
        TextInputLayout placeTextLayout = findViewById(R.id.new_reminder_place_layout);

        //set timepick 24 mode
        timePicker.setIs24HourView(true);
        //setup the error_text style
        mainTextLayout.setErrorEnabled(true);
        mainTextLayout.setErrorTextAppearance(R.style.errorTextAppearance);

        //  Data of reminder lists

        arrayList.add( new OTRemDataObject("6 30"," ","get up ","3.2"));



        // configure the recyclerView
        recyclerView.setHasFixedSize(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext() , 1 , GridLayout.VERTICAL , false );
        recyclerView.setLayoutManager(gridLayoutManager);
        int marbot = getApplicationContext().getResources().getDimensionPixelSize(R.dimen.reminder_itemdeco_margin_bot );
        int top = marbot/3 ;
        recyclerView.addItemDecoration(new reminderViewItemdecoration(marbot,top)); // add space among  items
        OTRemAdapter adapter = new OTRemAdapter(arrayList);
        recyclerView.setAdapter(adapter);

        /*
         * 添加list item滑动删除操作
         */
        ItemTouchHelper helper = new ItemTouchHelper(new itemtouchcallback(adapter));
        helper.attachToRecyclerView(recyclerView);

        // configure the maigc_tab button to implement the "return back" function by singleTap and the "create new reminder"function by doubleTap
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
        magicTab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return magicTabDetector.onTouchEvent(event);
            }
        });

        // two fundamental interactive button for cancel and add data #Cancel , #Done
        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : except setting UI visibility , updating datas are indeed needed yet


                //get place Text String
                String tempplace = placeText.getText().toString();
                if(tempplace==null || tempplace==" "||tempplace==""||tempplace=="  "){
                    placeTextLayout.getEditText().getText().clear();
                }else if(tempplace.length()>1){
                    tempplace = getResources().getString(R.string.textinput_address) + tempplace ;
                }

                String getinputtext = mainText.getText().toString();
                //first ,we checkout if users have texted nothing ,and on that situation ,we won't save anything
                if (getinputtext == "" || getinputtext == null || getinputtext == " "|| mainText.getText().length()<1) {
                    mainTextLayout.setError("invalide text , information cannot be null ");
                    mainText.getText().clear();
                    getinputtext = null;
                } else {
                    //get mainText String
                    String tempMainText = mainText.getText().toString();


                    //if the code gets here ,means valid information was received,then we add this to the JSON file and update view
                    // before we go further step , we need to check if user choosed time
                    //long press to set date

                    timePicker.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            DatePickerDialog datepicker = new DatePickerDialog(getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    iyear = year;
                                    imonth = month;
                                    iday = dayOfMonth;
                                }
                            } ,iyear,imonth,iday);
                            datepicker.getDatePicker().setSpinnersShown(true);
                            datepicker.show();
                            return true;
                        }
                    });

                    timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
                        @Override
                        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                            hourofday = hourOfDay;
                            mminute = minute;
                            timeChoosed = true;
                        }
                    });
                    // if timeChoosed equals [ true ], get time String
                    if (timeChoosed) {
                        String tempChoosedTime = String.valueOf(hourofday) + " : " + String.valueOf(mminute);
                        String tempChoosedDate = String.valueOf(iyear)+"."+String.valueOf(imonth)+"."+String.valueOf(iday);
                        OTRemDataObject tempDataObj = new OTRemDataObject(tempChoosedTime, tempplace, tempMainText ,tempChoosedDate);
                        adapter.addItem(tempDataObj);
                    }else {
                        adapter.addItem(new OTRemDataObject("",tempplace,tempMainText,""));
                    }

                    newreminderContainer.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: response to Users' cancel request , clear inputlayout if texts existed already,reset them
                mainText.getText().clear();
                placeText.getText().clear();
                mainTextLayout.setErrorEnabled(false);

                newreminderContainer.setVisibility( View.GONE );
                recyclerView.setVisibility( View.VISIBLE );
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


        // set animation for the magic_tab
        float magicTabHeight = getResources().getDimensionPixelSize(R.dimen.magic_tab_height );
        ImageButton imagicButton = (ImageButton)findViewById(R.id.magic_tab );

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


