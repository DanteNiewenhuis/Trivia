package com.example.dante.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class DetailscoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailscore);

        Intent intent = getIntent();

        List questions = (List<Question>) intent.getSerializableExtra("questions");

        ListView question_list = findViewById(R.id.question_list);

        question_list.setAdapter(new DetailAdapter(this, R.layout.question_item, questions));
    }
}
