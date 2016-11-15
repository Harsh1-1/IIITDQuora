package com.example.harsh.iiitdquora;

/**
 * Created by Tushar on 15-11-2016.
 */

public class Answer_Rating {

    private int answer_id;
    private String user;

    public Answer_Rating(int answer_id, String user) {
        this.answer_id = answer_id;
        this.user = user;
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
