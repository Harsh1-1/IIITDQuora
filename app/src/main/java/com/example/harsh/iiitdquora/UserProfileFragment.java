package com.example.harsh.iiitdquora;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {

    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment UserProfileFragment.
     */
    public static UserProfileFragment newInstance() {
        UserProfileFragment fragment = new UserProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CategoriesTask task = new CategoriesTask(this);
        task.execute();
        userInterests = new ArrayList<>();
        //TODO: Call appropriate tasks
    }

    private ArrayList<Categories> userInterests;
    private ArrayList<Categories> allCategories;

    public void setAllCategories(ArrayList<Categories> allCategories){
        this.allCategories = allCategories;
    }

    public void setUserInterests(ArrayList<Categories> userInterests){
        this.userInterests = userInterests;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        ((TextView)view.findViewById(R.id.UserNameView)).setText(SignInActivity.user.getUsername());
        ((TextView)view.findViewById(R.id.UserEmailView)).setText(SignInActivity.user.getEmailId());
        ((TextView)view.findViewById(R.id.ContactView)).setText(SignInActivity.user.getContact());
        ((TextView)view.findViewById(R.id.AboutMeView)).setText(SignInActivity.user.getAboutMe());
        Button updateInterest = (Button)view.findViewById(R.id.updateInterestButton);
        updateInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoriesDialog dialog = new CategoriesDialog();
                dialog.setList(allCategories, userInterests);
                dialog.show(getFragmentManager(), "CategoriesDialog");
            }
        });
        return view;
    }

}
