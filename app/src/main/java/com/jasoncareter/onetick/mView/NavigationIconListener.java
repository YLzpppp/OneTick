package com.jasoncareter.onetick.mView;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Interpolator;

public class NavigationIconListener implements View.OnClickListener {
    private Context context ;
    private View view ;
    private Interpolator interpolator ;
    private Drawable icon_while_closed;
    private Drawable icon_while_opened;
    // get params
    public NavigationIconListener(Context context , View view , @NonNull Interpolator interpolator, @NonNull Drawable icon_while_closed , @NonNull Drawable icon_while_opened){

    }

    @Override
    public void onClick(View v) {
        //TODO: Handle the icon click events here
    }
}
