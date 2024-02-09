package com.fitness_tracking.Dao;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fitness_tracking.R;
import com.fitness_tracking.auth.LoginActivity;
import com.fitness_tracking.auth.ProductActivity;
import com.fitness_tracking.entities.Produit;

public class ProductAdapter extends ArrayAdapter<Produit> {

    private final Context activityContext;
    private final ProductActivity productActivity=new ProductActivity();

    public ProductAdapter(@NonNull Context context, Context activityContext, List<Produit> dataArrayList) {
        super(context, R.layout.card_item, dataArrayList);
        this.activityContext = activityContext;
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

        if (listData != null) {
            listName.setText(listData.getName());
            listCal.setText(String.valueOf(listData.getCalorie()));
        }

        ImageButton btnDelete = view.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Produit productToDelete = getItem(position);

                if (productToDelete != null) {
                    deleteProduct(productToDelete.getId());
                }
            }
        });

        ImageButton btnEdit = view.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the product at the clicked position
                Produit productToEdit = getItem(position);

                if (productToEdit != null) {
                    productActivity.showAddProductDialog("Edit Product",activityContext,productToEdit);
                }
            }
        });

        return view;
    }

    private void deleteProduct(long productId) {
        DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
        databaseHandler.deleteProduit(productId);

    }

}