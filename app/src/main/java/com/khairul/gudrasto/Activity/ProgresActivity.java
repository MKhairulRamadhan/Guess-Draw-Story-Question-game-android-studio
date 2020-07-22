package com.khairul.gudrasto.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.khairul.gudrasto.Helper.SharePrefManager;
import com.khairul.gudrasto.R;

public class ProgresActivity extends AppCompatActivity {
    private SharePrefManager sharePrefManager;
    private TextView storyPointBest, guessPointBest,  storyPointLast, guessPointLast;

    private String storyBest, guessBest,  storyLast, guessLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progres);

        sharePrefManager = new SharePrefManager(this);

        storyPointBest = findViewById(R.id.storyPointBest);
        guessPointBest = findViewById(R.id.guessPointBest);
        storyPointLast = findViewById(R.id.storyPointLast);
        guessPointLast = findViewById(R.id.GuessPointLast);

        storyBest = sharePrefManager.getSpStoryBest();
        guessBest = sharePrefManager.getSpGuessBest();
        storyLast = sharePrefManager.getSpStoryWeek();
        guessLast = sharePrefManager.getSpGuessWeek();

        storyPointBest.setText(storyBest);
        storyPointLast.setText(storyLast);
        guessPointBest.setText(guessBest);
        guessPointLast.setText(guessLast);

    }
}
