package com.example.mp_8;

public class Grade {
    private int course_id;
    private String course_name;
    private String grade;
    private int student_id;
    public Grade(){

    }

    public Grade(int cid, String cn, String gd, int sid){
        course_id=cid;
        course_name=cn;
        grade=gd;
        student_id=sid;
    }

    public int getCourse_id() {
        return course_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public String getGrade() {
        return grade;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }
}
