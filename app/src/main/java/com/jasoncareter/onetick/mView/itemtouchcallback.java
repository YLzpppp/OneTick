package com.jasoncareter.onetick.mView;

import android.content.ClipData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.jasoncareter.onetick.mModel.OTRemAdapter;

public class itemtouchcallback extends ItemTouchHelper.Callback {

    private OTRemAdapter madapter;

    public itemtouchcallback(OTRemAdapter madapter){
        this.madapter = madapter ;
    }
    // setup and return the item's direction that being dragged or swiped to
    @Override
    public int getMovementFlags( RecyclerView recyclerView,  RecyclerView.ViewHolder viewHolder) {
        int dragflag = ItemTouchHelper.DOWN | ItemTouchHelper.UP ;
        int swipeflag = ItemTouchHelper.START ;
        return makeMovementFlags( dragflag ,swipeflag ) ;
    }

    @Override
    public boolean onMove( RecyclerView recyclerView,  RecyclerView.ViewHolder viewHolder,  RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped( RecyclerView.ViewHolder viewHolder, int i) {
        madapter.deleteItem(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }
}
