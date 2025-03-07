package com.fitness_tracking.auth;
import com.fitness_tracking.Dao.DatabaseHandler;
import com.fitness_tracking.MainActivity;
import com.fitness_tracking.R;
import com.fitness_tracking.entities.User;
import com.fitness_tracking.pages.ExerciceActivity;
import com.fitness_tracking.pages.ProductActivity;
import com.fitness_tracking.pages.WorkoutActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Optional;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    DatabaseHandler DB;
    Button btnlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=(EditText) findViewById(R.id.editEmail);
        password=(EditText) findViewById(R.id.editPassword);
        btnlogin=(Button) findViewById(R.id.LoginButton);
        DB = new DatabaseHandler(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String emailValue = email.getText().toString();
                    String pass = password.getText().toString();

                    if( email.equals("") || pass.equals("") ){
                        Toast.makeText(LoginActivity.this, "Veuillez saisir toutes les informations.", Toast.LENGTH_SHORT).show();
                    }else{
                        try {
                            if (DB.checkUserCredentials(emailValue, pass)) {
                                Optional<User> userOptional = DB.getUserByEmailAndPassword(emailValue, pass);
                                if (userOptional.isPresent()) {

                                    User user = userOptional.get();
                                    // Store the logged-in user in the session
                                    SessionManager.getInstance().loginUser(user);
                                    // Now we have the logged user data
                                    Toast.makeText(LoginActivity.this, "Connexion réussie.", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), WorkoutActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(LoginActivity.this, "Connexion échoué.", Toast.LENGTH_SHORT).show();
                                }
                            } else{
                                Toast.makeText(LoginActivity.this, "Identifiants invalides.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        );

    }

    public void viewRegisterClicked(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}
