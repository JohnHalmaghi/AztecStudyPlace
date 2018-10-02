package com.halmaghi.john.aztecstudyplace;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LandingActivity extends AppCompatActivity {

    private FirebaseUser currentUser;
    private DatabaseReference currentUsersRecord;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        final String currentUser = intent.getStringExtra("userID");
        Toast.makeText(this, currentUser, Toast.LENGTH_SHORT).show();
        database = FirebaseDatabase.getInstance();
        currentUsersRecord = database.getReference("users/"+currentUser);
        currentUsersRecord.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("classes")){
                    //if user has classes
                }
                else {
                    //if user does not have classes
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if(currentUser != null){
            //if(currentUser.hasClasses){
                //display classes
            //}
        } else {
            //load
        }

        setContentView(R.layout.activity_landing);
    }
}
