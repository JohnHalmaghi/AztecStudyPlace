package com.halmaghi.john.aztecstudyplace;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.halmaghi.john.aztecstudyplace.Util;


public class LogIn extends AppCompatActivity implements View.OnClickListener {

    private GoogleSignInClient googleSignIn;
    private SignInButton signInButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersTable;

    private int RC_SIGN_IN = 1;
    private static final String TAG = "LogInActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        GoogleSignInOptions signIn = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.server_client_id)).requestEmail().build();
        googleSignIn = GoogleSignIn.getClient(this, signIn);
        findViewById(R.id.continue_as_guest).setOnClickListener(this);
        signInButton = findViewById(R.id.google_sign_in);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this);

        //Check for class schedule update
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.google_sign_in:
                signIn();
                Log.d(TAG, "test");
                break;
            case R.id.continue_as_guest:
                continueAsGuest();
                break;
        }
        //Intent intent = new Intent(this, LandingActivity.class);
        //startActivity(intent);
    }


    private void continueAsGuest() {
        signOutAndDisconnect();
    }

    private void signOutAndDisconnect() {
        firebaseAuth.signOut();

        googleSignIn.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                updateUI(null);
            }
        });
    }

    private void signIn(){
        Intent signInIntent = googleSignIn.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String userID = user.getUid();
                            Log.d(TAG, userID);
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }
                        // ...
                    }
                });
    }

    private void updateUI(final FirebaseUser user) {
        Intent intent = new Intent(this, LandingActivity.class);
        if (user != null) {
            final String userID = user.getUid();
            final String userDisplayName = user.getDisplayName();
            final String userEmail = user.getEmail();

            usersTable = firebaseDatabase.getReference("users");
            usersTable.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(!Util.userExists(userID, dataSnapshot)){
                        User userToAdd = new User(userID, userDisplayName, userEmail);
                        usersTable.child(userID).setValue(userToAdd);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            intent.putExtra("userID", userID);
        }
        startActivity(intent);
    }

}
