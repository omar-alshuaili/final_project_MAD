package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameOver extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        listView = findViewById(R.id.lv);

        DatabaseHandler db = new DatabaseHandler(this);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        // Reading all scores
        List<HighScore> HighScores = db.getAllHighScores();
        String name = getIntent().getStringExtra("name");
        int score = getIntent().getIntExtra("score",0);
        db.addHighScore(new HighScore(dtf.format(now).toString(),name,score));



        for (HighScore hs : HighScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

            // Writing HighScore to log
        }




        // Calling SQL statement
        List<HighScore> top5HighScores = db.getTopFiveScores();


        HighScore HighScore = top5HighScores.get(top5HighScores.size()-1);
        // HighScore contains the 5th highest score

        // simple test to add a hi score
        int myCurrentScore = 40;
        // if 5th highest score < myCurrentScore, then insert new score
        if (HighScore.getScore() < myCurrentScore) {
            db.addHighScore(new HighScore("08 DEC 2020", "Elrond", 40));
        }


        // Calling SQL statement
        top5HighScores = db.getTopFiveScores();
        List<String> scoresStr;
        scoresStr = new ArrayList<>();

        int j = 1;
        for (HighScore hs : top5HighScores) {

            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

            // store score in string array
            scoresStr.add(j++ + " : "  +
                    hs.getPlayer_name() + "\t" +
                    hs.getScore());
            // Writing HighScore to log
        }




        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, scoresStr);
        listView.setAdapter(itemsAdapter);

    }

    public void goHome(View view) {
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);

    }
}