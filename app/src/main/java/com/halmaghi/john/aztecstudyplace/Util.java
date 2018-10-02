package com.halmaghi.john.aztecstudyplace;

import android.nfc.Tag;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import static android.content.ContentValues.TAG;

public class Util {

    public static boolean userExists(String userID, DataSnapshot snapshot){
        User user = new User();
        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
            user.setUserID(dataSnapshot.getValue(User.class).getUserID());
            if(user.getUserID().equals(userID)){
                Log.d(TAG, "Found existing userID!");
                return true;
            }
        }
        Log.d(TAG, "Did not find existing userID");
        return false;
    }

    //public static boolean userHasClasses();
}
