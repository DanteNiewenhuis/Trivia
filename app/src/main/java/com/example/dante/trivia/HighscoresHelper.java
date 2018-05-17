package com.example.dante.trivia;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HighscoresHelper implements Response.Listener<JSONObject>, Response.ErrorListener{
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

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                highscores = new ArrayList<>();
                Log.d("onChange", "empty");
                for (DataSnapshot score : dataSnapshot.child("highscores").getChildren()) {

                    Log.d("onChange", "add");
                    Highscore item = score.getValue(Highscore.class);
                    highscores.add(item);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getHighscores(Callback activity) {
        this.activity = activity;
        activity.gotHighscores(highscores);
    }

    public void postNewHighscore(String name, int score) {
        Highscore highscore = new Highscore(name, score);

        String userId = mDatabase.push().getKey();
        mDatabase.child("highscores").child(userId).setValue(highscore);
    }
}
