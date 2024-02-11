package com.fitness_tracking.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.fitness_tracking.Dao.DatabaseHandler;
import com.fitness_tracking.Dao.ExerciceAdapter;
import com.fitness_tracking.R;
import com.fitness_tracking.auth.LoginActivity;
import com.fitness_tracking.auth.Register;
import com.fitness_tracking.auth.SessionManager;
import com.fitness_tracking.entities.Exercice;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class ExerciceActivity extends AppCompatActivity {
    ExerciceAdapter listAdapter;
    List<Exercice> dataArrayList = new ArrayList<>();
    DatabaseHandler databaseHandler = new DatabaseHandler(this);
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercice);

        ListView listView = findViewById(R.id.listviewExercice);

        Long id = SessionManager.getInstance().getCurrentUser().getId();

        dataArrayList = databaseHandler.getAllExercicesForUser(id);

        listAdapter = new ExerciceAdapter(getApplicationContext(), this, dataArrayList);

        listView.setAdapter(listAdapter);

        Button btnAddExercice = findViewById(R.id.btnAddExercice);

        btnAddExercice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddExerciceDialog("Add Exercise", ExerciceActivity.this, null);
            }
        });
        bottomNavigationView= findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.workout);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();

                if(id==R.id.person) {
                    Toast.makeText(ExerciceActivity.this, "person", Toast.LENGTH_SHORT).show();

                    return true;

                }else
                if(id==R.id.home) {
                    Intent intent4 = new Intent(ExerciceActivity.this, WorkoutActivity.class);
                    startActivity(intent4);
                    return true;

                }else if(id==R.id.fitness) {
                    Intent intent4 = new Intent(ExerciceActivity.this, ProductActivity.class);
                    startActivity(intent4);
                    return true;

                }else if(id== R.id.workout){
                    Toast.makeText(ExerciceActivity.this, "workout.", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }

    public void showAddExerciceDialog(String title, Context context, Exercice exerciceToEdit) {
        listAdapter = new ExerciceAdapter(context, context, dataArrayList);
        databaseHandler = new DatabaseHandler(context);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_exercice, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextExerciceName = dialogView.findViewById(R.id.editTextExerciceName);
        final EditText editTextExercicePath = dialogView.findViewById(R.id.editTextExercicePath);
        final EditText editTextExerciceDescription = dialogView.findViewById(R.id.editTextExerciceDescription);
        TextView titlePage = dialogView.findViewById(R.id.titleAddExercice);
        titlePage.setText(title);
        if (exerciceToEdit != null) {
            editTextExerciceName.setText(exerciceToEdit.getName());
            editTextExercicePath.setText(exerciceToEdit.getPath());
            editTextExerciceDescription.setText(exerciceToEdit.getDescription());
        }

        Button btnCancel = dialogView.findViewById(R.id.btnCancelExercice);
        Button btnOK = dialogView.findViewById(R.id.btnOKExercice);

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
                String exerciceName = editTextExerciceName.getText().toString();
                String exercicePath = editTextExercicePath.getText().toString();
                String exerciceDescription = editTextExerciceDescription.getText().toString();

                if (exerciceToEdit == null) {
                    Long idddd=saveExerciceToDatabase(exerciceName, exercicePath, exerciceDescription);
                    Toast.makeText(context, "id saved"+idddd, Toast.LENGTH_SHORT).show();
                } else {
                    updateExerciceInDatabase(exerciceToEdit.getId(), exerciceName, exercicePath, exerciceDescription);
                }

                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private Long saveExerciceToDatabase(String exerciceName, String exercicePath, String exerciceDescription) {
        Long id = SessionManager.getInstance().getCurrentUser().getId();
        Exercice exercice = new Exercice(null, exerciceName, exercicePath, exerciceDescription, id);
        Long saved = databaseHandler.addExercice(exercice);
        if (saved != -1) {
            dataArrayList.clear();
            dataArrayList.addAll(databaseHandler.getAllExercicesForUser(id));
            listAdapter.notifyDataSetChanged();
        }
        return saved;
    }

    private void updateExerciceInDatabase(Long exerciceId, String exerciceName, String exercicePath, String exerciceDescription) {
        Long id = SessionManager.getInstance().getCurrentUser().getId();
        Exercice exercice = new Exercice(exerciceId, exerciceName, exercicePath, exerciceDescription, id);
        databaseHandler.updateExercice(exercice);
    }
}
