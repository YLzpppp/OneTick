package com.jasoncareter.onetick.mView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jasoncareter.onetick.R;

public class reminderViewItemdecoration extends RecyclerView.ItemDecoration {

    private int marginbot  ;
    private int margintop ;

    public reminderViewItemdecoration (int marginbot , int margintop ){
        this.marginbot = marginbot ;
        this.margintop = margintop ;
    }

    @Override
    public void getItemOffsets( Rect outRect,  View view,  RecyclerView parent,  RecyclerView.State state) {
        outRect.top = margintop;
        outRect.bottom = marginbot ;
    }

}
