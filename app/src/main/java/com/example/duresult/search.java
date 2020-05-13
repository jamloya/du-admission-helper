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
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.HashMap;
import java.util.Objects;

public class search extends Fragment {
    double percentage=0;
    ArrayList<Double> allMarks = new ArrayList<Double>();
    private RadioButton radioButton;
    private EditText marks1, marks2, marks3, marks4, marks5;

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


        isValidInput(marks1);
        isValidInput(marks2);
        isValidInput(marks3);
        isValidInput(marks4);
        isValidInput(marks5);

        double totalMarks = 0;
        for (double i : allMarks)
            totalMarks += i;

        percentage = (totalMarks * 100) / (allMarks.size() * 100);
        per.setText(String.valueOf(percentage) + "%");

    }

    public boolean checkInputs()
    {
        boolean flag;
        if(marks1.getText().toString().replaceAll("\\s","")=="" ||
        marks2.getText().toString().replaceAll("\\s","")=="" ||
        marks3.getText().toString().replaceAll("\\s","")=="" ||
        marks4.getText().toString().replaceAll("\\s","")=="" ||
        marks5.getText().toString().replaceAll("\\s","")=="")
        {
            flag=true;
        }
        else
        {
            flag=false;
        }
        return flag;
    }
    RadioGroup radioGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_college, container, false);
        final Spinner sp = (Spinner) v.findViewById(R.id.spinner1);
        final ArrayList<String> streams = new ArrayList<>();
        streams.add("Science");
        streams.add("Arts and Commerce");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.simple_spinner, streams);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setEnabled(true);
        final HashMap<String, String> hm = new HashMap<String, String>();
        final Spinner sp1 = (Spinner) v.findViewById(R.id.spinner2);
        final ArrayList<String> categories = new ArrayList<String>();
        categories.add("General (G)");
        hm.put("General (G)", "G");
        categories.add("Scheduled Caste (SC)");
        hm.put("Scheduled Caste (SC)", "SC");
        categories.add("Scheduled Tribe(ST)");
        hm.put("Scheduled Tribe(ST)", "ST");
        categories.add("Sikh minority (SM)");
        hm.put("Sikh minority (SM)", "SM");
        categories.add("(EWS)");
        hm.put("(EWS)", "EWS");
        categories.add("(PWD)");
        hm.put("(PWD)", "PWD");
        categories.add("Kashmiri migrants(KM)");
        hm.put("Kashmiri migrants(KM)", "KM");
        categories.add("(OBC)");
        hm.put("(OBC)", "OBC");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getActivity(), R.layout.simple_spinner, categories);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter1);
        sp1.setEnabled(true);

        per = (TextView) v.findViewById(R.id.calcPerText);

        marks1 = (EditText) v.findViewById(R.id.firstSubject);
        marks2 = (EditText) v.findViewById(R.id.secondSubject);
        marks3 = (EditText) v.findViewById(R.id.thirdSubject);
        marks4 = (EditText) v.findViewById(R.id.fourthSubject);
        marks5 = (EditText) v.findViewById(R.id.fifthSubject);

        final TextView per = (TextView) v.findViewById(R.id.calcPerText);

        final Button calcPer = (Button) v.findViewById(R.id.calcPerBtn);
        calcPer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                allMarks.clear();
                calculatePercentage();
            }
        });
        final RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.radioButton);

        final TextView result = (TextView) v.findViewById(R.id.result);

        Button search = (Button) v.findViewById(R.id.searchColleges);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                allMarks.clear();
                calculatePercentage();
                if(!checkInputs()) {
                    String ChoosenStream = sp.getSelectedItem().toString();
                    String ChoosenCategory = hm.get(sp1.getSelectedItem().toString());
                    int selectedid = radioGroup.getCheckedRadioButtonId();

                    radioButton = (RadioButton) radioGroup.findViewById(selectedid);

                    Intent i = new Intent(getActivity(), after_Search.class);
                    i.putExtra("percentage", String.valueOf(percentage));
                    i.putExtra("gender", String.valueOf(radioButton.getText()));
                    i.putExtra("category", ChoosenCategory);
                    startActivity(i);
                }
            }
        });

        return v;
    }


}

