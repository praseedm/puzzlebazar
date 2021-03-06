package com.p6c.project.puzzlebazar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.p6c.project.puzzlebazar.model.Common;
import com.p6c.project.puzzlebazar.model.Contants;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    TextView profilename, profileemail;
    private CircleImageView profileImage;
    private FirebaseAuth mAuth;
    FirebaseUser mFbUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profileImage = findViewById(R.id.profileImageView);
        profilename = findViewById(R.id.profileName);
        profileemail = findViewById(R.id.profileEmail);

        mAuth = FirebaseAuth.getInstance();
        mFbUser = mAuth.getCurrentUser();
        profilename.setText(mFbUser.getDisplayName());
        profileemail.setText(mFbUser.getEmail());
        String pUrl = mFbUser.getPhotoUrl().toString();
        if(pUrl == null)
            pUrl = Contants.PhotoUrl;

        Glide.with(this)
                .load(pUrl)
                .apply(new RequestOptions().centerCrop())
                .into(profileImage);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.siginout :
                siginout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void siginout() {
        mAuth.signOut();
        startLogin();
    }

    private void startLogin() {
        Intent login = new Intent(this, LoginActivity.class);
        login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(login);
        finish();
    }

    public void chooseCategory(View view) {
        Intent intent = new Intent(this,CategoryActivity.class);
        startActivity(intent);
    }
}
