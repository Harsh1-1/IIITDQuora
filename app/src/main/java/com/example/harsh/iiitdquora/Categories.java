package com.example.harsh.iiitdquora;

import java.util.ArrayList;

/**
 * Created by Tushar on 15-11-2016.
 */

public class Categories {

    private int interest_id;
    private String interest;

    public Categories(int question_id, String interest) {
        this.interest_id = question_id;
        this.interest = interest;
    }

    public int getQuestion_id() {
        return interest_id;
    }

    public void setQuestion_id(int question_id) {
        this.interest_id = question_id;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
}
