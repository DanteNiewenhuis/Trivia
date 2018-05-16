package com.example.dante.trivia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HighscoreAdapter extends ArrayAdapter<Highscore> {
    private ArrayList<Highscore> items;

    public HighscoreAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Highscore> objects) {
        super(context, resource, objects);
        this.items = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.highscore_item, parent, false);
        }

        Highscore item = items.get(position);
        String name = item.getName();
        int score = item.getScore();

        TextView name_view = convertView.findViewById(R.id.highscore_name);
        TextView score_view = convertView.findViewById(R.id.highscore_score);

        name_view.setText(name);
        score_view.setText(Integer.toString(score));

        return convertView;
    }
}
