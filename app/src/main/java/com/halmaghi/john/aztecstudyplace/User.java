package com.halmaghi.john.aztecstudyplace;


import java.util.ArrayList;

public class User {
    private String userID;
    private String displayName;
    private String email;
    private ArrayList<Course> courses;

    public User(){}

    public User(String userID, String displayName, String email){
        this.userID = userID;
        this.displayName = displayName;
        this. email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

}
