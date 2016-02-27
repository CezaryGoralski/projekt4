package com.example.cezar.projekt4.RecyclerView;

/**
 * Created by Marcin on 27.02.2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cezar.projekt4.R;

public class PlaceModelHolder extends RecyclerView.ViewHolder {

    public TextView nameTextView;
    public TextView addressTextView;
    public TextView tagTextView;

    public ImageView iconView;

    public PlaceModelHolder(View itemView) {
        super(itemView);

        nameTextView = (TextView) itemView.findViewById(R.id.place_title);
        addressTextView = (TextView) itemView.findViewById(R.id.place_address);
        tagTextView = (TextView) itemView.findViewById(R.id.place_type);

        iconView = (ImageView) itemView.findViewById(R.id.place_image);
    }

}
