package com.example.ssandoy.s236305_mappe2;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by ssandoy on 28.10.2016.
 */
public class MessageFragment extends Fragment {

    private TimePicker time;

    private Button confirmButton;
    private Button confirmIcon;
    private CheckBox onoffBox;

    private EditText messageText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_message, container, false);

        initWidgets(v);

        Log.d("MessageActivity", "Er i ONCREATE");
        createListeners();

        onoffBox.setChecked(PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).getBoolean("onoff",false));
        if(PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).getBoolean("onoff",false))
            onoffBox.setTextColor(Color.GREEN);
        else
            onoffBox.setTextColor(Color.RED);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        onoffBox.setChecked(PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).getBoolean("onoff",false));
    }

    public void initWidgets(View v) {
        time = (TimePicker) v.findViewById(R.id.timePicker);
        confirmButton = (Button) v.findViewById(R.id.confirmButton);
        confirmIcon = (Button) v.findViewById(R.id.confirmIcon);
        onoffBox = (CheckBox) v.findViewById(R.id.OnOffCheckBox);
        messageText = (EditText) v.findViewById(R.id.messageText);

        time.setIs24HourView(true);
        int hour = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).getInt("hour", 1);
        int min =  PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).getInt("minute", 1);
        time.setCurrentHour(hour);
        time.setCurrentMinute(min);

        String msgText = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).getString("message","");
        Log.d("MESSAGE", msgText);
        messageText.setText(msgText);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageText.getText().toString();
                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putString("message", message).commit();

                int hour = time.getCurrentHour() == 0 ? 24 : time.getCurrentHour();
                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putInt("hour", hour).commit();
                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putInt("minute", time.getCurrentMinute()).commit();

                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putBoolean("onoff", onoffBox.isChecked()).commit();

                Toast.makeText(getActivity().getApplicationContext(), "Time and message confirmed.", Toast.LENGTH_LONG).show();

            }
        });

        confirmIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageText.getText().toString();
                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putString("message", message).commit();

                int hour = time.getCurrentHour() == 0 ? 24 : time.getCurrentHour();
                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putInt("hour", hour).commit();
                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putInt("minute", time.getCurrentMinute()).commit();

                PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putBoolean("onoff", onoffBox.isChecked()).commit();
                Toast.makeText(getActivity().getApplicationContext(), "Time and message confirmed.", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void createListeners() {

        onoffBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox) v).isChecked()) {
                    PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putBoolean("onoff", onoffBox.isChecked()).commit();
                    onoffBox.setTextColor(Color.GREEN);
                    Intent i = new Intent(getActivity(), SendMessagePeriodicService.class);
                    getActivity().startService(i);
                } else {
                    PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putBoolean("onoff", onoffBox.isChecked()).commit();
                    onoffBox.setTextColor(Color.RED);
                    Intent i = new Intent(getActivity(), SendMessagePeriodicService.class);
                    getActivity().stopService(i);
                }
            }
        });

        //TODO: ON RESTORE AND SAVE

    }
}
