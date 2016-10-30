package com.example.ssandoy.s236305_mappe2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    private Pattern name = Pattern.compile(regExName);
    private Pattern phone = Pattern.compile(regExPhone);

    private EditText firstName;
    private EditText lastName;
    private EditText phoneNr;
    private DatePicker birthday;
    private Button submitIcon;
    private Button submitButton;


    private AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");


        initWidgets();

        db = new DBHandler(this);
        db.open();

        Intent intent = getIntent();
        LayoutInflater inflater = (LayoutInflater) getSupportActionBar().getThemedContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);

        Bundle pakke = intent.getExtras();
        personId = pakke.getInt("ID");
        Cursor cursor = db.findPersonByID(personId);
        cursor.moveToFirst();
        firstName.setText(cursor.getString(cursor.getColumnIndex(db.KEY_FIRSTNAME)));
        lastName.setText(cursor.getString(cursor.getColumnIndex(db.KEY_LASTNAME)));
        phoneNr.setText(cursor.getString(cursor.getColumnIndex(db.KEY_PH_NO)));
        birthday.updateDate(cursor.getInt(cursor.getColumnIndex(db.KEY_YEAR)),cursor.getInt(cursor.getColumnIndex(db.KEY_MONTH))-1, cursor.getInt(cursor.getColumnIndex(db.KEY_DAY)));

        createListeners();
        db.close();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("firstName", firstName.getText().toString());
        savedInstanceState.putString("lastName", lastName.getText().toString());
        savedInstanceState.putString("phNr", phoneNr.getText().toString());
        savedInstanceState.putInt("year", birthday.getYear());
        savedInstanceState.putInt("month", birthday.getMonth());
        savedInstanceState.putInt("day", birthday.getDayOfMonth());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String firstname = savedInstanceState.getString("firstName");
        String lastname = savedInstanceState.getString("lastName");
        String phNr = savedInstanceState.getString("phNr");
        firstName.setText(firstname);
        lastName.setText(lastname);
        phoneNr.setText(phNr);

        int year = savedInstanceState.getInt("year");
        int month = savedInstanceState.getInt("month");
        int day = savedInstanceState.getInt("day");
        birthday.updateDate(year,month,day);


    }

    @Override
    public	boolean onCreateOptionsMenu(Menu menu)	{
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.change_menu,	menu);
        return	true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delContact:
                new AlertDialog.Builder(ChangeActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.warning)
                        .setMessage(R.string.deletePerson)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.reopen();
                                db.deletePerson(personId);
                                Intent intent = new Intent(ChangeActivity.this, StartActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                break;
            case R.id.action_ret:
                Intent intent  = new Intent(ChangeActivity.this,StartActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return	true;
    }


    public void initWidgets() {
        firstName = (EditText) findViewById(R.id.FirstNameTV);
        lastName = (EditText) findViewById(R.id.LastNameTV);
        phoneNr = (EditText) findViewById(R.id.phoneNrTV);
        birthday = (DatePicker) findViewById(R.id.birthDatePicker);
        submitButton = (Button) findViewById(R.id.saveButton);
        submitIcon = (Button) findViewById(R.id.submitIcon);
    }

    public void createListeners() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            db.reopen();
                final String fName = firstName.getText().toString();
                final String lName = lastName.getText().toString();
                final String phNr = phoneNr.getText().toString();
                final int day = birthday.getDayOfMonth();
                final int month = birthday.getMonth()+1;
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
                                    Intent intent = new Intent(ChangeActivity.this, DisplayActivity.class);
                                    intent.putExtra("ID", personId);
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

        submitIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            db.reopen();
                final String fName = firstName.getText().toString();
                final String lName = lastName.getText().toString();
                final String phNr = phoneNr.getText().toString();
                final int day = birthday.getDayOfMonth();
                final int month = birthday.getMonth()+1;
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
                                    Intent intent = new Intent(ChangeActivity.this, DisplayActivity.class);
                                    intent.putExtra("ID", personId);
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

    }
}
