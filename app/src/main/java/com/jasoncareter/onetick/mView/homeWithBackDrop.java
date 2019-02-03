package com.jasoncareter.onetick.mView;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jasoncareter.onetick.R;

public class homeWithBackDrop extends Fragment {

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


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
}
