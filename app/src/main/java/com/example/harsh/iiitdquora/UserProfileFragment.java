package com.example.harsh.iiitdquora;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        ((TextView)view.findViewById(R.id.UserNameView)).setText(SignInActivity.user.getUsername());
        ((TextView)view.findViewById(R.id.UserEmailView)).setText(SignInActivity.user.getEmailId());
        ((TextView)view.findViewById(R.id.ContactView)).setText(SignInActivity.user.getContact());
        ((TextView)view.findViewById(R.id.AboutMeView)).setText(SignInActivity.user.getAboutMe());
        return view;
    }

}
