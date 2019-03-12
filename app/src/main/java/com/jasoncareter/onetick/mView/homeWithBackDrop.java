package com.jasoncareter.onetick.mView;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.jasoncareter.onetick.R;
import com.jasoncareter.onetick.mModel.OTRemData;
import com.jasoncareter.onetick.mModel.OTRemDataObject;
import com.jasoncareter.onetick.mPresenter.MainAccActivity;
import com.jasoncareter.onetick.mPresenter.MainActivity;
import com.jasoncareter.onetick.mPresenter.reminderList;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.util.Calendar;

public class homeWithBackDrop extends Fragment {

    private Button button_enter_rem ;
    private Button button_enter_day ;
    private Button button_enter_acc ;
    private int Hour;
    private int Minute;
    private Calendar c;
    private String pstr = "";
    private JSONObject wholeobj ;
    private boolean timebeenset = false;

    OTRemData jsondata ;

    private static final String TAG = homeWithBackDrop.class.getName() ;

    public homeWithBackDrop() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_with_back_drop, container, false);
        // call the private method  setToolBar to init the ToolBar defined in the AppBarLayout of {@link_fragment_home_with_backdrop }
        setToolBar(view);

        //TODO: response to the click event triggered by EnterReminderPage Button
        button_enter_rem = (Button)view.findViewById(R.id.enter_reminder_pager ) ;
        button_enter_rem.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity() , reminderList.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.reminderlist_show , R.anim.mainactivity_exit);
            }
        });

        //TODO: response to the click event triggered by EnterAccountPage Button
        button_enter_acc = (Button)view.findViewById(R.id.enter_accounts_pager );
        button_enter_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity() , MainAccActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.reminderlist_show , R.anim.mainactivity_exit);
            }
        });

        //TODO: response to the click event triggered by EnterDayPage Button
        button_enter_day = (Button)view.findViewById(R.id.enter_day_pager );
        button_enter_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+"在主界面点击了进入 day pager的按钮");
                Activity activity = getActivity() ;
                ((MainActivity)activity).navigateToFrag(new BlankFragment(),true);
            }
        });


        TextView mainPageText = view.findViewById(R.id.mainpagemaintext );
        TextInputLayout mainTextLayout = view.findViewById(R.id.mainpageinputlayout);
        TextInputEditText mainTextEdit = view.findViewById(R.id.mainpageinputtext );
        ImageView mainPageEdit = view.findViewById(R.id.home_card_edit );
        TextView mainPageTime = view.findViewById(R.id.mainpagetime );
        TextInputEditText mainPagePlace = view.findViewById(R.id.mainpageplaceedit );

        //set up the main text tap events
        mainPageText.setClickable(true);
        mainPageText.setFocusableInTouchMode(true);
        GestureDetector mainTextDoubleTapEvent = new GestureDetector( getActivity(), new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                mainPageText.setVisibility(View.GONE);
                mainTextLayout.setVisibility(View.VISIBLE );
                mainTextLayout.setHint("Enter texts here" );
                return true;
            }
        });
        mainPageText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mainTextDoubleTapEvent.onTouchEvent(event);
            }
        });

        /*
         * single tap to popup time picker dialog
         */
        mainPageTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();
                Hour =  c.HOUR_OF_DAY;
                Minute = c.MINUTE;
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timebeenset = true;
                        Hour = hourOfDay;
                        Minute = minute;
                    }
                } , Hour,Minute, true);

            }
        });

        /*
         * quick done button ,when the input action finished
         */
        mainPageEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String input_str = mainTextEdit.getText().toString();
                if( input_str == null || input_str=="" || input_str == " "){
                    mainTextLayout.setErrorEnabled(true);
                    mainTextLayout.setError("invalide input , try it again ");
                }else{
                    //不判断，直接获取地址信息
                    mainPagePlace.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            pstr = mainPagePlace.getText().toString();
                        }
                        @Override
                        public void afterTextChanged(Editable s) { }
                    });

                    //输入的信息有效，添加进JsonFile中，并在reminder  list 中更新此条信息
                    jsondata = new OTRemData(getContext());
                    String wholeobjs = jsondata.readJsonFile();
                    try {
                         wholeobj = new JSONObject(wholeobjs);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONObject newobj ;
                    //判断是否设置时间
                    String time;
                    if (timebeenset){
                        //则用户选择了时间
                        time = String.valueOf(Hour)+" : "+String.valueOf(Minute);
                        newobj = jsondata.getNewJsonObj(time, pstr , input_str);
                    }else {
                        time = "";
                        newobj = jsondata.getNewJsonObj(time, pstr ,input_str);
                    }
                    // 完成jsonfile的更新
                    jsondata.writeObj(jsondata.getFlagRec(), newobj , wholeobj);
                    //TODO : 更新reminder list 中的项目。 如果json file中没有内容则显示提示信息，否则显示json file 中最近一项
                    reminderList rl = new reminderList();
                    rl.arrayList.add(new OTRemDataObject(time,pstr,input_str,""));
                    mainPageTime.setText(time);
                }
            }
        });

        return view ;
    }

    private void setToolBar(View v){
        Toolbar toolbar = (Toolbar)v.findViewById(R.id.onetick_toolbar);
        AppCompatActivity activity =(AppCompatActivity) getActivity();
        if(activity != null ){
            activity.setSupportActionBar(toolbar);
        }
        //TODO: implement toolbar. setNavigationOnClickListener to adjust the view
        toolbar.setNavigationOnClickListener( new NavigationIconListener(getContext() ,v.findViewById(R.id.home_card_container ) ,
                new AccelerateDecelerateInterpolator(),getContext().getResources().getDrawable(R.drawable.navigation_icon),getContext().getResources().getDrawable(R.drawable.navigation_down)));
    }

}
