package com.jasoncareter.onetick.mView;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class accountViewItemdecoration extends RecyclerView.ItemDecoration {

    private int bottom ;

    public accountViewItemdecoration(int bottom) {
        this.bottom = bottom ;
    }

    @Override
    public void getItemOffsets( Rect outRect,  View view,  RecyclerView parent,  RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = bottom ;
    }
}
