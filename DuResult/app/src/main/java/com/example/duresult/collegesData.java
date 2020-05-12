package com.example.duresult;

import java.util.ArrayList;
import java.util.HashMap;

public class collegesData {
    private String collegename;
    private String collegeUrl=null;
    private HashMap<String,ArrayList<String>> courses=new HashMap<String, ArrayList<String>>();


    public collegesData(String name,String url){
        collegename=name;
        collegeUrl=url;
    }
    public collegesData(String name)
    {
        collegename=name;
    }

    public void addCourseDetails(String coursename,ArrayList<String> details){
        courses.put(coursename,details);
    }

    public String getCollegename(){
        return collegename;
    }

    public String getCollegeUrl() {
        return collegeUrl;
    }
}
