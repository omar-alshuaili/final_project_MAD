package com.example.final_project;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class sequenceScreen extends AppCompatActivity {
    private Button color1;
    private Button color2;
    private Button color3;
    private Button color4;

    private final int b1 = 1;
    private final int b2 = 2;
    private final int b3 = 3;
    private final int b4 = 4;
    int[] colorArray = new int[4];
    int[] gameSequence = new int[120];
    int arrayIndex = 0;
    int sequenceCount = 4, n = 0;
    int c = 4;
    Animation anim;
    Button goPlayGround;
    Button sqBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sequence);
        getSupportActionBar().hide();
        for (int i = 0; i < 4; i++) {
            Random r = new Random();
            int red = r.nextInt(256);
            int green = r.nextInt(256);
            int blue = r.nextInt(256);
            int color = Color.rgb(red, green, blue);
            final int finalColor = color;
            boolean contains = IntStream.of(colorArray).anyMatch(x -> x == finalColor);
            if (contains) {
                r = new Random();
                red = r.nextInt(256);
                green = r.nextInt(256);
                blue = r.nextInt(256);
                color = Color.rgb(red, green, blue);
            }
            colorArray[i] = color;
        }
        color1 = findViewById(R.id.color1);
        color2 = findViewById(R.id.color2);
        color3 = findViewById(R.id.color3);
        color4 = findViewById(R.id.color4);

        color1.setBackgroundColor(colorArray[0]);
        color2.setBackgroundColor(colorArray[1]);
        color3.setBackgroundColor(colorArray[2]);
        color4.setBackgroundColor(colorArray[3]);

        goPlayGround = findViewById(R.id.goPlayGround);
        sqBtn = findViewById(R.id.startSq);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                sqBtn.setEnabled(true);
                goPlayGround.setEnabled(false);

                sequenceCount+=2;
                c = sequenceCount;
                Arrays.fill(gameSequence,0);
            }

        }
    }



    CountDownTimer ct = new CountDownTimer(sequenceCount*1500, 1500) {

        public void onTick(long millisUntilFinished) {
            
            oneButton();
        }

        @Override
        public void onFinish() {
            goPlayGround.setEnabled(true);

        }
    };

    private void oneButton() {
        Log.i(TAG,"sequenceCount: "+String.valueOf(sequenceCount));
        Log.i(TAG,"g: "+String.valueOf(sequenceCount*1500));
        String[] buttons = {"color1","color2","color3","color4"};

        int rnd = new Random().nextInt(buttons.length);

        String button = buttons[rnd];



        switch (button) {
            case "color1":
                flashButton(color1);
                gameSequence[arrayIndex++] = b1;
                break;

            case "color2":
                flashButton(color2);
                gameSequence[arrayIndex++] = b2;
                break;
            case "color3":
                flashButton(color3);
                gameSequence[arrayIndex++] = b3;
                break;
            case "color4":
                flashButton(color4);
                gameSequence[arrayIndex++] = b4;
                break;
            default:
                break;
        }   // end switch
    }

    private void flashButton(Button button) {

        anim = new AlphaAnimation(1, 0);
        anim.setDuration(1000); //You can manage the blinking time with this parameter

        anim.setRepeatCount(0);
        button.startAnimation(anim);

    }

    private int getRandom(int maxValue) {

        return ((int) ((Math.random() * maxValue) + 1));
    }


    public void startSq(View view) {
        ct.start();
        sqBtn.setEnabled(false);

    }
    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
    }


    public void Play(View view) {
        Intent intent = new Intent(sequenceScreen.this, playGround.class);
        intent.putExtra("colorArray", colorArray);
        intent.putExtra("gameSequence", gameSequence);
        intent.putExtra("sequenceCount", sequenceCount);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivityForResult(intent, 1);
    }
}

