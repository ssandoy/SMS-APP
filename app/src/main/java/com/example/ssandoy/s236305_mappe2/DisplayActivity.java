package com.example.ssandoy.s236305_mappe2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    DBHandler db;

    private TextView firstName;
    private TextView lastName;
    private TextView phoneNr;
    private TextView birthday;

    int personId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");

        initWidgets();
        db = new DBHandler(this);
        db.open();

        Intent intent = getIntent();
        Bundle pakke = intent.getExtras();
        personId = pakke.getInt("ID");
        Cursor cursor = db.findPersonByID(personId);
        cursor.moveToFirst();
        firstName.setText(cursor.getString(cursor.getColumnIndex(db.KEY_FIRSTNAME)));
        lastName.setText(cursor.getString(cursor.getColumnIndex(db.KEY_LASTNAME)));
        phoneNr.setText(cursor.getString(cursor.getColumnIndex(db.KEY_PH_NO)));
        birthday.setText(new StringBuilder().append(cursor.getInt(cursor.getColumnIndex(db.KEY_DAY))).append("-").append(cursor.getInt(cursor.getColumnIndex(db.KEY_MONTH))).append("-")
        .append(cursor.getInt(cursor.getColumnIndex(db.KEY_YEAR))));
        db.close();
    }

    @Override
    public	boolean onCreateOptionsMenu(Menu menu)	{
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.display_menu,	menu);
        return	true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delContact:
                new AlertDialog.Builder(DisplayActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.warning)
                        .setMessage(R.string.deletePerson)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.reopen();
                                db.deletePerson(personId);
                                Intent intent = new Intent(DisplayActivity.this, StartActivity.class);
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
                Intent intent  = new Intent(DisplayActivity.this,StartActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.action_change:
                Intent intent2 = new Intent(DisplayActivity.this, ChangeActivity.class);
                intent2.putExtra("ID", personId);
                startActivity(intent2);
                break;
            default:
                break;
        }
        return	true;
    }

    public void initWidgets() {
        firstName = (TextView) findViewById(R.id.FirstNameTV);
        lastName = (TextView) findViewById(R.id.LastNameTV);
        phoneNr = (TextView) findViewById(R.id.phoneNrTV);
        birthday = (TextView) findViewById(R.id.birtdayView);
    }
}
