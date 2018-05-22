package com.example.dante.trivia;

import android.app.DownloadManager;
import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

<<<<<<< HEAD
import org.json.JSONArray;
import org.json.JSONException;

=======
>>>>>>> e9654a0346640497f90cf53e06a792c90deb3d0e
import java.util.ArrayList;
import java.util.Collections;

<<<<<<< HEAD
public class HighscoresHelper implements Response.Listener<JSONArray>, Response.ErrorListener{
=======
public class HighscoresHelper{
>>>>>>> e9654a0346640497f90cf53e06a792c90deb3d0e
    private Context context;
    private Callback activity;
    private DatabaseReference mDatabase;
    private ArrayList<Highscore> highscores;

    public interface Callback {
        void gotHighscores(ArrayList<Highscore> highscores);
        void gotHighscoresError(String message);
    }

<<<<<<< HEAD
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
=======
    public HighscoresHelper(Context context) {
        this.context = context;

        mDatabase = FirebaseDatabase.getInstance().getReference();

>>>>>>> e9654a0346640497f90cf53e06a792c90deb3d0e
    }

    public void getHighscores(Callback activity) {
        this.activity = activity;

<<<<<<< HEAD
        DownloadManager.Query query = mDatabase.orderByChild("score");
        queue.addValueEventListener(this);
=======
        // get the children in the highscores database and order them by scores.
        DatabaseReference reference = mDatabase.child("highscores");
        Query query = reference.orderByChild("score").limitToLast(2000);
        query.addValueEventListener(new highscoreValueListener());
>>>>>>> e9654a0346640497f90cf53e06a792c90deb3d0e
    }

    private class highscoreValueListener implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            // create a new ArrayList and get the entries from the database
            highscores = new ArrayList<>();

            for (DataSnapshot score : dataSnapshot.getChildren()) {

                Highscore item = score.getValue(Highscore.class);
                highscores.add(item);
            }

            // reverse the Arraylist to get a descending list.
            Collections.reverse(highscores);
            activity.gotHighscores(highscores);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            activity.gotHighscoresError(databaseError.getMessage());
        }
    }

    public void postNewHighscore(Highscore new_score) {
        String userId = mDatabase.push().getKey();
        mDatabase.child("highscores").child(userId).setValue(new_score);
    }
}
