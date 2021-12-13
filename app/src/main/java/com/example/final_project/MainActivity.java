package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener  {

        // experimental values for hi and lo magnitude limits
        private final double NORTH_MOVE_FORWARD = 9.0;     // upper mag limit
        private final double NORTH_MOVE_BACKWARD = 6.0;      // lower mag limit
        boolean highLimit = false;      // detect high limit
        int counter = 0;                // step counter


        TextView  tvSteps;
        private SensorManager mSensorManager;
        private Sensor mSensor;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            // we are going to use the sensor service
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            EditText myTextBox = (EditText) findViewById(R.id.userName);
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







    /*
         * When the app is brought to the foreground - using app on screen
         */
        protected void onResume() {
            super.onResume();
            // turn on the sensor
            mSensorManager.registerListener(this, mSensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        /*
         * App running but not on screen - in the background
         */
        protected void onPause() {
            super.onPause();
            mSensorManager.unregisterListener(this);    // turn off listener to save power
        }


        @Override
        public void onSensorChanged(SensorEvent event) {

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];



            // Can we get a north movement
            // you need to do your own mag calculating
            if ((x > NORTH_MOVE_FORWARD) && (highLimit == false)) {
                highLimit = true;
            }
            if ((x < NORTH_MOVE_BACKWARD) && (highLimit == true)) {
                // we have a tilt to the north
                counter++;
                tvSteps.setText(String.valueOf(counter));
                highLimit = false;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // not used
        }

        public static double round(double value, int places) {
            if (places < 0) throw new IllegalArgumentException();

            long factor = (long) Math.pow(10, places);
            value = value * factor;
            long tmp = Math.round(value);
            return (double) tmp / factor;
        }

    public void goToSequence(View view) {
        Intent intent = new Intent(MainActivity.this, sequenceScreen.class);
        startActivity(intent);

    }
}
