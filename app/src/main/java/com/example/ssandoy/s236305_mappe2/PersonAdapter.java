package com.example.ssandoy.s236305_mappe2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ssandoy on 15.10.2016.
 */
public class PersonAdapter extends ArrayAdapter<Person> {

    public PersonAdapter(Context context, ArrayList<Person> persons) {
        super(context, 0, persons);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Person person = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_person, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.Name);;
        TextView tvPhNr  = (TextView) convertView.findViewById(R.id.phoneNr);
        TextView tvBDay  = (TextView) convertView.findViewById(R.id.birthDay);

        // Populate the data into the template view using the data object
        tvName.setText(person.getFirstName() + " " + person.getLastName());
        tvPhNr.setText(person.getPhoneNumber());
        tvBDay.setText(person.getBirthday().getTime().toString());
        // Return the completed view to render on screen
        return convertView;
    }

    public int getPersonID(int position) {
        Person person = getItem(position);
        return person.get_ID();
    }
}
