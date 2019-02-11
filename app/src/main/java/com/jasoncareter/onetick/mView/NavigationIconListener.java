package com.jasoncareter.onetick.mView;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Interpolator;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.jasoncareter.onetick.R;
import com.jasoncareter.onetick.mPresenter.MainActivity;

public class NavigationIconListener implements View.OnClickListener {
    private Context context ;
    private View sheet ;
    private Interpolator interpolator ;
    private Drawable icon_while_up;
    private Drawable icon_while_down;
    private boolean downbot = false;
    private int height ;
    private final AnimatorSet animatorSet = new AnimatorSet();

    // get params
    public NavigationIconListener(Context context , View view , @NonNull Interpolator interpolator, @NonNull Drawable icon_while_up , @NonNull Drawable icon_while_down){
        this.context = context ;
        sheet = view ;
        this.interpolator = interpolator ;
        this.icon_while_up = icon_while_up ;
        this.icon_while_down = icon_while_down ;

        DisplayMetrics displayMetrics = new DisplayMetrics() ;
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels ;

    }

    @Override
    public void onClick(View v) {
        //TODO: Handle the icon click events here
        downbot = !downbot;

        animatorSet.removeAllListeners();
        animatorSet.end();
        animatorSet.cancel();
        //modify the icon status before anim starting
        updateIcon(v);

        final int translateY = height - context.getResources().getDimensionPixelSize(R.dimen.backdrop_reveal_height);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(sheet ,"TranslationY" ,downbot?translateY:0);
        objectAnimator.setDuration(400);
        objectAnimator.setInterpolator(interpolator);


        objectAnimator.start();
    }
    private void updateIcon(View view) {
        if (icon_while_up != null && icon_while_down != null) {
            if (!(view instanceof ImageView)) {
                throw new IllegalArgumentException("updateIcon() must be called on an ImageView");
            }
            if (downbot) {
                ((ImageView) view).setImageDrawable(icon_while_down);
            } else {
                ((ImageView) view).setImageDrawable(icon_while_up);
            }
        }
    }


}
