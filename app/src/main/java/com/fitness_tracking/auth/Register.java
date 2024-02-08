package com.fitness_tracking.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fitness_tracking.Dao.DatabaseHandler;
import com.fitness_tracking.R;
import com.fitness_tracking.entities.User;

public class Register extends AppCompatActivity {

    EditText name,email,password,weight,height;
    RadioGroup sex;
    Button register;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);
        name=(EditText) findViewById(R.id.editTextName);
        email=(EditText) findViewById(R.id.editTextEmail);
        password=(EditText) findViewById(R.id.editTextPassword);
        weight=(EditText) findViewById(R.id.editTextWeight);
        height=(EditText) findViewById(R.id.editTextHeight);
        sex=(RadioGroup) findViewById(R.id.radioGroupSex);
        register=(Button) findViewById(R.id.buttonRegister);
        databaseHandler = new DatabaseHandler(this);

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String nameValue=name.getText().toString();
                String emailValue=email.getText().toString();
                String passwordValue=password.getText().toString();
                Double weightValue=Double.parseDouble(weight.getText().toString());
                Double heightValue=Double.parseDouble(height.getText().toString());
                int sexId=sex.getCheckedRadioButtonId();
                RadioButton sexRadio=findViewById(sexId);
                String sexValue= sexRadio.getText().toString();

                if(nameValue.isEmpty() || emailValue.isEmpty() || passwordValue.isEmpty() ||
                        weightValue.isNaN() || heightValue.isNaN() || sexValue.isEmpty()){
                    Toast.makeText(Register.this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
                }else{
                    if(databaseHandler.checkEmail(emailValue)){
                        User user=new User(null,emailValue,nameValue,passwordValue,weightValue,heightValue,sexValue);
                        boolean userSaved=databaseHandler.saveUser(user);
                        if(userSaved){
                            Toast.makeText(Register.this, "Inscription réussie ! Veuillez vous connecter.", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Register.this, "Erreur lors de l'enregistrement des données.", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Register.this, "Adresse e-mail déjà existante.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
