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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnswerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnswerFragment extends Fragment {          //Fragment for showing unansweres questions
    /*private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    */

    public AnswerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AnswerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnswerFragment newInstance() {
        AnswerFragment fragment = new AnswerFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_answer, container, false);
    }

    QuestionListAdapter answerAdapter;
    RecyclerView answerRecyclerView;
    RecyclerView.LayoutManager answerRecyclerViewLayoutManager;
    ArrayList<Question> dataset;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        answerRecyclerView = (RecyclerView)getView().findViewById(R.id.answerFragmentRecyclerView);
        answerRecyclerViewLayoutManager = new LinearLayoutManager(getActivity());
        answerRecyclerView.setLayoutManager(answerRecyclerViewLayoutManager);
        answerAdapter = new QuestionListAdapter(getActivity());
        answerRecyclerView.setAdapter(answerAdapter);
        if(savedInstanceState != null){
            update((ArrayList<Question>) savedInstanceState.getSerializable("dataset"));
        }

    }

    public void update(ArrayList<Question> dataset){
        this.dataset = dataset;
        answerAdapter.setDataset(dataset);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("dataset", dataset);
    }

}
