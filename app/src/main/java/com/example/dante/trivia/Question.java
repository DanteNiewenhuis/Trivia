package com.example.dante.trivia;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {
    private String question;
    private List<String> answers;
    private String correctAnswer;
    private String givenAnswer;
    private int points_gained;
    private int value;

    public Question() {

    }

    public Question(String question, List<String> answers, String correctAnswer, int value) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.value = value;
        this.points_gained = 0;
    }

    public boolean goodAnswer() {
        return correctAnswer.equals(givenAnswer);
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPoints_gained() {
        return points_gained;
    }

    public void setPoints_gained(int points_gained) {
        this.points_gained = points_gained;
    }
}
