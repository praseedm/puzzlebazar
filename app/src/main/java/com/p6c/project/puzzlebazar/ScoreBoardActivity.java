package com.p6c.project.puzzlebazar;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.p6c.project.puzzlebazar.model.Common;
import com.p6c.project.puzzlebazar.model.Contants;
import com.p6c.project.puzzlebazar.model.ScoreObj;
import com.p6c.project.puzzlebazar.model.ScoreViewholder;

public class ScoreBoardActivity extends AppCompatActivity {
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    Query scoreRef;
    RecyclerView scorelist;
    FirebaseRecyclerAdapter<ScoreObj,ScoreViewholder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        scorelist = findViewById(R.id.score_listview);

        scoreRef= mRootRef.child(Contants.Score_Ref).child(Common.category).orderByChild("score");
        showList();
    }

    private void showList() {
        scorelist.setHasFixedSize(false);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);

        scorelist.setLayoutManager(manager);
        FirebaseRecyclerOptions<ScoreObj> options = new FirebaseRecyclerOptions.Builder<ScoreObj>()
                .setQuery(scoreRef,ScoreObj.class)
                .build();

        mAdapter = new FirebaseRecyclerAdapter<ScoreObj, ScoreViewholder>(
                options
        ) {@Override
            protected void onBindViewHolder(@NonNull ScoreViewholder holder, int position, @NonNull ScoreObj model) {
                holder.name.setText(model.getUserName());
                holder.score.setText("Score : "+model.getScore()+"");
                Glide.with(ScoreBoardActivity.this)
                        .load(model.getPhotoUri())
                        .apply(new RequestOptions().centerCrop())
                        .into(holder.image);
            }

            @NonNull
            @Override
            public ScoreViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.score_item,parent,false);
                return new ScoreViewholder(view);
            }
        };

        scorelist.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}
