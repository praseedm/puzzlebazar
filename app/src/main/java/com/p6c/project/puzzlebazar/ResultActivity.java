package com.p6c.project.puzzlebazar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.p6c.project.puzzlebazar.model.Common;
import com.p6c.project.puzzlebazar.model.Contants;
import com.p6c.project.puzzlebazar.model.ScoreObj;

public class ResultActivity extends AppCompatActivity {

    TextView score_view, total_view;
    Button playB;
    private FirebaseAuth mAuth ;
    private FirebaseUser mFbUser;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        score_view = findViewById(R.id.dis_score);
        total_view = findViewById(R.id.dis_totalQ);
        playB = findViewById(R.id.playAgain);

        Bundle extra = getIntent().getExtras();
        if(extra != null){
            score = extra.getInt("SCORE");
            int total = extra.getInt("TOTAL");
            int correct = extra.getInt("CORRECT");
            score_view.setText(String.format("SCORE : %d",score));
            total_view.setText(String.format("PASSED : %d / %d",correct,total));
        }

        mAuth = FirebaseAuth.getInstance();
        mFbUser = mAuth.getCurrentUser();
        //write score to cloud
        ScoreObj newObj = new ScoreObj(mFbUser.getUid(),mFbUser.getDisplayName(),mFbUser.getPhotoUrl().toString(),score, Common.category);
        mRootRef.child(Contants.Score_Ref).child(Common.category).push()//.child(mFbUser.getUid()+"_"+Common.category)
                .setValue(newObj);
        playB.setVisibility(View.VISIBLE);
    }

    public void startGame(View view) {
        Intent intent = new Intent(this,ScoreBoardActivity.class);
        startActivity(intent);
        finish();
    }
}
