package com.fitness_tracking.Dao;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fitness_tracking.R;
import com.fitness_tracking.entities.Produit;

public class ProductAdapter extends ArrayAdapter<Produit> {
    public ProductAdapter(@NonNull Context context, List<Produit> dataArrayList) {
        super(context, R.layout.card_item, dataArrayList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Produit listData = getItem(position);
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);
        }


        TextView listName = view.findViewById(R.id.listName);
        TextView listCal = view.findViewById(R.id.listCal);

        // Set data to the views
        if (listData != null) {
            listName.setText(listData.getName()); // Set product name
            listCal.setText(String.valueOf(listData.getCalorie())); // Set product calories
        }

        return view;
    }
}