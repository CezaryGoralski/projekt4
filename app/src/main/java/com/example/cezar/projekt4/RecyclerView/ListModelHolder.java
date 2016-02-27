package com.example.cezar.projekt4.RecyclerView;

/**
 * Created by Marcin on 19.01.2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.cezar.projekt4.R;

public class ListModelHolder extends RecyclerView.ViewHolder {

    public TextView nameTextView;
    public TextView descriptionTextView;
    public TextView numberTextView;

    public ListModelHolder(View itemView) {
        super(itemView);

        nameTextView = (TextView) itemView.findViewById(R.id.list_title);
        descriptionTextView = (TextView) itemView.findViewById(R.id.list_description);
        numberTextView = (TextView) itemView.findViewById(R.id.list_item_number);
    }

}
