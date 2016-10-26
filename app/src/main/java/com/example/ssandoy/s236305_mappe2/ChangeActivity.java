package com.example.ssandoy.s236305_mappe2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.regex.Pattern;

public class ChangeActivity extends AppCompatActivity {

    DBHandler db;

    int personId;

    final String regExName = "^[a-zæøåA-ZÆØÅ][a-zæøåA-ZÆØÅ -]{1,}$";
    final String regExPhone = "^[0-9]{8}$";

    Pattern name = Pattern.compile(regExName);
    Pattern phone = Pattern.compile(regExPhone);

    EditText firstName;
    EditText lastName;
    EditText phoneNr;
    DatePicker birthday;
    Button submitButton;
    Button deleteButton;

    Button returnButton;

    private AlertDialog alertDialog;


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
        birthday.updateDate(cursor.getInt(cursor.getColumnIndex(db.KEY_YEAR)),cursor.getInt(cursor.getColumnIndex(db.KEY_MONTH))-1, cursor.getInt(cursor.getColumnIndex(db.KEY_DAY)));

        createListeners();


    }


    public void initWidgets() {
        firstName = (EditText) findViewById(R.id.FirstNameET);
        lastName = (EditText) findViewById(R.id.LastNameET);
        phoneNr = (EditText) findViewById(R.id.phoneNrET);
        birthday = (DatePicker) findViewById(R.id.birthDatePicker);
        submitButton = (Button) findViewById(R.id.saveButton);
        deleteButton = (Button) findViewById(R.id.deleteButton);
        returnButton = (Button) findViewById(R.id.deleteButton);
    }

    public void createListeners() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String fName = firstName.getText().toString();
                final String lName = lastName.getText().toString();
                final String phNr = phoneNr.getText().toString();
                final int day = birthday.getDayOfMonth();
                final int month = birthday.getMonth();
                final int year = birthday.getYear();

                if (name.matcher(fName).matches() && name.matcher(lName).matches() && phone.matcher(phNr).matches()) {
                    alertDialog = new AlertDialog.Builder(ChangeActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(R.string.warning)
                            .setMessage(R.string.updatePerson)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.update(fName, lName, phNr, year, month, day, personId);
                                    Intent intent = new Intent(ChangeActivity.this, ContactsActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();

                } else {

                    new AlertDialog.Builder(ChangeActivity.this).setTitle("Error").setMessage("Feil input").show();
                }
            }
        });



        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog = new AlertDialog.Builder(ChangeActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.warning)
                        .setMessage(R.string.deletePerson)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.deletePerson(personId);
                                Intent intent = new Intent(ChangeActivity.this, ContactsActivity.class);
                                startActivity(intent);
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

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

    }
}
