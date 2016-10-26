package com.example.ssandoy.s236305_mappe2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;



public class ContactsActivity extends AppCompatActivity {

    DBHandler db;

    ListView contactsView;
    Button returnButton;

    private ArrayList<Person> contacts; //TODO: IMPLEMENT LISTFRAGMENT!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        initWidgets();

        db = new DBHandler(this);
        db.open();

        contacts = allContacts();

        final PersonAdapter personAdapter = new PersonAdapter(this, contacts);

        contactsView.setAdapter(personAdapter);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactsActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

        contactsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Intent intent = new Intent(ContactsActivity.this, ChangeActivity.class);
                int PID = personAdapter.getPersonID(position);
                intent.putExtra("ID", PID);
                startActivity(intent);
            }
        });

    }

    public void initWidgets() {
        contactsView = (ListView) findViewById(R.id.contactsView);
        returnButton = (Button) findViewById(R.id.returnButton);
    }

    public ArrayList<Person> allContacts() {
        ArrayList<Person> persons = new ArrayList<>();
        Cursor cursor = db.findAll();
        if	(cursor.moveToFirst())	{
            do{
                Person person	= new Person();
                person.set_ID(cursor.getInt(0));
                person.setFirstName(cursor.getString(1));
                person.setLastName(cursor.getString(2));
                person.setPhoneNumber(cursor.getString(3));
                person.setBirthday(db.setPersonBirthDate(cursor.getInt(4), cursor.getInt(5) -1, cursor.getInt(6)));
                persons.add(person);
            }
            while	(cursor.moveToNext());
        }
        cursor.close();
        return persons;
    }
}
