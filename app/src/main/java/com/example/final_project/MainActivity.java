package com.example.final_project;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainActivity extends AppCompatActivity   {

    EditText myTextBox;




        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            myTextBox = (EditText) findViewById(R.id.userName);
            Button Go = (Button) findViewById(R.id.Go);

            myTextBox.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    Go.setEnabled(true);

                }
            });

        }







    public void goToSequence(View view) {
        String name =  myTextBox.getText().toString();
        DatabaseHandler db = new DatabaseHandler(this);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        db.addHighScore(new HighScore(dtf.format(now).toString(),name,0));


        Intent intent = new Intent(MainActivity.this, sequenceScreen.class);
        startActivity(intent);

    }
}
