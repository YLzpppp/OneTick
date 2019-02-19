package com.jasoncareter.onetick.mView;

import android.content.ClipData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class itemtouchcallback extends ItemTouchHelper.Callback {
    @Override
    public int getMovementFlags( RecyclerView recyclerView,  RecyclerView.ViewHolder viewHolder) {
        int dragflag = ItemTouchHelper.DOWN | ItemTouchHelper.UP ;
        int swipeflag = ItemTouchHelper.END |ItemTouchHelper.START ;
        return makeMovementFlags( dragflag ,swipeflag ) ;
    }

    @Override
    public boolean onMove( RecyclerView recyclerView,  RecyclerView.ViewHolder viewHolder,  RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped( RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public boolean isLongPressDragEnabled() {
        return super.isLongPressDragEnabled();
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return super.isItemViewSwipeEnabled();
    }
}
