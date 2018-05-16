package com.example.dante.trivia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class HighscoreActivity extends AppCompatActivity implements HighscoresHelper.Callback{
    private HighscoresHelper highscores_request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        highscores_request = new HighscoresHelper(this);
        highscores_request.getHighscores(this);
    }

    @Override
    public void gotHighscores(ArrayList<Highscore> highscores) {
        ListView list_view = findViewById(R.id.highscore_list);
        list_view.setAdapter(new HighscoreAdapter(this, R.layout.highscore_item, highscores));
    }

    @Override
    public void gotHighscoresError(String message) {

    }
}
