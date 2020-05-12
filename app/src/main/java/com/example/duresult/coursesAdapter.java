package com.example.duresult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class coursesAdapter extends ArrayAdapter<coursesStruct> {
    public coursesAdapter(@NonNull Context context, ArrayList<coursesStruct> data) {
        super(context, 0, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.result_list, parent, false);
        }
        coursesStruct c = getItem(position);
        TextView coursesname = (TextView) listItemView.findViewById(R.id.course_nameText);
        coursesname.setText(c.getCourseName());

        TextView categoryname = (TextView) listItemView.findViewById(R.id.categoryText);
        categoryname.setText(c.getCategory());

        TextView percentage = (TextView) listItemView.findViewById(R.id.percentageText);
        percentage.setText(c.getPercentage());

        return listItemView;
    }
}
