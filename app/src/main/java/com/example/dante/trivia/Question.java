package com.example.dante.trivia;

public class Question {
    private String question;
    private String[] answers;
    private String correctAnswer;
    private int value;

    public Question(String question, String[] answers, String correctAnswer, int value) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
        this.value = value;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
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
}
