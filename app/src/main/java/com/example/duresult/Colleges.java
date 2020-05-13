package com.example.duresult;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class Colleges extends Fragment {
    final ArrayList<collegesData> allColleges = new ArrayList<collegesData>();
    View v;

    void addToList() {

        if (!allColleges.isEmpty()) {

            CollegeAdapter adapter = new CollegeAdapter(getActivity(), allColleges);
            ListView lv = (ListView) v.findViewById(R.id.collegeList);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    collegesData clickedItem = allColleges.get(position);
                    Uri url = Uri.parse(clickedItem.getCollegeUrl());
                    Intent i = new Intent(Intent.ACTION_VIEW, url);
                    startActivity(i);

                }
            });
        }
    }

    public boolean isInternetWorking(){
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(!isConnected)
        {
            Toast.makeText(getActivity(),"No Internet Connection.",Toast.LENGTH_SHORT).show();
        }
        return isConnected;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.colleges_page, container, false);
        if(isInternetWorking()) {
            if (allColleges.isEmpty()) {
                AndroidNetworking.get("Api/Colleges")
                        .addHeaders("Connection", "close")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                v.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                                JSONArray keys = response.names();
                                String key = "null";
                                JSONArray value = null;
                                for (int i = 0; i < keys.length(); ++i) {

                                    try {
                                        key = keys.getString(i);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        value = response.getJSONArray(key); // Here's your value
                                        for (int it = 0; it < value.length(); it++) {
                                            JSONArray temp = value.getJSONArray(it);
                                            allColleges.add(new collegesData(temp.getString(0), temp.getString(1)));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                addToList();
                            }

                            @Override
                            public void onError(ANError anError) {
                                v.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                                Toast.makeText(getActivity(), "Error Getting Results.Try Again!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
        else
        {
            v.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        }
        return v;
    }
}

