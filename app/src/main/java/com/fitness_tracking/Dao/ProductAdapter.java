package com.fitness_tracking.Dao;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fitness_tracking.R;
import com.fitness_tracking.pages.ProductActivity;
import com.fitness_tracking.auth.SessionManager;
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
        TextView listProtein = view.findViewById(R.id.listProtein);
        TextView listCarbs = view.findViewById(R.id.listCarbs);
        TextView listFat = view.findViewById(R.id.listFat);

        if (listData != null) {
            listName.setText(listData.getName());
            listCal.setText(String.valueOf(listData.getCalorie())+" Cal");
            listProtein.setText(String.valueOf(listData.getProteine()));
            listCarbs.setText(String.valueOf(listData.getCarbe()));
            listFat.setText(String.valueOf(listData.getFate()));
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

        Long id = SessionManager.getInstance().getCurrentUser().getId();

        this.clear();
        this.addAll(databaseHandler.getAllProduitsForUser(id));
        this.notifyDataSetChanged();

    }

    private void updateProductInDatabase(Long productId, String productName, Double calorie, Double protein, Double carbs, Double fats) {
        DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
        Long id = SessionManager.getInstance().getCurrentUser().getId();
        Produit produit=new Produit(productId,productName,calorie,protein,carbs,fats,id);
        databaseHandler.updateProduit(produit);

    }

}