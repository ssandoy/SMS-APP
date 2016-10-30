package com.example.ssandoy.s236305_mappe2;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

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

    private Button saveButton;
    private Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        db = new DBHandler(this);


        initWidgets();


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
                    Toast.makeText(RegActivity.this, R.string.contactAdded, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegActivity.this, StartActivity.class);
                    startActivity(intent);
                    finish();
                } else{
                    if(!phone.matcher(phoneNr).matches())
                        new AlertDialog.Builder(RegActivity.this).setTitle("Error").setMessage(R.string.nrValidate).show();
                    else if(!name.matcher(fName).matches() || !name.matcher(lName).matches())
                        new AlertDialog.Builder(RegActivity.this).setTitle("Error").setMessage(R.string.nameValidate).show();
                }

            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog builder = new AlertDialog.Builder(RegActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.warning)
                        .setMessage(R.string.exitContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent exit = new Intent(RegActivity.this,StartActivity.class);
                                startActivity(exit);
                                finish();
                            }
                        })
                        .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

            }
        });

    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("firstName", firstName.getText().toString());
        savedInstanceState.putString("lastName", lastName.getText().toString());
        savedInstanceState.putString("phNr", phNr.getText().toString());
        savedInstanceState.putInt("year", birthday.getYear());
        savedInstanceState.putInt("month", birthday.getMonth());
        savedInstanceState.putInt("day", birthday.getDayOfMonth());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String firstname = savedInstanceState.getString("firstName");
        String lastname = savedInstanceState.getString("lastName");
        String phoneNr = savedInstanceState.getString("phNr");
        firstName.setText(firstname);
        lastName.setText(lastname);
        phNr.setText(phoneNr);

        int year = savedInstanceState.getInt("year");
        int month = savedInstanceState.getInt("month");
        int day = savedInstanceState.getInt("day");
        birthday.updateDate(year,month,day);


    }


    public void initWidgets() {
        firstName = (EditText) findViewById(R.id.FirstNameTV);
        lastName = (EditText) findViewById(R.id.LastNameTV);
        phNr = (EditText) findViewById(R.id.phoneNrTV);
        birthday = (DatePicker) findViewById(R.id.birthDatePicker);
        saveButton = (Button) findViewById(R.id.saveButton);
        returnButton = (Button) findViewById(R.id.exitButton);
    }

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
