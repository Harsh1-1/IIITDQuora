package com.example.harsh.iiitdquora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    QuestionListAdapter questionListAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerLayoutManager;
    ArrayList<Question>dataset;
    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        questionListAdapter = new QuestionListAdapter(this);
        recyclerView = (RecyclerView)findViewById(R.id.searchRecyclerView);
        recyclerLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerLayoutManager);
        recyclerView.setAdapter(questionListAdapter);
        query = getIntent().getStringExtra("QUERY");
    }

    public void update(ArrayList<Question> dataset){
        this.dataset = dataset;
        questionListAdapter.setDataset(dataset);
    }
}
