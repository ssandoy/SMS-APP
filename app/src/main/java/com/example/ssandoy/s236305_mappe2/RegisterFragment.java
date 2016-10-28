package com.example.ssandoy.s236305_mappe2;

import android.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

/**
 * Created by ssandoy on 28.10.2016.
 */
public class RegisterFragment extends Fragment {

    DBHandler db;

    final String regExName = "^[a-zæøåA-ZÆØÅ][a-zæøåA-ZÆØÅ -]{1,}$";
    final String regExPhone = "^[0-9]{8}$";

    private Pattern name = Pattern.compile(regExName);
    private Pattern phone = Pattern.compile(regExPhone);


    private EditText firstName;
    private EditText lastName;
    private EditText phNr;
    private DatePicker birthday;

    private Button saveButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_reg, container, false);

        LanguageSelector.loadLanguage(getActivity());
        db = new DBHandler(getActivity());

        initWidgets(v);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName = firstName.getText().toString();
                String lName = lastName.getText().toString();
                String phoneNr = phNr.getText().toString();
                int day = birthday.getDayOfMonth();
                int month = birthday.getMonth() + 1; //DatePicker-months is indexed from 0-11
                int year = birthday.getYear();

                //IF MATCHES THEN
                if(name.matcher(fName).matches() && name.matcher(lName).matches() && phone.matcher(phoneNr).matches()) {
                    saveContact(fName, lName, phoneNr, day, month, year);
                    Toast.makeText(getActivity(), R.string.contactAdded, Toast.LENGTH_SHORT).show();

                } else{
                    if(!phone.matcher(phoneNr).matches())
                        new AlertDialog.Builder(getActivity()).setTitle("Error").setMessage(R.string.nrValidate).show();
                    else if(!name.matcher(fName).matches() || !name.matcher(lName).matches())
                        new AlertDialog.Builder(getActivity()).setTitle("Error").setMessage(R.string.nameValidate).show();
                }

                Log.d("REG", "Insert");
            }
        });
        return v;
    }


    public void initWidgets(View v) {
        firstName = (EditText) v.findViewById(R.id.FirstNameET);
        lastName = (EditText) v.findViewById(R.id.LastNameET);
        phNr = (EditText) v.findViewById(R.id.phoneNrET);
        birthday = (DatePicker) v.findViewById(R.id.birthDatePicker);
        saveButton = (Button) v.findViewById(R.id.saveButton);
    }

    //TODO: ON RESTORE AND SAVE

    public void saveContact(String firstName, String lastName, String phoneNr, int day, int month, int year) {
        db.open();
        ContentValues cv = new ContentValues(4);
        cv.put(db.KEY_FIRSTNAME, firstName);
        cv.put(db.KEY_LASTNAME, lastName);
        cv.put(db.KEY_PH_NO, phoneNr);
        cv.put(db.KEY_DAY, day);
        cv.put(db.KEY_MONTH, month);
        cv.put(db.KEY_YEAR,year);

        db.insert(cv);
        db.close();
    }


}
