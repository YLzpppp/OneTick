package com.jasoncareter.onetick.mView;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.jasoncareter.onetick.R;
import com.jasoncareter.onetick.mPresenter.MainActivity;

public class homeWithBackDrop extends Fragment {
    private String value ;

    public homeWithBackDrop() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        String value =((MainActivity)context).AcommunicateF() ;
        this.value = value ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_home_with_back_drop, container, false);
        // call the private method  setToolBar to init the ToolBar defined in the AppBarLayout of {@link_fragment_home_with_backdrop }
        setToolBar(view);

        //TODO: Get device metrics
        TextView textView = (TextView)view.findViewById(R.id.t1);
        textView.setText(value);

        return view ;
    }

    private void setToolBar(View v){
        Toolbar toolbar = (Toolbar)v.findViewById(R.id.onetick_toolbar);
        AppCompatActivity activity =(AppCompatActivity) getActivity();
        if(activity != null ){
            activity.setSupportActionBar(toolbar);
        }
        //TODO: implement toolbar. setNavigationOnClickListener to adjust the view
    }


}
