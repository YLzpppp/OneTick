package com.jasoncareter.onetick.mView;

import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.jasoncareter.onetick.mModel.OTAccAdapter;

public class accitemtouchcallback extends ItemTouchHelper.Callback {

    private OTAccAdapter madapter;

    public accitemtouchcallback(OTAccAdapter madapter){
        this.madapter = madapter ;
    }
    @Override
    public int getMovementFlags( RecyclerView recyclerView,  RecyclerView.ViewHolder viewHolder) {
        int drag = ItemTouchHelper.UP | ItemTouchHelper.DOWN ;
        int swip = ItemTouchHelper.LEFT;
        return makeMovementFlags(drag, swip);
    }

    @Override
    public boolean onMove( RecyclerView recyclerView,  RecyclerView.ViewHolder viewHolder,  RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped( RecyclerView.ViewHolder viewHolder, int i) {
        madapter.deleteItem(i);
    }
}
