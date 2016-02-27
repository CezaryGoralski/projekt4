package com.example.cezar.projekt4.RecyclerView;

/**
 * Created by Marcin on 19.01.2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cezar.projekt4.Model.ListModel;
import com.example.cezar.projekt4.R;

import java.util.ArrayList;

public class ListModelViewAdapter extends RecyclerView.Adapter<ListModelHolder> {

    private final ArrayList<ListModel> itemsList;

    public ListModelViewAdapter(ArrayList<ListModel> itemsList) {
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
        final ListModel item = itemsList.get(position);

        holder.nameTextView.setText(item.getName());
        holder.descriptionTextView.setText(item.getDescription());

        String flex;

        switch(item.getNumberOfPlaces()){
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

        holder.numberTextView.setText(item.getNumberOfPlaces() + flex);
        //
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
