package com.jasoncareter.onetick.mView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;

import com.jasoncareter.onetick.R;
import com.jasoncareter.onetick.mPresenter.FullscreenActivity;
import com.jasoncareter.onetick.mPresenter.MainAccActivity;
import com.jasoncareter.onetick.mPresenter.MainActivity;
import com.jasoncareter.onetick.mPresenter.reminderList;

public class homeWithBackDrop extends Fragment {

    private String value ;
    private Button button_enter_rem ;
    private Button button_enter_day ;
    private Button button_enter_acc ;

    public homeWithBackDrop() {
        // Required empty public constructor
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        String value =((MainActivity)context).AcommunicateF() ;
//        this.value = value ;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home_with_back_drop, container, false);
        // call the private method  setToolBar to init the ToolBar defined in the AppBarLayout of {@link_fragment_home_with_backdrop }
        setToolBar(view);

        //TODO: Get device metrics
//        TextView textView = (TextView)view.findViewById(R.id.t1);
//        textView.setText(value);

        button_enter_rem = (Button)view.findViewById(R.id.enter_reminder_pager ) ;
        button_enter_rem.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity() , reminderList.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.reminderlist_show , R.anim.mainactivity_exit);
            }
        });

        button_enter_day = (Button)view.findViewById(R.id.enter_day_pager );
        button_enter_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity() , FullscreenActivity.class);
                startActivity(i);
            }
        });

        button_enter_acc = (Button)view.findViewById(R.id.enter_accounts_pager );
        button_enter_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity() , MainAccActivity.class);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.reminderlist_show , R.anim.mainactivity_exit);
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
