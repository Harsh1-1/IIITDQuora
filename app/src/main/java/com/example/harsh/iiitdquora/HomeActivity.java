package com.example.harsh.iiitdquora;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.*;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 3;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    FragmentManager fragmentManager;
    private String KEY_FEED_FRAGMENT = "feedFragment";
    private String KEY_ASKED_FRAGMENT = "askedFragment";
    private String KEY_ANSWER_FRAGMENT = "answerFragment";

    private Fragment askedFragment = AskedFragment.newInstance();
    private Fragment feedFragment = FeedFragment.newInstance();
    private Fragment answerFragment = AnswerFragment.newInstance();
    private Context context = this;
    //ArrayList<Question> askedQuestionArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("f", "Problem occured after this point");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        fragmentManager=getSupportFragmentManager();
        viewPager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(fragmentManager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        //fragmentManager.beginTransaction().add(askedFragment, null).add(feedFragment, null).add(answerFragment,null).commit();


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        setFocusOfButtons(R.id.feedButton);
                        break;
                    case 1:
                        setFocusOfButtons(R.id.AskedButton);
                        break;
                    case 2:
                        setFocusOfButtons(R.id.activityHomeAnswerButton);
                        break;
                }
            }
            @Override public void onPageScrollStateChanged(int state) {}
        });

        Button feedButton = (Button)(findViewById(R.id.feedButton));
        feedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
                //TODO: Call feed task
            }
        });

        Button askedButton = (Button)(findViewById(R.id.AskedButton));
        askedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
                UserQuestionsTask task = new UserQuestionsTask(context);
                task.execute(SignInActivity.user.getEmailId());
            }
        });

        Button answerButton = (Button)(findViewById(R.id.activityHomeAnswerButton));
        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(2);
                //TODO: call answer task
            }
        });

        if(savedInstanceState == null) {
            //viewPager.setCurrentItem(2);
            //viewPager.setCurrentItem(1);
            viewPager.setCurrentItem(0);
            setFocusOfButtons(R.id.feedButton);
        }else {
            feedFragment = fragmentManager.getFragment(savedInstanceState, KEY_FEED_FRAGMENT);
            askedFragment = fragmentManager.getFragment(savedInstanceState, KEY_ASKED_FRAGMENT);
            answerFragment = fragmentManager.getFragment(savedInstanceState, KEY_ANSWER_FRAGMENT);
        }
        int cur = viewPager.getCurrentItem();
        switch (cur){
            case 0 : setFocusOfButtons(R.id.feedButton);
                break;
            case 1 : setFocusOfButtons(R.id.AskedButton);
                break;
            case 2 : setFocusOfButtons(R.id.activityHomeAnswerButton);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.QuestionSearch);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.QuestionSearch:
                //TODO
                return true;

            case R.id.UserProfile:
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainLayout, UserProfileFragment.newInstance());
                if(fragmentManager.getBackStackEntryCount() > 0)
                    fragmentManager.popBackStack();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return true;

            case R.id.AskQuestion:
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainLayout, AskQuestionFragment.newInstance());
                if(fragmentManager.getBackStackEntryCount() > 0)
                    fragmentManager.popBackStack();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return true;

            case R.id.Logout:
                SignInActivity.user = null;
                Intent intent = new Intent(getApplicationContext(),SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0 || fragmentManager.getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }


    public void setFocusOfButtons(int buttonID){
        int id[] = new int[3];
        id[0] = R.id.feedButton;
        id[1] = R.id.activityHomeAnswerButton;
        id[2] = R.id.AskedButton;
        for(int i = 0; i < id.length; i++){
            if(id[i] == buttonID){
                ((Button)findViewById(id[i])).setTextColor(Color.parseColor("#FFFFFF"));
            }
            else{
                ((Button)findViewById(id[i])).setTextColor(Color.parseColor("#AAAAAA"));
            }
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //Fragment retFragment = feedFragment;
            Fragment retFragment = feedFragment;
            switch (position){
                case 0:
                    retFragment = feedFragment;
                    break;
                case 1:
                    retFragment = askedFragment;
                    UserQuestionsTask task = new UserQuestionsTask(context);
                    task.execute(SignInActivity.user.getEmailId());
                    break;
                case 2:
                    retFragment = answerFragment;
                    break;
            }
            return retFragment;
        }
/*
        @Override
        public int getItemPosition(Object object){
            return POSITION_NONE;
        }
*/
        @Override
        public int getCount() {
            return NUM_PAGES;
        }

    }

    public void updateAsked(ArrayList<Question> questions){
        ((AskedFragment)askedFragment).update(questions);
    }

    public void updateFeed(ArrayList<Question> questions){
        ((FeedFragment)feedFragment).update(questions);
    }

    public void updateAnswer(ArrayList<Question> questions){
        ((AnswerFragment)answerFragment).update(questions);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        fragmentManager.putFragment(outState, KEY_FEED_FRAGMENT,feedFragment);
        fragmentManager.putFragment(outState, KEY_ANSWER_FRAGMENT, answerFragment);
        fragmentManager.putFragment(outState, KEY_ASKED_FRAGMENT, askedFragment);
    }
}
