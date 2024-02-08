package com.fitness_tracking.auth;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.fitness_tracking.Dao.DatabaseHandler;
import com.fitness_tracking.Dao.ProductAdapter;
import com.fitness_tracking.R;
import com.fitness_tracking.entities.Produit;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {
    ProductAdapter listAdapter;
    List<Produit> dataArrayList = new ArrayList<>();
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produit); // Replace 'your_layout_file_name' with the actual XML layout file name

        ListView listView = findViewById(R.id.listview);

        Long id = SessionManager.getInstance().getCurrentUser().getId();
        dataArrayList = databaseHandler.getAllProduitsForUser(id);
        listAdapter = new ProductAdapter(ProductActivity.this, dataArrayList);

        listView.setAdapter(listAdapter);
        listView.setClickable(true);
    }
}
