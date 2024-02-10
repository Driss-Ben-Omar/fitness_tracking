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
import com.fitness_tracking.Dao.WorkoutAdapter;
import com.fitness_tracking.R;
import com.fitness_tracking.auth.LoginActivity;
import com.fitness_tracking.auth.Register;
import com.fitness_tracking.auth.SessionManager;
import com.fitness_tracking.entities.Produit;
import com.fitness_tracking.entities.Workout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkoutActivity extends AppCompatActivity {
    WorkoutAdapter listAdapter;
    List<Workout> dataArrayList = new ArrayList<>();
    DatabaseHandler databaseHandler = new DatabaseHandler(this);
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


        ListView listView = findViewById(R.id.workoutListView);

        Long id = SessionManager.getInstance().getCurrentUser().getId();

        dataArrayList = databaseHandler.getAllWorkoutsForUser(id);

        listAdapter = new WorkoutAdapter(getApplicationContext(), this, dataArrayList);
        int count=databaseHandler.getAllWorkoutsForUser(id).size();

        listView.setAdapter(listAdapter);

        Button btnAddWorkout = findViewById(R.id.btnAddWorkout);

        btnAddWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddWorkoutDialog("Add Workout", WorkoutActivity.this, null);
            }
        });

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.person) {
                    Toast.makeText(WorkoutActivity.this, "person", Toast.LENGTH_SHORT).show();
                    return true;

                } else if (id == R.id.home) {
                    Toast.makeText(WorkoutActivity.this, "home", Toast.LENGTH_SHORT).show();
                    return true;

                } else if (id == R.id.fitness) {
                    Intent intent4 = new Intent(WorkoutActivity.this, ProductActivity.class);
                    startActivity(intent4);
                    return true;

                } else if (id == R.id.workout) {
                    Intent intent4 = new Intent(WorkoutActivity.this, ExerciceActivity.class);
                    startActivity(intent4);
                    return true;
                }
                return false;
            }
        });
    }

    public void showAddWorkoutDialog(String title, Context context, Workout workoutToEdit) {
        listAdapter = new WorkoutAdapter(context, context, dataArrayList);
        databaseHandler = new DatabaseHandler(context);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_workout, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextWeight = dialogView.findViewById(R.id.editTextWeightWorkout);
        final EditText editTextRepetition = dialogView.findViewById(R.id.editTextRepetition);
        final EditText editTextSerie = dialogView.findViewById(R.id.editTextSerieWorkout);

        TextView titlePage = dialogView.findViewById(R.id.titleAddWorkout);
        titlePage.setText(title);

        if(workoutToEdit!=null){
            editTextWeight.setText(String.valueOf(workoutToEdit.getWeight()));
            editTextRepetition.setText(String.valueOf(workoutToEdit.getRepetition()));
            editTextSerie.setText(String.valueOf(workoutToEdit.getSerie()));

        }

        Button btnCancel = dialogView.findViewById(R.id.btnCancelWorkout);
        Button btnOK = dialogView.findViewById(R.id.btnOKWorkout);

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
                int workoutSerie =Integer.parseInt(editTextSerie.getText().toString());
                int workoutRepetition =Integer.parseInt(editTextRepetition.getText().toString());
                double workoutweight =Double.parseDouble(editTextWeight.getText().toString());

                if(workoutToEdit==null){
                    saveWorkoutDatabase(workoutweight,workoutRepetition,workoutSerie);
                }else{

                    updateWorkoutInDatabase(workoutToEdit.getId(),workoutweight,workoutRepetition,workoutSerie,context);
                }

                alertDialog.dismiss();
            }
        });


        alertDialog.show();
    }

    private void saveWorkoutDatabase(double wei, int rep,int serie) {
        Long id = SessionManager.getInstance().getCurrentUser().getId();
        Workout workout=new Workout(null,null,wei,serie,rep,new Date(),id);
        Long saved=databaseHandler.addWorkout(workout);
        if (saved != -1) {
            dataArrayList.clear();
            dataArrayList.addAll(databaseHandler.getAllWorkoutsForUser(id));
            listAdapter.notifyDataSetChanged();
        }
    }

    private void updateWorkoutInDatabase(Long workoutId, double weight, int repetition, int serie,Context context) {
        Long id = SessionManager.getInstance().getCurrentUser().getId();
        Workout workout=new Workout(workoutId,null,weight,serie,repetition,new Date(),id);
        databaseHandler.updateWorkout(workout);

    }
}
