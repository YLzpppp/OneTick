package com.jasoncareter.onetick.mView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class followbehavior extends CoordinatorLayout.Behavior<TextView> {
    public followbehavior(Context context , AttributeSet attrs){
        super(context ,attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull TextView child, @NonNull View dependency) {
        return dependency instanceof Button ;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull TextView child, @NonNull View dependency) {
        child.setX(dependency.getX()+430);
        child.setY(dependency.getY()+430);
        return true;
    }
}
