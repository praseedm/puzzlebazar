package com.p6c.project.puzzlebazar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.p6c.project.puzzlebazar.model.Common;
import com.p6c.project.puzzlebazar.model.Contants;
import com.p6c.project.puzzlebazar.model.Question;

import java.util.Collections;

public class LoadActivity extends AppCompatActivity {
    TextView category_info,load_txt;
    Button playB, scoreB;
    FirebaseDatabase fdb = FirebaseDatabase.getInstance();
    DatabaseReference mRef,QuesRef=fdb.getReference(Contants.Question_Ref);
    String totalRef = "total";
    int total_count = 0;
    int temp_count = 0;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        category_info = findViewById(R.id.cat_info);
        playB = findViewById(R.id.playB);
        scoreB = findViewById(R.id.newButton);
        load_txt = findViewById(R.id.load_txt);
        String msg = "Category " +Common.category + " selected,";
        category_info.setText(String.format("%s \n %s",msg, Contants.loadmsg));
        //playB.setVisibility(View.VISIBLE);
        category = Common.category;
        loadCount(category);
    }

    private void loadCount(String categoryId) {
        if(Contants.questionList.size() > 0)
            Contants.questionList.clear();
        total_count = 0;
        temp_count = 0;
        //Get question counts
        QuesRef.child(categoryId).child(totalRef).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                total_count = dataSnapshot.getValue(Integer.class);
                if(total_count == 0){
                    load_txt.setText("No questions.");}
                else {
                loadQuestion(category);}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void loadQuestion(String category) {
        //Load questions
        if(total_count > 0){

            QuesRef.child(category).child(Contants.Question_Ref)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                Question q = snapshot.getValue(Question.class);
                                Contants.questionList.add(q);
                                temp_count++;
                            }
                            if(temp_count == total_count){
                                load_txt.setVisibility(View.GONE);
                                playB.setVisibility(View.VISIBLE);
                                scoreB.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }


    }

    public void startquiz(View view) {
        //Shuffle Questions
        Collections.shuffle(Contants.questionList);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void showScores(View view) {
        Intent intent = new Intent(this,ScoreBoardActivity.class);
        startActivity(intent);
        finish();
    }
}
