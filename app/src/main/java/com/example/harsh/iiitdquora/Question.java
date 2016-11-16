package com.example.harsh.iiitdquora;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Tushar on 15-11-2016.
 */

public class Question {

    private int id;
    private String description;
    private String created_by;
    private String created_on;
    private String text;
    private Bitmap image;
    private ArrayList<Categories>categories;

    public Question(int id, String description, String created_by, String created_on, String text, Bitmap image, ArrayList<Answer> answers) {
        this.id = id;
        this.description = description;
        this.created_by = created_by;
        this.created_on = created_on;
        this.text = text;
        this.image = image;
        this.answers = answers;
    }

    public Question(int id, String description, String created_by, String created_on, String text)
    {
        this.id = id;
        this.description = description;
        this.created_by = created_by;
        this.created_on = created_on;
        this.text = text;
    }


    public ArrayList<Categories> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Categories> categories) {
        this.categories = categories;
    }

    public ArrayList<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<Answer> answers) {
        this.answers = answers;
    }

    private ArrayList<Answer>answers = new ArrayList<Answer>();


    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
