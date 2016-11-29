package com.example.harsh.iiitdquora;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.harsh.iiitdquora.Helpers.InternetConnectivity;
import com.example.harsh.iiitdquora.beans.Categories;
import com.example.harsh.iiitdquora.tasks.ImageResizerTask;
import com.example.harsh.iiitdquora.tasks.QuestionBackgroundTask;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AskQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AskQuestionFragment extends Fragment implements Updatable {
    /*private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    */

    private static int RESULT_GET_IMAGE = 10000;

    public AskQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */
    // TODO: Rename and change types and number of parameters
    public static AskQuestionFragment newInstance(/*String param1, String param2*/) {
        AskQuestionFragment fragment = new AskQuestionFragment();
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

    private Bitmap bm = null;

    public void update(Bitmap bm){
        this.bm = bm;
        final Button askQuestionButton = (Button)getView().findViewById(R.id.AskButton);
        askQuestionButton.setEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_ask_question, container, false);

        //Button uploadImageButton = (Button)view.findViewById(R.id.UploadImageButton);
        final Button askQuestionButton = (Button)view.findViewById(R.id.AskButton);
        /*uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_GET_IMAGE);
            }
        });
        */
        askQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("f", "askButton Clicked");
                EditText questionText = (EditText) view.findViewById(R.id.QuestionAskEditText);
                EditText descText = (EditText) view.findViewById(R.id.QuestionDescEditText);
                Log.d("f", "Question Asked is " + questionText.getText().toString());
                Spinner spinner = (Spinner)getView().findViewById(R.id.categorySpinner);
                String s = (String)spinner.getSelectedItem();
                if(s.equalsIgnoreCase("Please Select a Category")){
                    Toast.makeText(getContext(), "Please select a Category", Toast.LENGTH_SHORT).show();
                    return;
                }
                int pos = -1;
                for(Categories c : HomeActivity.categoriesArrayList){
                    if(s.equalsIgnoreCase(c.getInterest())){
                        pos = c.getInterest_id();
                    }
                }
                if(questionText.getText().toString() == null || questionText.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Question Text cannot be null", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Question Asked Successfully", Toast.LENGTH_SHORT).show();

                    if(InternetConnectivity.isConnected() == false)
                    {
                        Toast.makeText(getContext(),"No Internet Connectivity",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    QuestionBackgroundTask questionBackgroundTask = new QuestionBackgroundTask(getContext());
                    questionBackgroundTask.execute(SignInActivity.user.getEmailId(),
                            questionText.getText().toString(), descText.getText().toString(), String.valueOf(pos), s);
                    ((HomeActivity)getActivity()).updateAskedDataset();
                    questionText.setText("");
                    descText.setText("");
                    spinner.setSelection(HomeActivity.categoriesArrayList.size());
                    ImageView imageView = (ImageView)getView().findViewById(R.id.selectedImageView);
                    imageView.setImageDrawable(null);
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = (ImageView) getView().findViewById(R.id.selectedImageView);
        if(savedInstanceState != null){
            bm = (Bitmap) savedInstanceState.getParcelable("bitmap");
            if(bm != null) {
                imageView.setImageBitmap(bm);
            }else{
                imageView.setImageDrawable(null);
            }
        }else {
            imageView.setImageDrawable(null);
        }
        Spinner spinner = (Spinner) getView().findViewById(R.id.categorySpinner);
        String choices[] = new String[HomeActivity.categoriesArrayList.size() + 1];
        for(int i = 0; i < HomeActivity.categoriesArrayList.size(); i++){
            choices[i] = HomeActivity.categoriesArrayList.get(i).getInterest();
        }
        choices[choices.length - 1] = "Please Select a Category";
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, choices);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(choices.length-1);
        //((LinearLayout)getView().findViewById(R.id.askQuestionLinearLayout)).invalidate();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_GET_IMAGE && resultCode == Activity.RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            String[] column = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, column, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(column[0]);
            String imageString = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) getView().findViewById(R.id.selectedImageView);
            Button button = (Button) getView().findViewById(R.id.AskButton);
            button.setEnabled(false);

            if(InternetConnectivity.isConnected() == false)
            {
                Toast.makeText(getContext(),"No internet connectivity",Toast.LENGTH_SHORT).show();
                return;
            }


            ImageResizerTask task = new ImageResizerTask(imageView, this);
            task.execute(imageString, "512", "512");
            //imageView.setImageBitmap(BitmapFactory.decodeFile(imageString));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(bm != null){
            outState.putParcelable("bitmap", bm);
        }
    }

}
