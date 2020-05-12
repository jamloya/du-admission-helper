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

public class CollegeAdapter extends ArrayAdapter<collegesData> {
    public CollegeAdapter(Context c,ArrayList<collegesData> colleges)
    {
        super(c,0,colleges);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.college_list, parent, false);
        }

        collegesData currentCollege = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID miwok_text_view.
        TextView college = (TextView) listItemView.findViewById(R.id.collegesText);
        // Get the Miwok translation from the currentWord object and set this text on
        // the Miwok TextView.
        college.setText(currentCollege.getCollegename());
        return listItemView;
    }
}
