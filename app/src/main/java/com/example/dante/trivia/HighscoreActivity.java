package com.example.dante.trivia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HighscoreActivity extends AppCompatActivity implements HighscoresHelper.Callback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        new HighscoresHelper(this).getHighscores(this);
    }

    @Override
    public void gotHighscores(ArrayList<Highscore> highscores) {
        ListView list_view = findViewById(R.id.highscore_list);
        list_view.setAdapter(new HighscoreAdapter(this, R.layout.highscore_item, highscores));
        list_view.setOnItemClickListener(new highscoreClickListener());
    }

    @Override
    public void gotHighscoresError(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    private class highscoreClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Highscore item = (Highscore) adapterView.getItemAtPosition(i);
            ArrayList<Question> questions = (ArrayList) item.getQuestions();

            Intent intent = new Intent(HighscoreActivity.this, DetailscoreActivity.class);
            intent.putExtra("questions", questions);

            startActivity(intent);

        }
    }
}
