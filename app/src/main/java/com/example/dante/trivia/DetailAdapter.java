package com.example.dante.trivia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DetailAdapter extends ArrayAdapter<Question> {
    private List<Question> items;

    public DetailAdapter(@NonNull Context context, int resource, @NonNull List<Question> objects) {
        super(context, resource, objects);
        this.items = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.question_item, parent, false);
        }

        // get the info of the items
        Question item = items.get(position);
        String question = item.getQuestion();
        String correctAnswer = item.getCorrectAnswer();
        String givenAnswer = item.getGivenAnswer();
        int score = item.getPoints_gained();

        // get the views
        TextView question_view = convertView.findViewById(R.id.detail_question_view);
        TextView correct_answer_view = convertView.findViewById(R.id.detail_correct_answer_view);
        TextView given_answer_view = convertView.findViewById(R.id.detail_given_answer_view);
        TextView score_view = convertView.findViewById(R.id.detail_score_view);

        // set the info to the views.
        question_view.setText(question);
        correct_answer_view.setText(correctAnswer);
        given_answer_view.setText(givenAnswer);
        score_view.setText(Integer.toString(score));

        // set the color to green if the question was answered correctly otherwise red.
        if (item.goodAnswer()) {
            convertView.setBackgroundColor(convertView.getResources().getColor(android.R.color.holo_green_light, getDropDownViewTheme()));
        }
        else {
            convertView.setBackgroundColor(convertView.getResources().getColor(android.R.color.holo_red_light, getDropDownViewTheme()));
        }
        return convertView;
    }
}
