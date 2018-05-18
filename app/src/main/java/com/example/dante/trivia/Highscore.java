package com.example.dante.trivia;

import java.io.Serializable;

public class Highscore implements Serializable{
    private String name;
    private int score = 0;
    private int questions_answered = 0;
    private int questions_correct = 0;
    private String date;

    public Highscore() {

    }

    public Highscore(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public void increase_score(int x) {
        this.score += x;
    }

    public void increase_answered() {
        this.questions_answered++;
    }

    public void increase_correct() {
        this.questions_correct++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuestions_answered() {
        return questions_answered;
    }

    public void setQuestions_answered(int questions_answered) {
        this.questions_answered = questions_answered;
    }

    public int getQuestions_correct() {
        return questions_correct;
    }

    public void setQuestions_correct(int questions_correct) {
        this.questions_correct = questions_correct;
    }
}
