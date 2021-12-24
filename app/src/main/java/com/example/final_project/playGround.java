package com.example.final_project;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class playGround extends AppCompatActivity implements SensorEventListener {
    private Button color1;
    private Button color2;
    private Button color3;
    private Button color4;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    int[] sq;
    boolean correct = false;
    int current =0;
    int[] array;
    TextView tvDir;
    int round;
    int gameRound;

    boolean choosing = false;
    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playground);
        getSupportActionBar().hide();

        // we are going to use the sensor service
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        tvDir = findViewById(R.id.t);

        int[] color = getIntent().getIntArrayExtra("colorArray");
        sq = getIntent().getIntArrayExtra("gameSequence");

        Bundle bundle = getIntent().getExtras();
        round = bundle.getInt("sequenceCount");

        color1 = findViewById(R.id.SequenceColo1);
        color2 = findViewById(R.id.SequenceColo2);
        color3 = findViewById(R.id.SequenceColo3);
        color4 = findViewById(R.id.SequenceColo4);
        color1.setBackgroundColor(color[0]);
        color2.setBackgroundColor(color[1]);
        color3.setBackgroundColor(color[2]);
        color4.setBackgroundColor(color[3]);

        array = new int[sq.length];

        gameRound = 4;
    }






    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        if(round == 0){
            Arrays.fill(sq, 0);
            Arrays.fill(array, 0);
            current = 0;

            tvDir.setText("You Got it!! next round");
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    choosing = false;

                    gameRound = gameRound + 2;
                    Intent returnIntent = new Intent();
                    returnIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    returnIntent.putExtra("result",gameRound);
                    setResult(Activity.RESULT_OK,returnIntent);

                    finish();
                }
            }, 2000); // Millisecond 1000 = 1 sec

        }

            if (x < -0.8f & !choosing) {

                check();
                array[current] = 2;
                checkIfCorrect();

                current ++;

                if(!correct){
                    Intent M = new Intent(playGround.this, GameOver.class);
                    startActivity(M);
                }



            }

            else if (x > 0.8f & !choosing ) {

                check();
                array[current] = 4;
                checkIfCorrect();

                current ++;
                if(!correct){
                    Intent M = new Intent(playGround.this, GameOver.class);
                    startActivity(M);
                }


            }

            if (y > 0.35f & !choosing ) {

                check();
                array[current] = 3;
                checkIfCorrect();

                current ++;

                if(!correct){

                    Intent M = new Intent(playGround.this, GameOver.class);
                    M.putExtra("score",gameRound);
                    startActivity(M);
                }


            }

            else if (y < -0.24f & !choosing ) {

                check();
                array[current] = 1;
                checkIfCorrect();
                current ++;

                if(!correct){
                    Intent M = new Intent(playGround.this, GameOver.class);
                    startActivity(M);
                }

            }








    }





    public void check(){

        choosing = true;


        new CountDownTimer(2000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvDir.setText("Your choice will be saved in.." + millisUntilFinished / 1000);
            }

            public void onFinish() {
                tvDir.setText("Please choose the next sequence");

                round--;

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        choosing = false;

                    }
                }, 2000); // Millisecond 1000 = 1 sec

            }
        }.start();



    }
    public void checkIfCorrect(){

        for(int i =0; i < array.length;i++){
            if(array[i] !=0) {


                if (array[i] == sq[i]) {
                    correct = true;


                } else {
                    Log.i(TAG,"was false");



                    correct = false;
                }
            }
            else {
                continue;
            }
        }
    }




    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

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
        mSensorManager.unregisterListener(this);    // turn off listener to save power    }
    }
}