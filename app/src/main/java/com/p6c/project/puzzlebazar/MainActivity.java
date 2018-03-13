package com.p6c.project.puzzlebazar;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.p6c.project.puzzlebazar.model.Contants;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseDatabase fdb = FirebaseDatabase.getInstance();
    DatabaseReference mRef,QuesRef=fdb.getReference(Contants.Question_Ref);
    final static long INTERVAL = 1000; //1 sec
    final static long TIMEOUT = 7000;
    int progressValue = 0;

    CountDownTimer mCountDown;

    int index=0, score=0, Questionno = 0 , totalQuestions = 0, correctAnswer;
    ProgressBar progressBar;
    ImageView Question_img;
    TextView Question_txt,score_txt,qno_txt;
    Button Btn1,Btn2,Btn3,Btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        score_txt = findViewById(R.id.score);
        qno_txt = findViewById(R.id.totalQuestions);
        Question_txt = findViewById(R.id.question_text);
        progressBar = findViewById(R.id.progressbar);
        Btn1 = findViewById(R.id.Answer1);
        Btn2 = findViewById(R.id.Answer2);
        Btn3 = findViewById(R.id.Answer3);
        Btn4 = findViewById(R.id.Answer4);

        Btn1.setOnClickListener(this);
        Btn2.setOnClickListener(this);
        Btn3.setOnClickListener(this);
        Btn4.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        mCountDown.cancel();
        if(index < totalQuestions){
            Button clicked = (Button) v;
            if(clicked.getText().equals(Contants.questionList.get(index).))
        }
    }
}
