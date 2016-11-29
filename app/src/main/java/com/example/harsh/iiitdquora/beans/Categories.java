package com.example.harsh.iiitdquora.beans;

import java.io.Serializable;
import java.util.ArrayList;

/*
CLASS NAME : CATEGORIES
PURPOSE : DATA MEMBERS AND GETTERS AND SETTER FOR CATEGORIES
 */


public class Categories implements Serializable{

    private int interest_id;
    private String interest;

    public Categories(int question_id, String interest) {
        this.interest_id = question_id;
        this.interest = interest;
    }

    public int getInterest_id() {
        return interest_id;
    }

    public void setInterest_id(int question_id) {
        this.interest_id = question_id;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }
}
