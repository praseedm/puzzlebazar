package com.p6c.project.puzzlebazar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView score_view, total_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        score_view = findViewById(R.id.dis_score);
        total_view = findViewById(R.id.dis_totalQ);

        Bundle extra = getIntent().getExtras();
        if(extra != null){
            int score = extra.getInt("SCORE");
            int total = extra.getInt("TOTAL");
            int correct = extra.getInt("CORRECT");
            score_view.setText(String.format("SCORE : %d",score));
            total_view.setText(String.format("PASSED : %d / %d",correct,total));
        }

    }

    public void startGame(View view) {
        Intent intent = new Intent(this,CategoryActivity.class);
        startActivity(intent);
        finish();
    }
}
