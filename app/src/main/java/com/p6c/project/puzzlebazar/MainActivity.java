package com.p6c.project.puzzlebazar;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.p6c.project.puzzlebazar.model.Contants;
import com.p6c.project.puzzlebazar.model.Question;

import java.util.Collections;

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

        Contants.categoryId = "01";
        loadQuestion(Contants.categoryId);
    }

    private void loadQuestion(String categoryId) {
        if(Contants.questionList.size() > 0)
            Contants.questionList.clear();

        QuesRef.orderByChild(Contants.cId).equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                            Question q = snapshot.getValue(Question.class);
                            Contants.questionList.add(q);
                        }
                        showQuestion(index);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        //Shuffle Questions
        Collections.shuffle(Contants.questionList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        totalQuestions = Contants.questionList.size();
        mCountDown = new CountDownTimer(TIMEOUT,INTERVAL) {
            @Override
            public void onTick(long millisec) {
                progressBar.setProgress(progressValue);
                progressValue++;
            }

            @Override
            public void onFinish() {
                mCountDown.cancel();
                //showQuestion(++index);
            }
        };
        //showQuestion(index);
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
            if(clicked.getText().equals(Contants.questionList.get(index).getCorrectAnswer())){
                score += 10;
                correctAnswer ++;
                showQuestion(++index);
            }
            else{
                Intent intent = new Intent(this,ResultActivity.class);
                Bundle data = new Bundle();
                data.putInt("SCORE",score);
                data.putInt("TOTAL",totalQuestions);
                data.putInt("CORRECT",correctAnswer);
                intent.putExtras(data);
                startActivity(intent);
                finish();
            }
            score_txt.setText(String.format("%d",score));
        }
    }

    private void showQuestion(int i)  {
        if(index < totalQuestions){
            Questionno++;
            qno_txt.setText(String.format("%d / %d",Questionno,totalQuestions));
            progressBar.setProgress(0);
            progressValue=0;
            if(Contants.questionList.get(index).getImage().equals("true")){
                //TODO LOAD IMAGE

            }
            else {
                Question_txt.setText(Contants.questionList.get(index).getQuestion());
                Question_img.setVisibility(View.INVISIBLE);
                Question_txt.setVisibility(View.VISIBLE);
            }
            Btn1.setText(Contants.questionList.get(index).getAnswer1());
            Btn2.setText(Contants.questionList.get(index).getAnswer2());
            Btn3.setText(Contants.questionList.get(index).getAnswer3());
            Btn4.setText(Contants.questionList.get(index).getAnswer4());

            mCountDown.start();
        }
        else{
            Intent intent = new Intent(this,ResultActivity.class);
            Bundle data = new Bundle();
            data.putInt("SCORE",score);
            data.putInt("TOTAL",totalQuestions);
            data.putInt("CORRECT",correctAnswer);
            intent.putExtras(data);
            startActivity(intent);
            finish();
        }
    }
}
