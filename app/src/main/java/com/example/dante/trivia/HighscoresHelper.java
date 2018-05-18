package com.example.dante.trivia;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class HighscoresHelper{
    private Context context;
    private Callback activity;
    private DatabaseReference mDatabase;
    private ArrayList<Highscore> highscores;

    public interface Callback {
        void gotHighscores(ArrayList<Highscore> highscores);
        void gotHighscoresError(String message);
    }

    public HighscoresHelper(Context context) {
        this.context = context;

        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    public void getHighscores(Callback activity) {
        this.activity = activity;

        // get the children in the highscores database and order them by scores.
        DatabaseReference reference = mDatabase.child("highscores");
        Query query = reference.orderByChild("score").limitToLast(2000);
        query.addValueEventListener(new highscoreValueListener());
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
