package com.example.croudstate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class home extends AppCompatActivity {

    private ImageButton cerrar;
    private TextView nombre;

     private ImageButton perf;
     FirebaseAuth auth;
     DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_home);

        cerrar = (ImageButton)findViewById(R.id.btn_cerrarSesion);
        perf = (ImageButton)findViewById(R.id.btn_perfil);
        auth = FirebaseAuth.getInstance();
        nombre = (TextView)findViewById(R.id.nombre_tv);
        database = FirebaseDatabase.getInstance().getReference();

        perf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, profile.class);
                startActivity(intent);
            }
        });


        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(home.this, Login.class));
                finish();
            }
        });
        getUserInfo();
    }

    //TRAER LOS DATOS DEL USUARIO
    private void getUserInfo(){
        String id = auth.getCurrentUser().getUid();
        database.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    String name = dataSnapshot.child("nombre").getValue().toString();

                    nombre.setText(name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}