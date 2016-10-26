package com.example.ssandoy.s236305_mappe2;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TimePicker;

public class MessageActivity extends AppCompatActivity {



    private TimePicker time;

    private Button returnButton;
    private Button confirmButton;
    private CheckBox onoffBox;

    private EditText messageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initWidgets();

        Log.d("MessageActivity", "Er i ONCREATE");
        createListeners();

        onoffBox.setChecked(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("onoff",false));
    }

    @Override
    protected void onResume() {
        super.onResume();
        initWidgets();
        onoffBox.setChecked(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("onoff",false));
    }

    public void initWidgets() {
        time = (TimePicker) findViewById(R.id.timePicker);
        returnButton = (Button) findViewById(R.id.returnButton);
        confirmButton = (Button) findViewById(R.id.confirmButton);
        onoffBox = (CheckBox) findViewById(R.id.OnOffCheckBox);
        messageText = (EditText) findViewById(R.id.messageText);

        time.setIs24HourView(true);
        int hour = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("hour", 1);
        int min =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("minute", 1);
        time.setCurrentHour(hour);
        time.setCurrentMinute(min);

        String msgText = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("message","");
        Log.d("MESSAGE", msgText);
        messageText.setText(msgText);

    }

    public void createListeners() {
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessageActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

        onoffBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Intent i = new Intent(MessageActivity.this, SendMessagePeriodicService.class);
                    startService(i);
                } else {
                    Intent i = new Intent(MessageActivity.this, SendMessagePeriodicService.class);
                    stopService(i);
                }
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageText.getText().toString();
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("message", message).commit();

                int hour = time.getCurrentHour() == 0 ? 24 : time.getCurrentHour();
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt("hour", hour).commit(); //TODO: APPLY VS COMMIT()
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putInt("minute", time.getCurrentMinute()).commit();

                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putBoolean("onoff", onoffBox.isChecked()).commit();

                finish();

            }
        });
    }
}
