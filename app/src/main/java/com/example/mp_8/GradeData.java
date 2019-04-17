package com.example.mp_8;

public class GradeData {

    public String coName;
    public String grade;
    public String name;


    public GradeData(String nm, String gd){
        coName=nm;
        grade=gd;
        name="";


    }

    public GradeData(String cnm, String gd, String nm){
        coName=cnm;
        grade=gd;
        name=nm;
    }
}
