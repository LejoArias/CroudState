package com.example.croudstate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

     private EditText mail;
    private EditText pass;
    private Button login;
    private Button registrarse;

    //DATOS INGRESADOS
    private String email;
    private String password;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        mail = (EditText)findViewById(R.id.email_log_et);
        pass = (EditText)findViewById(R.id.pass_reg_et);
        login = (Button)findViewById(R.id.btn_login);
        registrarse  = (Button)findViewById(R.id.btn_registrarse);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = mail.getText().toString();
                password = pass.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()){
                       loginUser();
                }
                else{
                    Toast.makeText(Login.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
            }
        });

    }

    private void loginUser(){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(Login.this, profile.class));
                    finish();
                }
                else{
                    Toast.makeText(Login.this, "No se ha podido iniciar la sesion",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}