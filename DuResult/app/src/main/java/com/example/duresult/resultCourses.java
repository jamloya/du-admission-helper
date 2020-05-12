package com.example.duresult;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class resultCourses extends AppCompatActivity {
 final ArrayList<coursesStruct> resultCourses=new ArrayList<coursesStruct>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_courses);
        Bundle extras=getIntent().getExtras();
        String collegeName=extras.getString("collegename");
        TextView t=(TextView) findViewById(R.id.collegeNameText);
        t.setText(collegeName);
        extras.remove("collegename");
        for (String key : extras.keySet()) {
                try {
                    resultCourses.add(new coursesStruct(key, extras.getStringArrayList(key)));
                }
                catch(ClassCastException e) {
                    Log.d("Error",String.valueOf(e));
                }
        }

        coursesAdapter adapter=new coursesAdapter(getApplicationContext(),resultCourses);
        ListView lv=(ListView) findViewById(R.id.result_courses_list);
        lv.setAdapter(adapter);
    }
}
