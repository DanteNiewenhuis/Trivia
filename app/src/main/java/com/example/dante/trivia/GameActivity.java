package com.example.dante.trivia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements TriviaHelper.Callback{
    private TriviaHelper question_request;
    private Question current_question;
    private int score = 0;
    private int questions_answered = 0;
    private int questions_correct = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        question_request = new TriviaHelper(this);
        question_request.getNextQuestion(this);

    }

    @Override
    public void gotQuestion(Question question) {
        current_question = question;
        TextView question_view = findViewById(R.id.question_View);
        TextView index_view = findViewById(R.id.index_view);
        TextView correct_view = findViewById(R.id.correct_view);
        TextView score_view = findViewById(R.id.score_view);

        index_view.setText("question " + questions_answered);
        correct_view.setText(questions_correct + " correct");
        score_view.setText("score: " + score);
        question_view.setText(question.getQuestion());

        String[] answers = question.getAnswers();

        for (int i = 0; i < 4; i++) {
            int id = getResources().getIdentifier("answer_" + i + "_button", "id", getPackageName());
            RadioButton button = findViewById(id);

            button.setText(answers[i]);
        }
    }

    @Override
    public void gotQuestionError(String message) {
        Log.d("error", message);
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    public void submit(View v){
        RadioGroup answers = findViewById(R.id.answers_group);
        int checked_id = answers.getCheckedRadioButtonId();
        RadioButton checked = findViewById(checked_id);
        String answer = checked.getText().toString();

        Log.d("CHOSEN ANSWER", answer);
        Log.d("CORRECT ANSWER", current_question.getCorrectAnswer());
        TextView response_text = findViewById(R.id.response_text);
        if (answer.equals(current_question.getCorrectAnswer())) {
            response_text.setText("good job!!!!!!!");
            questions_correct++;
            score += current_question.getValue();
        }
        else {
            response_text.setText("bad answer");
        }

        questions_answered++;

        if (questions_answered == 10) {
            HighscoresHelper highscoreHelper = new HighscoresHelper(this);
            highscoreHelper.postNewHighscore("test", score);
            finish();
        }
        question_request.getNextQuestion(this);
    }
}
