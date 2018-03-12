package com.p6c.project.puzzlebazar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class SplashActivity extends AppCompatActivity {
    LinearLayout l1;
    Button playB;
    Animation uptodowm,downtoup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        l1 = findViewById(R.id.l1);
        playB = findViewById(R.id.playB);
        uptodowm= AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);

        l1.setAnimation(uptodowm);
        playB.setAnimation(downtoup);
    }
}
