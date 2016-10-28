package com.example.ssandoy.s236305_mappe2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Created by ssandoy on 28.10.2016.
 */
public class ChangeFragment extends Fragment {

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
    private Button deleteButton;
    private Button deleteIcon;

    private AlertDialog alertDialog;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_change, container, false);
        LanguageSelector.loadLanguage(getActivity());
        db = new DBHandler(getActivity());
        db.open();
        personId = getArguments().getInt("PID");
        initWidgets(v);
        Cursor cursor = db.findPersonByID(personId);
        cursor.moveToFirst();
        firstName.setText(cursor.getString(cursor.getColumnIndex(db.KEY_FIRSTNAME)));
        lastName.setText(cursor.getString(cursor.getColumnIndex(db.KEY_LASTNAME)));
        phoneNr.setText(cursor.getString(cursor.getColumnIndex(db.KEY_PH_NO)));
        birthday.updateDate(cursor.getInt(cursor.getColumnIndex(db.KEY_YEAR)),cursor.getInt(cursor.getColumnIndex(db.KEY_MONTH))-1, cursor.getInt(cursor.getColumnIndex(db.KEY_DAY)));

        createListeners();

        return v;
    }

    public void initWidgets(View v) {
        firstName = (EditText) v.findViewById(R.id.FirstNameET);
        lastName = (EditText) v.findViewById(R.id.LastNameET);
        phoneNr = (EditText) v.findViewById(R.id.phoneNrET);
        birthday = (DatePicker) v.findViewById(R.id.birthDatePicker);
        submitButton = (Button) v.findViewById(R.id.saveButton);
        submitIcon = (Button) v.findViewById(R.id.submitIcon);
        deleteButton = (Button) v.findViewById(R.id.deleteButton);
        deleteIcon = (Button) v.findViewById(R.id.deleteIcon);

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
                final int month = birthday.getMonth();
                final int year = birthday.getYear();

                if (name.matcher(fName).matches() && name.matcher(lName).matches() && phone.matcher(phNr).matches()) {
                    alertDialog = new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(R.string.warning)
                            .setMessage(R.string.updatePerson)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.update(fName, lName, phNr, year, month, day, personId);
                                    FragmentManager FM = getFragmentManager();
                                    FragmentTransaction FT = FM.beginTransaction();

                                    ContactsFragment RF = new ContactsFragment();
                                    FT.add(R.id.fragmentContent, RF);
                                    FT.addToBackStack("f2");
                                    FT.commit();
                                }
                            })
                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();

                } else {

                    new AlertDialog.Builder(getActivity()).setTitle("Error").setMessage("Feil input").show(); //TODO FIKS BEDRE MELDING
                }
                db.close();
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
                final int month = birthday.getMonth();
                final int year = birthday.getYear();

                if (name.matcher(fName).matches() && name.matcher(lName).matches() && phone.matcher(phNr).matches()) {
                    alertDialog = new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(R.string.warning)
                            .setMessage(R.string.updatePerson)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.update(fName, lName, phNr, year, month, day, personId);
                                    FragmentManager FM = getFragmentManager();
                                    FragmentTransaction FT = FM.beginTransaction();

                                    ContactsFragment RF = new ContactsFragment();
                                    FT.add(R.id.fragmentContent, RF);
                                    FT.addToBackStack("f2");
                                    FT.commit();
                                }
                            })
                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();

                } else {

                    new AlertDialog.Builder(getActivity()).setTitle("Error").setMessage("Feil input").show();
                }
                db.close();
            }
        });



        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.reopen();
                alertDialog = new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.warning)
                        .setMessage(R.string.deletePerson)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.deletePerson(personId);
                                FragmentManager FM = getFragmentManager();
                                FragmentTransaction FT = FM.beginTransaction();

                                ContactsFragment RF = new ContactsFragment();
                                FT.add(R.id.fragmentContent, RF);
                                FT.addToBackStack("f2");
                                FT.commit();
                            }
                        })
                        .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                db.close();
            }
        });


        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.reopen();
                alertDialog = new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.warning)
                        .setMessage(R.string.deletePerson)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.deletePerson(personId);
                                FragmentManager FM = getFragmentManager();
                                FragmentTransaction FT = FM.beginTransaction();

                                ContactsFragment RF = new ContactsFragment();
                                FT.add(R.id.fragmentContent, RF);
                                FT.addToBackStack("f2");
                                FT.commit();
                            }
                        })
                        .setNegativeButton(R.string.no,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
                db.close();
            }
        });

    }

    //TODO: ON RESTORE AND SAVE

}
