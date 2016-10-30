package com.example.ssandoy.s236305_mappe2;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ssandoy on 15.10.2016.
 */
public class PersonAdapter extends BaseAdapter implements Filterable{

    private ArrayList<Person> mOriginalValues;
    private ArrayList<Person> mDisplayedValues;
    LayoutInflater inflater;

    public PersonAdapter(Context context, ArrayList<Person> mProductArrayList) {
        this.mOriginalValues = mProductArrayList;
        this.mDisplayedValues = mProductArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDisplayedValues.size();
    }

    @Override
    public Person getItem(int position) {
        return mDisplayedValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                mDisplayedValues = (ArrayList<Person>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<Person> FilteredArrList = new ArrayList<Person>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<>(mDisplayedValues);
                }

                if (constraint == null || constraint.length() == 0) {

                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) { //SJEKK PÅ OM FILTER TREFFER
                         String data1 = mOriginalValues.get(i).getFirstName();
                         String data2 = mOriginalValues.get(i).getLastName();
                        if (data1.toLowerCase().contains(constraint.toString()) || data2.toLowerCase().contains(constraint.toString())){
                            FilteredArrList.add(new Person(mOriginalValues.get(i).getFirstName(), mOriginalValues.get(i).getLastName(),mOriginalValues.get(i).getPhoneNumber(),mOriginalValues.get(i).getBirthday()));
                        }
                    }
                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }

    private class ViewHolder {
        LinearLayout llContainer;
        TextView tvName;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_person, null);
            holder.llContainer = (LinearLayout)convertView.findViewById(R.id.llContainer);
            holder.tvName = (TextView) convertView.findViewById(R.id.Name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(mDisplayedValues.get(position).getFirstName() + " " + mDisplayedValues.get(position).getLastName());


        holder.tvName.setTextColor(Color.parseColor("#42C0FB"));
        holder.tvName.setTextSize(24);
        return convertView;

    }

    public int getPersonID(int position) {
        Person person = getItem(position);
        return person.get_ID();
    }



}
