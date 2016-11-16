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

    private Fragment askedFragment = AskedFragment.newInstance();
    private Fragment feedFragment = FeedFragment.newInstance();
    private Fragment answerFragment = AnswerFragment.newInstance();

    //ArrayList<Question> askedQuestionArrayList;


    private Button askedButton;

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
        viewPager.setCurrentItem(0);
        setFocusOfButtons(R.id.feedButton);
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
                        setFocusOfButtons(R.id.AnsweredButton);
                        break;
                }
            }
            @Override public void onPageScrollStateChanged(int state) {}
        });



        final Context context = this;
        askedButton = (Button)(findViewById(R.id.AskedButton));
        askedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*setFocusOfButtons(R.id.AskedButton);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if(fragmentManager.findFragmentById(askedFragment.getId()) == null) {
                    fragmentTransaction.replace(R.id.mainLayout, askedFragment);
                }else{
                    for(Fragment fragment : fragmentManager.getFragments()){
                        fragmentTransaction.hide(fragment);
                    }
                    fragmentTransaction.show(askedFragment);
                }
                fragmentTransaction.commit();*/
                viewPager.setCurrentItem(1);
                UserQuestionsTask task = new UserQuestionsTask(context);
                task.execute(SignInActivity.user.getEmailId());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.QuestionSearch);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

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
        id[1] = R.id.AnsweredButton;
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
                    break;
                case 2:
                    retFragment = answerFragment;
                    break;
            }
            return retFragment;
        }

        @Override
        public int getItemPosition(Object object){
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

    }

    public void updateAsked(ArrayList<Question> questions){
        ((AskedFragment)askedFragment).update(questions);
        //askedAdapter.setDataset(questions);
    }

}
