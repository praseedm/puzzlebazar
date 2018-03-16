package com.p6c.project.puzzlebazar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.p6c.project.puzzlebazar.model.Common;
import com.p6c.project.puzzlebazar.model.Contants;
import com.p6c.project.puzzlebazar.model.UserObj;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth ;
    private FirebaseUser mFbUser;
    private String userName , userEmail;
    private String TAG = "LoginActivity";
    private static final int RC_SIGN_IN = 9001;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth.AuthStateListener mAuthListener;
    SignInButton signB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signB = findViewById(R.id.signB);
        signB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sigin();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mFbUser = mAuth.getCurrentUser();
                if(mFbUser != null){
                    String photoUri = Contants.PhotoUrl;
                    if(mFbUser.getPhotoUrl() != null){
                        photoUri = mFbUser.getPhotoUrl().toString();
                    }
                    UserObj newUser = new UserObj(
                            mFbUser.getDisplayName(),photoUri,
                            mFbUser.getUid(),mFbUser.getEmail()
                    );
                    mRootRef.child(Contants.User_Ref).child(mFbUser.getUid()).setValue(newUser);
                    Common.user = newUser;
                    startProfileActivity();
                    finish();
                }
                else {

                }

            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            Log.d(TAG, "onStop: removed");
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void startProfileActivity() {
        Intent main = new Intent(this,ProfileActivity.class);
        main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(main);
    }

    public void sigin() {
        Toast.makeText(this, "Sigin", Toast.LENGTH_SHORT).show();
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.GoogleBuilder().build()))
                        .setIsSmartLockEnabled(false)
                        .build(),
                RC_SIGN_IN);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showSnackbar(R.string.sign_in_cancelled);
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackbar(R.string.no_internet_connection);
                    return;
                }

                showSnackbar(R.string.unknown_error);
                Log.e(TAG, "Sign-in error: ", response.getError());
            }
        }
    }

    public void showSnackbar(int msg){
        Snackbar.make(findViewById(R.id.signB), msg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        //Toast.makeText(this,msg , Toast.LENGTH_SHORT).show();
        //Snackbar.make(findViewById(R.id.snackbar),msg,Snackbar.LENGTH_SHORT).show();

    }
}
