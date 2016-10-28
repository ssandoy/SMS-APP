package com.example.ssandoy.s236305_mappe2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by ssandoy on 28.10.2016.
 */
public class LanguageFragment extends Fragment {

    private ImageButton noButton;
    private ImageButton enButton;
    private ImageButton returnArrow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LanguageSelector.loadLanguage(getActivity());
        View v = inflater.inflate(R.layout.activity_language, container, false);
        noButton = (ImageButton) v.findViewById(R.id.noButton);
        enButton = (ImageButton) v.findViewById(R.id.enButton);
        returnArrow = (ImageButton) v.findViewById(R.id.returnArrow);
        createListeners();
        return v;
    }


    public void createListeners() {

    enButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setLanguage("en");

        }
    });

    noButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setLanguage("no");
        }
    });


    }

    public void setLanguage(String lang) {

        LanguageSelector.changeLanguage(getActivity(), lang);
        FragmentManager FM = getFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();

        ContactsFragment RF = new ContactsFragment();
        FT.add(R.id.fragmentContent, RF);
        FT.addToBackStack("f2");
        FT.commit();

    }
}
