package com.jasoncareter.onetick.mView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jasoncareter.onetick.R;

public class reminderViewHolder extends RecyclerView.ViewHolder {
    public TextView maintext ;
    public TextView timetext ;
    public TextView datetext;
    public reminderViewHolder( View itemView) {
        super(itemView);
        maintext = itemView.findViewById(R.id.reminder_item_maintext );
        timetext = itemView.findViewById(R.id.reminder_item_time );
        datetext = itemView.findViewById(R.id.reminder_item_day );
    }
}
