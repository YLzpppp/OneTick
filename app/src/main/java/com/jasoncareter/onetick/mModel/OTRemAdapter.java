package com.jasoncareter.onetick.mModel;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jasoncareter.onetick.R;
import com.jasoncareter.onetick.mView.reminderViewHolder;

import java.util.ArrayList;

public class OTRemAdapter extends RecyclerView.Adapter<reminderViewHolder> {

    private ArrayList<OTRemDataObject> dataset ;

    public OTRemAdapter(ArrayList<OTRemDataObject> dataset ){
        this.dataset = dataset ;
    }
    @Override
    public reminderViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View  view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reminder_item_view , viewGroup ,false );
        return new reminderViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder( reminderViewHolder viewHolder, int position) {
        OTRemDataObject currentdata = dataset.get(position);
        viewHolder.maintext.setText( currentdata.getContent());
        viewHolder.timetext.setText(currentdata.getTime());
    }

    @Override
    public int getItemCount() {  return dataset.size() ; }

}
