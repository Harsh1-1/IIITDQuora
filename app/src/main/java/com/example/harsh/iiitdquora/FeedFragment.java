package com.example.harsh.iiitdquora;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.harsh.iiitdquora.Helpers.QuestionListAdapter;
import com.example.harsh.iiitdquora.beans.Question;

import java.util.ArrayList;

//Fragment for feed
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

//Fragment for showing the feed to the user
public class FeedFragment extends Fragment {
    /*private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    */

    public FeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FeedFragment.
     */
    public static FeedFragment newInstance() {
        FeedFragment fragment = new FeedFragment();
    /*    Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
    */   return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    /*    if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    QuestionListAdapter feedAdapter;
    RecyclerView feedRecyclerView;
    RecyclerView.LayoutManager feedRecyclerViewLayoutManager;
    ArrayList<Question> dataset;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        feedRecyclerView = (RecyclerView)getView().findViewById(R.id.feedFragmentRecyclerView);
        feedRecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        feedRecyclerView.setLayoutManager(feedRecyclerViewLayoutManager);
        feedAdapter = new QuestionListAdapter(getActivity());
        feedRecyclerView.setAdapter(feedAdapter);
        if(savedInstanceState != null){
            update((ArrayList<Question>) savedInstanceState.getSerializable("dataset"));
        }

    }

    //Update dataset and views
    public void update(ArrayList<Question> dataset){
        this.dataset = dataset;
        feedAdapter.setDataset(dataset);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("dataset", dataset);
    }

}
