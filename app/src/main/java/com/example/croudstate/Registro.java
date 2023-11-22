package com.example.croudstate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {

    private ImageButton volver;
    private EditText nombre;
    private EditText apellido;
    private EditText identificacion;
    private EditText nacimiento;
    private EditText email;
    private EditText contraseña;
    private EditText verificacionCont;

    private Button registarse;

    //VARIABLES DE LOS DATOS QUE SE VAN A REGISTRAR
    private String name = "";
    private String lastname = "";
    private String id = "";
    private String date = "";
    private String mail = "";
    private String pass = "";
    private String verificataionPass = "";

    FirebaseAuth Auth;
    DatabaseReference dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registro);

        Auth = FirebaseAuth.getInstance();
        dataBase = FirebaseDatabase.getInstance().getReference();

        volver = (ImageButton)findViewById(R.id.btn_volver);
        nombre = (EditText)findViewById(R.id.nombre_et);
        apellido = (EditText)findViewById(R.id.apellido_et);
        identificacion = (EditText)findViewById(R.id.identificacion_et);
        email = (EditText)findViewById(R.id.email_et);
        contraseña = (EditText)findViewById(R.id.contraseña_et);
        verificacionCont = (EditText)findViewById(R.id.verificacionCont_et);
        registarse = (Button)findViewById(R.id.btn_guardar);

        //VOLVER AL LOGIN
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registro.this, Login.class);
                startActivity(intent);
            }
        });


        //REGISTRAR USUARIO
        registarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nombre.getText().toString();
                lastname = apellido.getText().toString();
                id = identificacion.getText().toString();
                mail = email.getText().toString();
                pass = contraseña.getText().toString();
                verificataionPass = verificacionCont.getText().toString();

                if(!name.isEmpty() && !lastname.isEmpty() && !id.isEmpty()  && !mail.isEmpty() && !pass.isEmpty() && !verificataionPass.isEmpty()) {
                    if(pass.length() >= 6 ){
                        registrarUser();
                    }
                    else{
                        Toast.makeText(Registro.this, "La contraseña debe tener al menos 6 caracteres",Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(Registro.this, "Complete todos los campos",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void registrarUser(){
         Auth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                 if(task.isSuccessful()){
                     Map<String, Object> map = new HashMap<>();
                     map.put( "nombre", name);
                     map.put( "apellido", lastname);
                     map.put( "identificacion", id);
                     map.put( "email", mail);
                     map.put( "contraseña", pass);
                     map.put( "verificacion contraseña", verificataionPass);

                     String id = Auth.getCurrentUser().getUid();

                     dataBase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task2) {
                                if(task.isSuccessful()){
                                 startActivity(new Intent(Registro.this, MainActivity.class));
                                 finish();
                             }
                             else{
                                 Toast.makeText(Registro.this, "No se pudieron crear los datos correctamente",Toast.LENGTH_SHORT).show();
                             }

                         }
                     });
                 }
                 else{
                     Toast.makeText(Registro.this, "No se pudo registrar el usuario",Toast.LENGTH_SHORT).show();
                 }
             }
         });

    }
}