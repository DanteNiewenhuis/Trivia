package com.example.dante.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void play(View v) {
        EditText name_input = findViewById(R.id.name_input);
        String name = name_input.getText().toString();

        if (name.length() == 0) {
            TextView warning = findViewById(R.id.warning_text);
            warning.setVisibility(View.VISIBLE);
        }
        else {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra("name", name);
            startActivity(intent);
        }
    }

    public void highscores(View v) {
        Intent intent = new Intent(this, HighscoreActivity.class);
        startActivity(intent);
    }
}
