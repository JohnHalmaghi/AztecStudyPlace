package com.halmaghi.john.aztecstudyplace;


public class Course {
    private String courseName;
    private String courseInstructor;
    private String courseMeetingTime;
    private String classroom;

    public Course() {}

    public Course(String courseName, String courseInstructor, String courseMeetingTime, String classroom){
        this.courseName = courseName;
        this. courseInstructor = courseInstructor;
        this.courseMeetingTime = courseMeetingTime;
        this.classroom = classroom;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseInstructor() {
        return courseInstructor;
    }

    public void setCourseInstructor(String courseInstructor) {
        this.courseInstructor = courseInstructor;
    }

    public String getCourseMeetingTime() {
        return courseMeetingTime;
    }

    public void setCourseMeetingTime(String courseMeetingTime) {
        this.courseMeetingTime = courseMeetingTime;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}
