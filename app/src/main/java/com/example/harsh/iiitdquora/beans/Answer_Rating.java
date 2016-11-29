package com.example.harsh.iiitdquora.beans;

/*
CLASS NAME : ANSWER_RATING
PURPOSE : DATA MEMBERS AND GETTERS AND SETTER FOR ANSWER_RATING
 */


public class Answer_Rating {

    private int answer_id;
    private String user;
    private int rating;

    public Answer_Rating(int answer_id, String user,int rating) {
        this.answer_id = answer_id;
        this.user = user;
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(int answer_id) {
        this.answer_id = answer_id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
