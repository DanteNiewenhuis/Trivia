package com.example.dante.trivia;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class HighscoresHelper implements Response.Listener<JSONArray>, Response.ErrorListener{
    private Context context;
    private Callback activity;
    private DatabaseReference mDatabase;
    private ArrayList<Highscore> highscores;

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
    public void onResponse(JSONArray response) {
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

        DownloadManager.Query query = mDatabase.orderByChild("score");
        queue.addValueEventListener(this);
    }

    public void postNewHighscore(String name, int score) {
        Highscore highscore = new Highscore(name, score);

        String userId = mDatabase.push().getKey();
        mDatabase.child("highscores").child(userId).setValue(highscore);
    }
}
