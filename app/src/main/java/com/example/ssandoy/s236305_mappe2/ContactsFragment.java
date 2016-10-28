package com.example.ssandoy.s236305_mappe2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by ssandoy on 28.10.2016.
 */
public class ContactsFragment  extends Fragment {

    DBHandler db;

    private ListView contactsView;

    private EditText searchText;

    private ArrayList<Person> contacts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_contacts, container, false);
        initWidgets(v);
        LanguageSelector.loadLanguage(getActivity());
        db = new DBHandler(getActivity());


        contacts = allContacts();

        final PersonAdapter personAdapter = new PersonAdapter(getActivity(), contacts);


        contactsView.setAdapter(personAdapter);
        createListeners(personAdapter);
        return v;
    }

  /*  @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("search", searchText.getText().toString());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String search = savedInstanceState.getString("search") == null ? "" : savedInstanceState.getString("search");
        searchText.setText(search);

    }*/ //TODO: FIX

    public void initWidgets(View v) {
        contactsView = (ListView) v.findViewById(R.id.contactsView);
        searchText = (EditText) v.findViewById(R.id.searchText);

    }

    public void createListeners(final PersonAdapter personAdapter) {



        contactsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                int PID = personAdapter.getPersonID(position);
               // Intent intent = new Intent(getActivity(), ChangeActivity.class);
                FragmentManager FM = getFragmentManager();
                FragmentTransaction FT = FM.beginTransaction();

                ChangeFragment RF = new ChangeFragment();
                Bundle args = new Bundle();
                args.putInt("PID", PID);
                RF.setArguments(args);
                FT.add(R.id.fragmentContent, RF);
                FT.addToBackStack("f3");
                FT.commit();

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

    //TODO: ON RESTORE AND SAVE


}
