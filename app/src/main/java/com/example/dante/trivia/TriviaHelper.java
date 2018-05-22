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
    private int category = 1;

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

        //TODO make random
        String[] answers = new String[4];
        int [] random_seed = new int[4];

        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int x = random.nextInt(response.length());
            if (!Arrays.asList(random_seed).contains(x)) {
                random_seed[i] = x;
            }
        }

        int correct_index = random.nextInt(4);
        String question = "";
        String correct_answer = "";
        int value = 0;

        try {
            for (int i = 0; i < 4; i++) {
                JSONObject object = response.getJSONObject(random_seed[i]);
                answers[i] = object.getString("answer");

                if (i == correct_index) {
                    question = object.getString("question");
                    correct_answer = object.getString("answer");
                    value = object.getInt("value");
                }
            }

            Log.d("question", question);
            Log.d("correct answer", correct_answer);

            Question nextQuestion = new Question(question, answers, correct_answer, value);
            activity.gotQuestion(nextQuestion);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getNextQuestion(Callback activity) {
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "http://jservice.io/api/clues?category=" + category;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                                            null, this, this);
        queue.add(jsonArrayRequest);
    }

}
