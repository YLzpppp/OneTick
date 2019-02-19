package com.jasoncareter.onetick.mView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jasoncareter.onetick.R;

public class accountViewHolder extends RecyclerView.ViewHolder {

    public TextView accPlatform ;
    public TextView accAccount ;
    public TextView accPassword ;

    public accountViewHolder(View itemView){
        super(itemView);
        accPlatform = itemView.findViewById(R.id.account_item_platform );
        accAccount = itemView.findViewById(R.id.account_item_account );
        accPassword = itemView.findViewById(R.id.account_item_password );
    }
}
