package com.example.duresult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class after_Search extends AppCompatActivity {
    final ArrayList<collegesData> resultColleges=new ArrayList<collegesData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after__search);

        Bundle extras=getIntent().getExtras();
        String percentage=extras.getString("percentage");
        String gender=extras.getString("gender");
        String category=extras.getString("category");


        AndroidNetworking.get("http://10.0.2.2:5002/search")
                .addQueryParameter("percentage", percentage)
                .addQueryParameter("gender", gender)
                .addQueryParameter("category", category)
                .addHeaders("Connection", "close")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray keys=response.names();
                        String key=null;
                        for(int i=0;i<keys.length();i++)
                        {
                            try {
                                key=keys.getString(i);
                                collegesData currCollege=new collegesData(key);
                                resultColleges.add(currCollege);
                                JSONArray courses=response.getJSONArray(key);
                                for(int j=0;j<courses.length();j++)
                                {
                                    ArrayList<String> details=new ArrayList<String>();
                                    JSONArray temp = courses.getJSONArray(j);
                                    details.add(temp.getString(1));
                                    details.add(temp.getString(2));
                                    //Log.d("temp"+String.valueOf(j),temp.getString(0));
                                    currCollege.addCourseDetails(temp.getString(0),details);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        CollegeAdapter adapter=new CollegeAdapter(getApplicationContext(),resultColleges);
                        ListView lv=(ListView) findViewById(R.id.result_list);
                        lv.setAdapter(adapter);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }
}
