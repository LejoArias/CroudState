package com.example.croudstate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    ImageButton inver;
    ImageButton proyec;

    ImageButton info;

    TextView nom;

    TextView email;

    FirebaseAuth auth;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_profile);

        inver = (ImageButton) findViewById(R.id.btn_inversion);
        proyec = (ImageButton) findViewById(R.id.btn_proyectos);
        info = (ImageButton)findViewById(R.id.btn_informacion);
        nom = (TextView) findViewById(R.id.usuario_tv);
        email = (TextView) findViewById(R.id.email_tv);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        inver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile.this, home.class);
                startActivity(intent);
            }
        });

        proyec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile.this, home.class);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile.this, information.class);
                startActivity(intent);
            }
        });
        getUser();
    }

    //TRAER LOS DATOS DEL USUARIO
    private void getUser() {
        String id = auth.getCurrentUser().getUid();
        database.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("nombre").getValue().toString();
                    String mail = dataSnapshot.child("email").getValue().toString();

                    nom.setText(name);
                    email.setText(mail);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}


