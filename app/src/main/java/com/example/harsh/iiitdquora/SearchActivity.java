package com.example.harsh.iiitdquora;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
//Activity for implementing search
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
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_searchToolbar);
        setSupportActionBar(myToolbar);
        ActionBar action = getSupportActionBar();
        action.setDisplayHomeAsUpEnabled(true);

        questionListAdapter = new QuestionListAdapter(this);
        recyclerView = (RecyclerView)findViewById(R.id.searchRecyclerView);
        recyclerLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerLayoutManager);
        recyclerView.setAdapter(questionListAdapter);
        query = getIntent().getStringExtra("QUERY");

        if(InternetConnectivity.isConnected() == false)
        {
            Toast.makeText(this,"No Internet Connectivity",Toast.LENGTH_SHORT).show();
            return;
        }

        SearchQuestionsTask searchQuestionsTask = new SearchQuestionsTask(this);
        searchQuestionsTask.execute(query);
    }

    public void update(ArrayList<Question> dataset){
        this.dataset = dataset;
        questionListAdapter.setDataset(dataset);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.QuestionSearch2);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchItem.expandActionView(); // expand the search action item automatically
        searchView.setQuery(query, false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query != null) {
                    Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                    intent.putExtra("QUERY", query);
                    startActivity(intent);
                    searchView.setQuery("", false);
                    searchItem.collapseActionView();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        // Configure the search info and add any event listeners...

        return super.onCreateOptionsMenu(menu);
    }
}
