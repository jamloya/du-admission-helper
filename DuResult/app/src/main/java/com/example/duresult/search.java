package com.example.duresult;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.math.MathUtils;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

public class search extends Fragment {
    double percentage;
    ArrayList<Double> allMarks = new ArrayList<Double>();

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private void isValidInput(EditText e) {
        String toCheck = String.valueOf(e.getText());
        if (isNumeric(toCheck)) {
            double number = Double.parseDouble(toCheck);
            if (number >= 0 && number <= 100) {
                allMarks.add(number);
                //Toast.makeText(getContext(),toCheck,Toast.LENGTH_SHORT).show();
            } else {
                e.setError("Not Valid");
            }
        } else {
            e.setError("Not Valid");
        }
    }

    TextView per;

    public void calculatePercentage() {
        double totalMarks = 0;
        for (double i : allMarks)
            totalMarks += i;

        percentage = (totalMarks * 100) / (allMarks.size() * 100);
        per.setText(String.valueOf(percentage) + "%");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_college, container, false);
        Spinner sp = (Spinner) v.findViewById(R.id.spinner1);
        final ArrayList<String> streams = new ArrayList<>();
        streams.add("Science");
        streams.add("Arts and Commerce");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.simple_spinner, streams);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setEnabled(true);
        per = (TextView) v.findViewById(R.id.calcPerText);
        final EditText marks1, marks2, marks3, marks4, marks5;
        marks1 = (EditText) v.findViewById(R.id.firstSubject);
        marks2 = (EditText) v.findViewById(R.id.secondSubject);
        marks3 = (EditText) v.findViewById(R.id.thirdSubject);
        marks4 = (EditText) v.findViewById(R.id.fourthSubject);
        marks5 = (EditText) v.findViewById(R.id.fifthSubject);

        final TextView per = (TextView) v.findViewById(R.id.calcPerText);

        Button calcPer = (Button) v.findViewById(R.id.calcPerBtn);
        calcPer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                allMarks.clear();
                isValidInput(marks1);
                isValidInput(marks2);
                isValidInput(marks3);
                isValidInput(marks4);
                isValidInput(marks5);

                if (allMarks.size() == 5) {
                    calculatePercentage();
                }
            }
        });

        final TextView result = (TextView) v.findViewById(R.id.result);

        Button search = (Button) v.findViewById(R.id.searchColleges);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(),after_Search.class);
                i.putExtra("percentage",String.valueOf(percentage));
                i.putExtra("gender","male");
                i.putExtra("category","G");
                startActivity(i);
            }
        });

        return v;
    }


}

