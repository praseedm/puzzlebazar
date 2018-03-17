package com.p6c.project.puzzlebazar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.p6c.project.puzzlebazar.model.Common;

public class LoadActivity extends AppCompatActivity {
    TextView category_info;
    Button playB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        category_info = findViewById(R.id.cat_info);
        playB = findViewById(R.id.playB);
        category_info.setText(Common.category);
        playB.setVisibility(View.VISIBLE);
    }

    public void startquiz(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
