package com.example.ssandoy.s236305_mappe2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class ChangeActivity extends AppCompatActivity {

    DBHandler db;

    int personId;

    EditText firstName;
    EditText lastName;
    EditText phoneNr;
    DatePicker birthday;
    Button submitButton;
    Button deleteButton;

    Button returnButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        initWidgets();

        db = new DBHandler(this);
        db.open();

        Intent intent = getIntent();

        Bundle pakke = intent.getExtras();
        personId = pakke.getInt("ID");
        Log.d("ID", "" + personId);
        Cursor cursor = db.findPersonByID(personId);
        cursor.moveToFirst();
        firstName.setText(cursor.getString(cursor.getColumnIndex(db.KEY_FIRSTNAME)));
        lastName.setText(cursor.getString(cursor.getColumnIndex(db.KEY_LASTNAME)));
        phoneNr.setText(cursor.getString(cursor.getColumnIndex(db.KEY_PH_NO)));
        birthday.updateDate(cursor.getInt(cursor.getColumnIndex(db.KEY_YEAR)),cursor.getInt(cursor.getColumnIndex(db.KEY_MONTH)), cursor.getInt(cursor.getColumnIndex(db.KEY_DAY)));

    }


    public void initWidgets() {
        firstName = (EditText) findViewById(R.id.FirstNameET);
        lastName = (EditText) findViewById(R.id.LastNameET);
        phoneNr = (EditText) findViewById(R.id.phoneNrET);
        birthday = (DatePicker) findViewById(R.id.birthDatePicker);
        submitButton = (Button) findViewById(R.id.submitButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        returnButton = (Button) findViewById(R.id.deleteButton);
    }
}
