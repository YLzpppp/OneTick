package com.jasoncareter.onetick.mModel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jasoncareter.onetick.R;
import com.jasoncareter.onetick.mView.accountViewHolder;

import java.util.ArrayList;

public class OTAccAdapter extends RecyclerView.Adapter<accountViewHolder> {

    private ArrayList<OTAccDataObject> dataset ;

    public OTAccAdapter(ArrayList<OTAccDataObject> dataset){
        this.dataset = dataset ;
    }

    @Override
    public accountViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.account_item_view ,viewGroup ,false );
        return new accountViewHolder(view);
    }

    @Override
    public void onBindViewHolder( accountViewHolder accountViewHolder, int i) {
        OTAccDataObject currentData = dataset.get(i);
        accountViewHolder.accPlatform.setText(currentData.getPlatform());
        accountViewHolder.accAccount.setText(currentData.getAccount());
        accountViewHolder.accPassword.setText(currentData.getPassword());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void deleteItem(int position){
        dataset.remove(position);
        notifyItemRemoved(position);
    }

}
