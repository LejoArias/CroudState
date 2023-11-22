package com.example.croudstate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();




      //agregar animacion
        //Animation launch_an1 = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);

        //ImageView logo_iv = findViewById(R.id.logo_iv);

        //logo_iv.setAnimation(launch_an1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              Intent intent = new Intent(MainActivity.this, Login.class);
              startActivity(intent);
              finish();
            }
        },5000);

    }
    //MANTENER SESION SI EL USUARIO YA SE LOGUEO
    @Override
    protected void onStart(){
        super.onStart();

        if (auth.getCurrentUser() != null){
            startActivity(new Intent(MainActivity.this, home.class));
            finish();
        }
    }
}