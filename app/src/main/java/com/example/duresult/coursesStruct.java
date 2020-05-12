package com.example.duresult;

import java.util.ArrayList;

public class coursesStruct {
    private String courseName;
    private ArrayList<String> details = new ArrayList<String>();

    public coursesStruct(String name, ArrayList<String> detail) {
        this.courseName = name;
        this.details = detail;
    }


    public String getCourseName() {
        return this.courseName;
    }

    public String getCategory() {
        return this.details.get(0);
    }

    public String getPercentage() {
        return this.details.get(1);
    }
}
