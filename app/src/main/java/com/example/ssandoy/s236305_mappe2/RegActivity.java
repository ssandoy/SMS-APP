package com.example.ssandoy.s236305_mappe2;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.regex.Pattern;

public class RegActivity extends AppCompatActivity {

    DBHandler db;

    final String regExName = "^[a-zæøåA-ZÆØÅ][a-zæøåA-ZÆØÅ -]{1,}$";
    final String regExPhone = "^[0-9]{8}$";

    private Pattern name = Pattern.compile(regExName);
    private Pattern phone = Pattern.compile(regExPhone);


    private EditText firstName;
    private EditText lastName;
    private EditText phNr;
    private DatePicker birthday;

    Button saveButton;
    Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        db = new DBHandler(this);
        db.open(); //TODO: db.close AND OPEN ONLY IN CLICKMETHODS??

        initWidgets();


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName = firstName.getText().toString();
                String lName = lastName.getText().toString();
                String phoneNr = phNr.getText().toString();
                int day = birthday.getDayOfMonth();
                int month = birthday.getMonth() + 1; //DatePicker-months is indexed from 0
                int year = birthday.getYear();

                //IF MATCHES THEN
                if(name.matcher(fName).matches() && name.matcher(lName).matches() && phone.matcher(phoneNr).matches()) {
                    saveContact(fName, lName, phoneNr, day, month, year);
                    finish();
                } else{

                new AlertDialog.Builder(RegActivity.this).setTitle("Error").setMessage("Feil input").show();
                }

                Log.d("REG", "Insert"); //TODO: MESSAGE THAT SUCCSESSFUL?
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
    }

    //TODO ON SAVEDINSTANCE FOR ALLE ACTIVITETER?


    public void initWidgets() {
        firstName = (EditText) findViewById(R.id.FirstNameET);
        lastName = (EditText) findViewById(R.id.LastNameET);
        phNr = (EditText) findViewById(R.id.phoneNrET);
        birthday = (DatePicker) findViewById(R.id.birthDatePicker);
        saveButton = (Button) findViewById(R.id.saveButton);
        returnButton = (Button) findViewById(R.id.exitButton);
    }

    public void saveContact(String firstName, String lastName, String phoneNr, int day, int month, int year) {
        ContentValues cv = new ContentValues(4);
        cv.put(db.KEY_FIRSTNAME, firstName);
        cv.put(db.KEY_LASTNAME, lastName);
        cv.put(db.KEY_PH_NO, phoneNr);
        cv.put(db.KEY_DAY, day);
        cv.put(db.KEY_MONTH, month);
        cv.put(db.KEY_YEAR,year);

        db.insert(cv);
    }
}
