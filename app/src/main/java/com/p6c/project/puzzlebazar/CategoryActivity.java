package com.p6c.project.puzzlebazar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.p6c.project.puzzlebazar.model.Common;

public class CategoryActivity extends AppCompatActivity implements View.OnClickListener {
    Button cat1,cat2,cat3,cat4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        cat1 = findViewById(R.id.cat1);
        cat2 = findViewById(R.id.cat2);
        cat3 = findViewById(R.id.cat3);
        cat4 = findViewById(R.id.cat4);

        cat1.setOnClickListener(this);
        cat2.setOnClickListener(this);
        cat3.setOnClickListener(this);
        cat4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Button clicked = (Button) v;
        Common.category = clicked.getText().toString();
        Common.Score = 0;
        Intent intent = new Intent(this,LoadActivity.class);
        startActivity(intent);
    }
}
