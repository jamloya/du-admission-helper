package com.example.duresult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Map;
import java.util.function.BiConsumer;

public class after_Search extends AppCompatActivity {
    final ArrayList<collegesData> resultColleges = new ArrayList<collegesData>();

    public void showList() {

        CollegeAdapter adapter = new CollegeAdapter(getApplicationContext(), resultColleges);
        ListView lv = (ListView) findViewById(R.id.result_list);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                collegesData clickedcollege = resultColleges.get(position);
                Intent i = new Intent(getApplicationContext(), resultCourses.class);
                Bundle b = new Bundle();
                b.putString("collegename", clickedcollege.getCollegename());
                for (Map.Entry mapElement : clickedcollege.getCourses().entrySet()) {
                    String key = (String) mapElement.getKey();
                    ArrayList<String> details;
                    details = (ArrayList<String>) mapElement.getValue();
                    b.putStringArrayList(key, details);
                }
                i.putExtras(b);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after__search);

        Bundle extras = getIntent().getExtras();
        String percentage = extras.getString("percentage");
        String gender = extras.getString("gender");
        String category = extras.getString("category");
        final TextView Qualified=(TextView) findViewById(R.id.QualifiedText);
        Qualified.setVisibility(View.GONE);
        final TextView message = (TextView) findViewById(R.id.message);

        AndroidNetworking.get("https://dulistparser.herokuapp.com/search")
                .addQueryParameter("percentage", percentage)
                .addQueryParameter("gender", gender)
                .addQueryParameter("category", category)
                .addHeaders("Connection", "close")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        if (response.length() != 0) {
                            JSONArray keys = response.names();
                            String key = null;
                            if (keys.length() > 0) {
                                for (int i = 0; i < keys.length(); i++) {
                                    try {
                                        key = keys.getString(i);
                                        collegesData currCollege = new collegesData(key);
                                        resultColleges.add(currCollege);
                                        JSONArray courses = response.getJSONArray(key);
                                        if (courses.length() > 0) {
                                            for (int j = 0; j < courses.length(); j++) {
                                                ArrayList<String> details = new ArrayList<String>();
                                                JSONArray temp = courses.getJSONArray(j);
                                                details.add(temp.getString(1));
                                                details.add(temp.getString(2));
                                                //Log.d("temp"+String.valueOf(j),temp.getString(0));
                                                currCollege.addCourseDetails(temp.getString(0), details);
                                            }
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(getApplicationContext(),"Error Getting results.",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                            if (!resultColleges.isEmpty()) {
                                message.setVisibility(View.GONE);
                                Qualified.setVisibility(View.VISIBLE);
                                showList();
                            }
                            else
                            {
                                message.setVisibility(View.VISIBLE);
                            }

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Error Getting Results.Try Again!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
