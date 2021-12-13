package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Random;
import java.util.stream.IntStream;

public class sequenceScreen extends AppCompatActivity {
    public Button color1;
    public Button color2;
    public Button color3;
    public Button color4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sequence);
        getSupportActionBar().hide();
        int[] colorArray  = new int[4];
        for (int i = 0; i<4; i++){
            Random r = new Random();
            int red = r.nextInt(256);
            int green = r.nextInt(256);
            int blue = r.nextInt(256);
            int color = Color.rgb(red, green, blue);
            final int finalColor = color;
            boolean contains = IntStream.of(colorArray).anyMatch(x -> x == finalColor);
            if ( contains){
                 r = new Random();
                 red = r.nextInt(256);
                 green = r.nextInt(256);
                 blue = r.nextInt(256);
                 color = Color.rgb(red, green, blue);
            }
            colorArray[i] = color;
        }
        color1 = findViewById(R.id.colo1);
        color2 = findViewById(R.id.color2);
        color3 = findViewById(R.id.color3);
        color4 = findViewById(R.id.color4);

        color1.setBackgroundColor(colorArray[0]);
        color2.setBackgroundColor(colorArray[1]);
        color3.setBackgroundColor(colorArray[2]);
        color4.setBackgroundColor(colorArray[3]);

    }



    public void goPlayGround(View view) {
        Intent intent = new Intent(sequenceScreen.this, playGround.class);
        startActivity(intent);
    }
}