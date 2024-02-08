package com.fitness_tracking.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

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

        listAdapter = new ProductAdapter(getApplicationContext(), dataArrayList);

        listView.setAdapter(listAdapter);
    }
}
