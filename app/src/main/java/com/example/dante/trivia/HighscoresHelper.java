package com.example.dante.trivia;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class HighscoresHelper implements Response.Listener<JSONObject>, Response.ErrorListener{
    private Context context;
    private Callback activity;
    private DatabaseReference mDatabase;

    public interface Callback {
        void gotHighscores(ArrayList<Highscore> highscores);
        void gotHighscoresError(String message);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("onResponse", "ERROR");
        activity.gotHighscoresError(error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d("onResponse", "init");
        ArrayList<Highscore> highscores = new ArrayList<>();
        String name;
        Log.d("onResponse", "init");
        for(int i = 0; i < response.names().length(); i++){
            try {
                name = response.names().getString(i);
                int score = response.getInt(name);
                Log.d("onResponse", "name: " + name);
                Log.d("onResponse", "score: " + score);
                highscores.add(new Highscore(name, score));
            }
            catch (JSONException e) {
                Log.d("onResponse", e.getMessage());
                e.printStackTrace();
            }
        }

        activity.gotHighscores(highscores);
    }

    public HighscoresHelper(Context context) {
        this.context = context;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void getHighscores(Callback activity) {
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        Log.d("get highscore", "init");
    }

    public void postNewHighscore(String userId, String name, int score) {
        Highscore highscore = new Highscore(name, score);

        mDatabase.child("highscores").child(userId).setValue(highscore);
    }
}
