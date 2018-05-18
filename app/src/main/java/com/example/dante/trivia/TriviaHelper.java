package com.example.dante.trivia;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Random;

public class TriviaHelper implements Response.Listener<JSONArray>, Response.ErrorListener{
    private Context context;
    private Callback activity;

    public TriviaHelper(Context context) {
        this.context = context;
    }

    public interface Callback {
        void gotQuestion(Question question);
        void gotQuestionError(String message);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotQuestionError(error.getMessage());
    }

    @Override
    public void onResponse(JSONArray response) {
        String[] answers = new String[4];

        // create an array of 4 random ints
        int [] random_seed = new int[4];
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int x = random.nextInt(response.length());
            Log.d("RANDOM_SEED", Integer.toString(x));
            if (!Arrays.asList(random_seed).contains(x)) {
                random_seed[i] = x;
            }
        }

        // generate the index of the question
        int correct_index = random.nextInt(4);
        String question = "";
        String correct_answer = "";
        int value = 0;

        try {

            // get the answers of the objects on the random indexes
            for (int i = 0; i < 4; i++) {
                JSONObject object = response.getJSONObject(random_seed[i]);
                answers[i] = object.getString("answer");

                // also get the answer for one of the answers, this answer will also be the correct one
                if (i == correct_index) {
                    question = object.getString("question");
                    correct_answer = object.getString("answer");
                    value = object.getInt("value");
                }
            }

            Question nextQuestion = new Question(question, answers, correct_answer, value);
            activity.gotQuestion(nextQuestion);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getNextQuestion(Callback activity) {
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);

        // generate a random category and get all questions from that
        int category = new Random().nextInt(100);
        Log.d("RANDOM_CATEGORY", Integer.toString(category));
        String url = "http://jservice.io/api/clues?category=" + category;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, this, this);
        queue.add(jsonArrayRequest);
    }

}
