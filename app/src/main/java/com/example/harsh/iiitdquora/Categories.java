package com.example.harsh.iiitdquora;

import java.util.ArrayList;

/**
 * Created by Tushar on 15-11-2016.
 */

public class Categories {

    private int question_id;
    private String interest;

    public Categories(int question_id, String interest) {
        this.question_id = question_id;
        this.interest = interest;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
}
