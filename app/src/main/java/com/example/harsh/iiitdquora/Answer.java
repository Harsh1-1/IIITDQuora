package com.example.harsh.iiitdquora;

import java.util.ArrayList;
import java.util.Date;

//Class for a Answer

public class Answer {

    private String created_by;
    private String date;
    private String text;
    private int question_id;
    private int answer_id;
    private ArrayList<Answer_Rating>answer_ratings;

    public Answer(String created_by, String date, String text, int question_id, int answer_id) {
        this.created_by = created_by;
        this.date = date;
        this.text = text;
        this.question_id = question_id;
        this.answer_id = answer_id;
    }


    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(int answer_id) {
        this.answer_id = answer_id;
    }
}
