package com.fitness_tracking.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.fitness_tracking.Dao.DatabaseHandler;
import com.fitness_tracking.Dao.ProductAdapter;
import com.fitness_tracking.R;
import com.fitness_tracking.entities.Produit;
import com.fitness_tracking.pages.Home;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    ProductAdapter listAdapter;
    List<Produit> dataArrayList = new ArrayList<>();
    DatabaseHandler databaseHandler=new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produit);

        ListView listView = findViewById(R.id.listview);

        Long id = SessionManager.getInstance().getCurrentUser().getId();


        dataArrayList = databaseHandler.getAllProduitsForUser(id);

        listAdapter = new ProductAdapter(getApplicationContext(), this, dataArrayList);

        listView.setAdapter(listAdapter);

        Button btnAddProduct = findViewById(R.id.btnAddPr);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddProductDialog("Add Prduct",ProductActivity.this,null);
            }
        });

    }

    public void showAddProductDialog(String title, Context context, Produit productToEdit) {
        listAdapter = new ProductAdapter(context, context, dataArrayList);
        databaseHandler=new DatabaseHandler(context);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_product, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextProductName = dialogView.findViewById(R.id.editTextProductName);
        final EditText editTextCalorie = dialogView.findViewById(R.id.editTextCalorie);
        final EditText editTextProtein = dialogView.findViewById(R.id.editTextProtein);
        final EditText editTextCarbs = dialogView.findViewById(R.id.editTextCarbs);
        final EditText editTextFats = dialogView.findViewById(R.id.editTextFats);
        TextView titlePage=dialogView.findViewById(R.id.titleAddProduct);
        titlePage.setText(title);
        if(productToEdit!=null){
            editTextProductName.setText(productToEdit.getName());
            editTextCalorie.setText(String.valueOf(productToEdit.getCalorie()));
            editTextProtein.setText(String.valueOf(productToEdit.getProteine()));
            editTextCarbs.setText(String.valueOf(productToEdit.getCarbe()));
            editTextFats.setText(String.valueOf(productToEdit.getFate()));
        }

        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnOK = dialogView.findViewById(R.id.btnOK);

        final AlertDialog alertDialog = dialogBuilder.create();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = editTextProductName.getText().toString();
                Double calorie = Double.parseDouble(editTextCalorie.getText().toString()) ;
                Double protein = Double.parseDouble(editTextProtein.getText().toString());
                Double carbs = Double.parseDouble(editTextCarbs.getText().toString());
                Double fats=Double.parseDouble(editTextFats.getText().toString());
                if(productToEdit==null){
                    saveProductToDatabase(productName, calorie, protein, carbs,fats);
                }else{
                    Toast.makeText(context, "id: "+productToEdit.getId()+" name: "+productName+" cal:"+ calorie
                            +"prot: "+protein+"carbs"+carbs+"fats:"+fats, Toast.LENGTH_LONG).show();
                    updateProductInDatabase(productToEdit.getId(),productName,calorie,protein,carbs,fats);



                }

                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }
    private void saveProductToDatabase(String productName, Double calorie, Double protein, Double carbs,Double fats) {
        Long id = SessionManager.getInstance().getCurrentUser().getId();
        Produit produit=new Produit(null,productName, calorie,protein,carbs,fats,id);
        Long saved=databaseHandler.addProduit(produit);
        if (saved != -1) {
            dataArrayList.clear();
            dataArrayList.addAll(databaseHandler.getAllProduitsForUser(id));
            listAdapter.notifyDataSetChanged();
        }
    }
    private void updateProductInDatabase(Long productId, String productName, Double calorie, Double protein, Double carbs, Double fats) {
        Long id = SessionManager.getInstance().getCurrentUser().getId();
        Produit produit=new Produit(productId,productName,calorie,protein,carbs,fats,id);
        databaseHandler.updateProduit(produit);

        dataArrayList.clear();
        dataArrayList.addAll(databaseHandler.getAllProduitsForUser(id));
        listAdapter.notifyDataSetChanged();
    }
}
