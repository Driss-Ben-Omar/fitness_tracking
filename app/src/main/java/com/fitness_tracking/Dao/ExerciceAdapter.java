package com.fitness_tracking.Dao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fitness_tracking.R;
import com.fitness_tracking.pages.ExerciceActivity;
import com.fitness_tracking.auth.SessionManager;
import com.fitness_tracking.entities.Exercice;

import java.util.List;

public class ExerciceAdapter extends ArrayAdapter<Exercice> {

    private final Context activityContext;
    private final ExerciceActivity exerciceActivity = new ExerciceActivity();

    public ExerciceAdapter(@NonNull Context context, Context activityContext, List<Exercice> dataArrayList) {
        super(context, R.layout.card_item_exercice, dataArrayList);
        this.activityContext = activityContext;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Exercice listData = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.card_item_exercice, parent, false);
        }

        TextView exerciceName = view.findViewById(R.id.exerciceName);
        TextView exerciceDescription = view.findViewById(R.id.exerciceDescription);

        if (listData != null) {
            exerciceName.setText(listData.getName());
            exerciceDescription.setText(listData.getDescription());
        }

        ImageButton btnDeleteExercice = view.findViewById(R.id.btnDeleteExercice);
        btnDeleteExercice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exercice exerciceToDelete = getItem(position);

                if (exerciceToDelete != null) {
                    deleteExercice(exerciceToDelete.getId());
                }
            }
        });

        ImageButton btnEditExercice = view.findViewById(R.id.btnEditExercice);
        btnEditExercice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exercice exerciceToEdit = getItem(position);

                if (exerciceToEdit != null) {
                    exerciceActivity.showAddExerciceDialog("Edit Exercise", activityContext, exerciceToEdit);
                }
            }
        });

        return view;
    }

    private void deleteExercice(long exerciceId) {
        DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
        databaseHandler.deleteExercice(exerciceId);

        Long id = SessionManager.getInstance().getCurrentUser().getId();

        this.clear();
        this.addAll(databaseHandler.getAllExercicesForUser(id));
        this.notifyDataSetChanged();
    }

}
