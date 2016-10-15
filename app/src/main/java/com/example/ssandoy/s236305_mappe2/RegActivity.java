package com.example.ssandoy.s236305_mappe2;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class RegActivity extends AppCompatActivity { //TODO ANNET NAVN? SKAL DEN BENYTTES VED ENDRINGER?

    DBHandler db;

    EditText firstName;
    EditText lastName;
    EditText phNr;
    DatePicker birthday;

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
                String phoneNr = phNr.getText().toString(); //TODO: VALIDERING PÅ TELEFONNUMMER
                int day = birthday.getDayOfMonth();
                int month = birthday.getMonth();
                int year = birthday.getYear();

                ContentValues cv = new ContentValues(4);
                cv.put(db.KEY_FIRSTNAME, fName);
                cv.put(db.KEY_LASTNAME, lName);
                cv.put(db.KEY_PH_NO, phoneNr);
                cv.put(db.KEY_DAY, day);
                cv.put(db.KEY_MONTH, month);
                cv.put(db.KEY_YEAR,year);

                db.insert(cv);
                Log.d("REG", "Insert"); //TODO: CHECK IF SUCCESSFULL AND RETURN TO STARTACTIVITY
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

    public void initWidgets() {
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        phNr = (EditText) findViewById(R.id.phoneNr);
        birthday = (DatePicker) findViewById(R.id.birthDate);
        saveButton = (Button) findViewById(R.id.saveButton);
        returnButton = (Button) findViewById(R.id.returnButton);
    }
}
