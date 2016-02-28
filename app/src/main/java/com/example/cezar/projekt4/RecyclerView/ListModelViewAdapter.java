package com.example.cezar.projekt4.RecyclerView;

/**
 * Created by Marcin on 19.01.2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cezar.projekt4.Model.NetworkModels.Paths;
import com.example.cezar.projekt4.R;

import java.util.ArrayList;

public class ListModelViewAdapter extends RecyclerView.Adapter<ListModelHolder> {

    private final ArrayList<Paths> itemsList;

    public ListModelViewAdapter(ArrayList<Paths> itemsList) {
        this.itemsList = itemsList;
    }


    @Override
    public ListModelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View view = inflater.inflate(R.layout.list_item, null);
        return new ListModelHolder(view);
    }

    @Override
    public void onBindViewHolder(ListModelHolder holder, final int position) {
        final Paths item = itemsList.get(position);

        holder.nameTextView.setText(item.getName());

        String flex;

        switch (item.getPlaces().size()) {
            case 1:
                flex = "miejsce";
                break;
            case 2:
            case 3:
            case 4:
                flex = "miejsca";
                break;
            default:
                flex = "miejsc";
                break;
        }

        holder.numberTextView.setText(item.getPlaces().size() + " " + flex);
    }

    @Override
    public int getItemCount() {
        if (itemsList == null) {
            return 0;
        } else {
            return itemsList.size();
        }
    }

}

