package com.example.ssandoy.s236305_mappe2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class StartActivity extends AppCompatActivity {

    DBHandler db;

    Button registerButton; //TODO: MAKE ALL PRIVATE
    Button contactsButton;
    Button messageButton; //TODO: ADD exitButton

    @Override
    protected void onCreate(Bundle savedInstanceState) { //TODO: INIT Message her og sett melding til gratulerer?
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initWidgets();

        db = new DBHandler(this);
        db.open();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, RegActivity.class);
                startActivity(intent);
            }
        });

        contactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, ContactsActivity.class);
                startActivity(intent);
            }
        });

        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, MessageActivity.class);
                startActivity(intent);
            }
        });



    }

    public void initWidgets() {
        registerButton = (Button) findViewById(R.id.register);
        contactsButton = (Button) findViewById(R.id.contactsButton);
        messageButton = (Button) findViewById(R.id.messageButton);
    }

    public List<Person> allContacts(List<Person> persons) { //TODO: TRENGS IKKE HER?
        Cursor cursor = db.findAll();
        if	(cursor.moveToFirst())	{
            do{
                Person person	= new Person();
                person.set_ID(cursor.getInt(0));
                person.setFirstName(cursor.getString(1));
                person.setLastName(cursor.getString(2));
                person.setPhoneNumber(cursor.getString(3));
                person.setBirthday(db.setPersonBirthDate(cursor.getInt(4), cursor.getInt(5), cursor.getInt(6)));
                persons.add(person);
            }
            while	(cursor.moveToNext());
        }
        cursor.close();
        return persons;
    }
}
