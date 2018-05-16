package com.example.dante.trivia;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class HighscoresHelper {
    private Context context;
    private Callback activity;

    public interface Callback {
        void gotHighscores(ArrayList<Highscore> highscores);
        void gotHighscoresError(String message);
    }

    //@Override
    public void onErrorResponse(VolleyError error) {
        activity.gotHighscoresError(error.getMessage());
    }

    //@Override
    public void onResponse() {
        ArrayList<Highscore> highscores = new ArrayList<>();
        highscores.add(new Highscore("test1", 4000));
        highscores.add(new Highscore("test2", 1000));
        highscores.add(new Highscore("test3", 4300));
        highscores.add(new Highscore("test4", 2000));

        activity.gotHighscores(highscores);
    }

    public HighscoresHelper(Context context) {
        this.context = context;
    }

    public void getHighscores(Callback activity) {
        this.activity = activity;

        onResponse();
    }
}
