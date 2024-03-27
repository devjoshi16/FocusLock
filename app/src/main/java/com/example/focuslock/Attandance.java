package com.example.focuslock;

public class Attandance {
    public String courseName;
    public String courseDescription;
    public String courseDuration;

    Attandance() {

    }

    Attandance(String courseName, String courseDescription, String courseDuration) {
        this.courseName = courseName;
        this.courseDescription = courseDescription;
        this.courseDuration = courseDuration;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDuration() {
        return courseDuration;
    }

    public String getCourseDescription() {
        return courseDescription;
    }
}
