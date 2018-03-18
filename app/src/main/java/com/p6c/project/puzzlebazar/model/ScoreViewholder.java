package com.p6c.project.puzzlebazar.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.p6c.project.puzzlebazar.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScoreViewholder extends RecyclerView.ViewHolder {
    public CircleImageView image;
    public TextView name,score;

    public ScoreViewholder(View itemView) {
        super(itemView);
        image= itemView.findViewById(R.id.image_view);
        name = itemView.findViewById(R.id.name_view);
        score = itemView.findViewById(R.id.score_view);
    }
}
