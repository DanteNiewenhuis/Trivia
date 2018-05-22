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

import java.util.ArrayList;
import java.util.List;
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

    private boolean contains(int[] arr, int item) {
        for (int n : arr) {
            if (item == n) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onResponse(JSONArray response) {
        List<String> answers = new ArrayList<>();

        // create an array of 4 random ints
        int [] random_seed = new int[4];
        Random random = new Random();
        int index = 0;

        // request the api again if to little questions are found in the category
        if (response.length() < 4) {
            activity.gotQuestionError("AGAIN");
        }

        while (index < 4) {
            int x = random.nextInt(response.length());
            if (!contains(random_seed, x)) {
                random_seed[index] = x;
                index++;
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
                answers.add(object.getString("answer"));


                // get a new question if the gotten answer is empty
                if (object.getString("answer").length() == 0) {
                    activity.gotQuestionError("AGAIN");
                }
                // also get the answer for one of the answers, this answer will also be the correct one
                if (i == correct_index) {
                    question = object.getString("question");
                    correct_answer = object.getString("answer");

                    // get a new question if the gotten question is empty
                    if (object.getString("question").length() == 0) {
                        activity.gotQuestionError("AGAIN");
                    }

                    // set the value to 100 if no value is found in the response
                    try {
                        value = object.getInt("value");
                    }
                    catch (JSONException e){
                        value = 100;
                    }
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
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                                            null, this, this);
        queue.add(jsonArrayRequest);
    }

}
