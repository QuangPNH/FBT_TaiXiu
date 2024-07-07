package com.example.prm392_taixiufbt.models;

public class QAItem {
    String question;
    String answer;

    public QAItem(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    // Getters
    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}