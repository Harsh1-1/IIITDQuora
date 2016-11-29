package com.example.harsh.iiitdquora;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harsh.iiitdquora.Helpers.CategoriesDialog;
import com.example.harsh.iiitdquora.Helpers.InternetConnectivity;
import com.example.harsh.iiitdquora.beans.Categories;
import com.example.harsh.iiitdquora.tasks.ImageResizerTask;
import com.example.harsh.iiitdquora.tasks.RetrieveInterestTask;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment implements Updatable{

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
        //CategoriesTask task = new CategoriesTask(this);
        //task.execute();
        if(InternetConnectivity.isConnected() == false)
        {
            Toast.makeText(getContext(),"No Internet Connectivity",Toast.LENGTH_SHORT).show();
            return;
        }
        RetrieveInterestTask retrieveInterestTask = new RetrieveInterestTask(this);
        retrieveInterestTask.execute(SignInActivity.user.getEmailId());
    }

    private ArrayList<Categories> userInterests;
    private ArrayList<Categories> allCategories = HomeActivity.categoriesArrayList;

    public void setAllCategories(ArrayList<Categories> allCategories){
        this.allCategories = HomeActivity.categoriesArrayList;
    }

    public void setUserInterests(ArrayList<Categories> userInterests){
        this.userInterests = userInterests;
        TextView interests = (TextView)getView().findViewById(R.id.InterestView);
        String temp = "";
        for(int i=0; i<userInterests.size(); i++){
            temp += userInterests.get(i).getInterest();
            if(i != userInterests.size() - 1){
                temp += ", ";
            }
        }
        interests.setText(temp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        ((TextView)view.findViewById(R.id.UserNameView)).setText(SignInActivity.user.getUsername());
        ((TextView)view.findViewById(R.id.UserEmailView)).setText(SignInActivity.user.getEmailId());
        //((TextView)view.findViewById(R.id.ContactView)).setText(SignInActivity.user.getContact());
        //((TextView)view.findViewById(R.id.AboutMeView)).setText(SignInActivity.user.getAboutMe());
        final Fragment frag = this;
        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.userProfileLinearLayout);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Button updateInterest = (Button)view.findViewById(R.id.updateInterestButton);
        updateInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(InternetConnectivity.isConnected() == false)
                {
                    Toast.makeText(getContext(),"No Internet Connectivity",Toast.LENGTH_SHORT).show();
                }else {
                    CategoriesDialog dialog = new CategoriesDialog();
                    dialog.setList(allCategories, userInterests);
                    dialog.setFrag(frag);
                    dialog.show(getFragmentManager(), "CategoriesDialog");
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = (ImageView)view.findViewById(R.id.profileImageView);
        if(savedInstanceState != null){
            bitmap = savedInstanceState.getParcelable("bitmap");
            if(bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }else{
                imageView.setImageDrawable(null);
            }
        }else {
            if(SignInActivity.user.getPicurl() != null && !SignInActivity.user.getPicurl().isEmpty()) {
                if(InternetConnectivity.isConnected() == false)
                {
                    Toast.makeText(getContext(),"No Internet Connectivity",Toast.LENGTH_SHORT).show();
                    return;
                }
                ImageResizerTask imageResizerTask = new ImageResizerTask(imageView, this);
                imageResizerTask.execute(SignInActivity.user.getPicurl(), "256", "256", "url");
            }else imageView.setImageDrawable(null);
        }
    }

    private Bitmap bitmap;
    @Override
    public void update(Bitmap bm) {
        this.bitmap = bm;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(bitmap != null){
            outState.putParcelable("bitmap", bitmap);
        }
    }
}
