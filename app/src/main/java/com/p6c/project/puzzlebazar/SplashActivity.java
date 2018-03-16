package com.p6c.project.puzzlebazar;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SplashActivity extends AppCompatActivity {
    LinearLayout l1;
    Animation uptodowm;
    private FirebaseAuth mAuth ;
    private FirebaseUser mFbUser;
    private DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private String TAG = "SplashActivity";
    private static int SPLASH_TIME_OUT = 1600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        l1 = findViewById(R.id.l1);
        uptodowm= AnimationUtils.loadAnimation(this,R.anim.uptodown);

        l1.setAnimation(uptodowm);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ChooseNext();
            }
        },SPLASH_TIME_OUT);
    }

    private void ChooseNext() {
        mAuth = FirebaseAuth.getInstance();
        mFbUser = mAuth.getCurrentUser();
        if(mFbUser != null){
            Intent main = new Intent(this,ProfileActivity.class);
            main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(main);
        }
        else {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
