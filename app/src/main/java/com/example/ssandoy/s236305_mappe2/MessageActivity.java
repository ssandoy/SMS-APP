package com.example.ssandoy.s236305_mappe2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

public class MessageActivity extends AppCompatActivity {

    private TimePicker time;

    private Button returnButton;
    private Button confirmButton;
    private Button confirmIcon;
    private Switch onoffBox;

    private EditText messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        initWidgets();
        createListeners();

        onoffBox.setChecked(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("onoff",false));
        if(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("onoff",false))
                onoffBox.setTextColor(Color.GREEN);
        else
            onoffBox.setTextColor(Color.RED);

    }

    @Override
    protected void onResume() {
        super.onResume();
        onoffBox.setChecked(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("onoff",false));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("Message", messageText.getText().toString());
        savedInstanceState.putInt("hour", time.getCurrentHour());
        savedInstanceState.putInt("minute", time.getCurrentMinute());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String message = savedInstanceState.getString("Message");
        int hour = savedInstanceState.getInt("hour");
        int minute = savedInstanceState.getInt("minute");
        messageText.setText(message);
        time.setCurrentHour(hour);
        time.setCurrentMinute(minute);

    }



    public void initWidgets() {
        time = (TimePicker) findViewById(R.id.timePicker);
        returnButton = (Button) findViewById(R.id.exitButton);
        confirmButton = (Button) findViewById(R.id.confirmButton);
        confirmIcon = (Button) findViewById(R.id.confirmIcon);
        onoffBox = (Switch) findViewById(R.id.OnOffCheckBox);
        messageText = (EditText) findViewById(R.id.messageText);

        time.setIs24HourView(true);
        int hour = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("hour", 1);
        int min =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("minute", 1);
        time.setCurrentHour(hour);
        time.setCurrentMinute(min);

        String msgText = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("message","");
        messageText.setText("");
        messageText.append(msgText);


    }

    public void createListeners() {
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });



        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageText.getText().toString();
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("message", message).commit();

                time.clearFocus();

                int hour = time.getCurrentHour() == 0 ? 24 : time.getCurrentHour();
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt("hour", hour).commit();
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt("minute", time.getCurrentMinute()).commit();

                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("onoff", onoffBox.isChecked()).commit();

                finish();
                Intent intent = new Intent(MessageActivity.this, SendMessagePeriodicService.class);
                startService(intent);
                Intent i = new Intent(MessageActivity.this, StartActivity.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), R.string.TimeAndMessage, Toast.LENGTH_LONG).show();

            }
        });

        confirmIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageText.getText().toString();
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("message", message).commit();

                int hour = time.getCurrentHour() == 0 ? 24 : time.getCurrentHour();
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt("hour", hour).commit();
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt("minute", time.getCurrentMinute()).commit();

                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("onoff", onoffBox.isChecked()).commit();

                finish();
                Intent intent = new Intent(MessageActivity.this, SendMessagePeriodicService.class);
                startService(intent);
                Intent i = new Intent(MessageActivity.this, StartActivity.class);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Time and message confirmed.", Toast.LENGTH_LONG).show();

            }
        });

        onoffBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("onoff", onoffBox.isChecked()).commit();
                    onoffBox.setTextColor(Color.GREEN);
                    Intent i = new Intent(MessageActivity.this, SendMessagePeriodicService.class);
                    startService(i);
                }else {
                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("onoff", onoffBox.isChecked()).commit();
                    onoffBox.setTextColor(Color.RED);
                    Intent i = new Intent(MessageActivity.this, SendMessagePeriodicService.class);
                    stopService(i);
                }
            }
        });

    }
}
