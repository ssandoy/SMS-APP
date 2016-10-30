package com.example.ssandoy.s236305_mappe2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    DBHandler db;

    private EditText searchText;

    private ArrayList<Person> contacts;

    private ListView contactsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");


        initWidgets();

        db = new DBHandler(this);

        contacts = allContacts();

        final PersonAdapter personAdapter = new PersonAdapter(this, contacts);

        contactsView.setAdapter(personAdapter);


        createListeners(personAdapter);




    }

    @Override
    public	boolean onCreateOptionsMenu(Menu menu)	{
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,	menu);
        return	true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch	(item.getItemId())	{
           case	R.id.action_regContact:
               Intent i  = new Intent(StartActivity.this,RegActivity.class);
               startActivity(i);
               break;
            case R.id.action_settings:
                Intent intent2  = new Intent(StartActivity.this,MessageActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
        return	true;
    }

    public void initWidgets() {
        contactsView = (ListView) findViewById(R.id.contactsView);
        searchText = (EditText) findViewById(R.id.searchText);
    }

    public void createListeners(final PersonAdapter personAdapter) {

        contactsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Intent intent = new Intent(StartActivity.this, DisplayActivity.class);
                int PID = personAdapter.getPersonID(position);
                intent.putExtra("ID", PID);
                startActivity(intent);
            }
        });

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                personAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                personAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                personAdapter.getFilter().filter(s.toString());
            }
        });
    }

    public ArrayList<Person> allContacts() {
        db.open();
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
        db.close();
        return persons;
    }
}
