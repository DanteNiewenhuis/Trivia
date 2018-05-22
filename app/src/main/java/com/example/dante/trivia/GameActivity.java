package com.example.dante.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameActivity extends AppCompatActivity implements TriviaHelper.Callback{
    private TriviaHelper question_request;
    private Question current_question;
    private Highscore current_game;
    private boolean first_try;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        question_request = new TriviaHelper(this);

        if (savedInstanceState != null) {
            current_question = (Question) savedInstanceState.getSerializable("current_question");
            current_game = (Highscore) savedInstanceState.getSerializable("current_game");
            updateGame();
        }
        else {
            Intent intent = getIntent();
            String name = (String) intent.getSerializableExtra("name");

            String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            current_game = new Highscore(name, date);
            question_request.getNextQuestion(this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("current_game", current_game);
        outState.putSerializable("current_question", current_question);
    }

    @Override
    public void gotQuestion(Question question) {
        current_question = question;
        first_try = true;
        updateGame();
    }

    private void updateGame() {
        // get all the TextViews
        TextView question_view = findViewById(R.id.question_View);
        TextView index_view = findViewById(R.id.index_view);
        TextView correct_view = findViewById(R.id.correct_view);
        TextView score_view = findViewById(R.id.detail_score_view);

        // update the text
        index_view.setText("question " + current_game.getQuestions_answered());
        correct_view.setText(current_game.getQuestions_correct() + " correct");
        score_view.setText("score: " + current_game.getScore());
        question_view.setText(current_question.getQuestion());

        // get the answers and set all the buttons to an answer
        List<String> answers = current_question.getAnswers();

        for (int i = 0; i < 4; i++) {
            int id = getResources().getIdentifier("answer_" + i + "_button", "id", getPackageName());
            RadioButton button = findViewById(id);

            button.setText(answers.get(i));
        }
    }

    @Override
    public void gotQuestionError(String message) {
        if (message.equals("AGAIN")) {
            question_request.getNextQuestion(this);
        }
        else {
            Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void submit(View v){
        // get the answer from the chosen answer to the radio group
        RadioGroup answers = findViewById(R.id.answers_group);
        int checked_id = answers.getCheckedRadioButtonId();
        RadioButton checked = findViewById(checked_id);
        current_question.setGivenAnswer(checked.getText().toString());

        // get the correct answer and compare the answers.
        TextView response_text = findViewById(R.id.response_text);
        String correct_answer = current_question.getCorrectAnswer();
        if (current_question.goodAnswer()) {
            response_text.setText("good job!!!!!!!");
            current_game.increase_correct();

            if (first_try) {
                current_game.increase_score(current_question.getValue());
                current_question.setPoints_gained(current_question.getValue());
            }
            else {
                current_game.increase_score(current_question.getValue()/2);
                current_question.setPoints_gained(current_question.getValue()/2);
            }
        }
        else {
            if (first_try) {
                response_text.setText("That was not the right answer, Try again");
                first_try = false;
                return;
            }

            else {
                response_text.setText("the correct answer was:\n" + correct_answer);
            }
        }

        current_game.increase_answered();
        current_game.addQuestion(current_question);

        // stop the game if 10 questions have been answered
        if (current_game.getQuestions_answered() == 10) {
            new HighscoresHelper(getApplicationContext()).postNewHighscore(current_game);

            Intent intent = new Intent(GameActivity.this, DetailscoreActivity.class);
            ArrayList<Question> questions = (ArrayList) current_game.getQuestions();

            intent. putExtra("questions", questions);
            startActivity(intent);

            finish();
        }
        question_request.getNextQuestion(this);
    }
}
