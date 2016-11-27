package com.example.harsh.iiitdquora;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.ArrayList;

/**
 * Created by Abhi on 27-11-2016.
 */

public class CategoriesDialog extends DialogFragment {
    ArrayList<Categories> allCategories;
    ArrayList<Categories> selectedCategories;

    public void setList(ArrayList<Categories> allCategories, ArrayList<Categories> selectedCategories){
        this.allCategories = allCategories;
        this.selectedCategories = selectedCategories;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] items = new String[allCategories.size()];
        final boolean[] selectedItems = new boolean[allCategories.size()];

        for(int i=0; i<allCategories.size(); i++){
            items[i] = allCategories.get(i).getInterest();
            boolean found = false;
            for(int j=0; j<selectedCategories.size(); j++){
                if(selectedCategories.get(i).getInterest().equals(items[i])){
                    found = true;
                    break;
                }
            }
            selectedItems[i] = found;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Categories")
                .setMultiChoiceItems(items, selectedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        selectedItems[which] = isChecked;
                    }
                })
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ArrayList<Categories> choice = new ArrayList<Categories>();
                        for(int i = 0;i < selectedItems.length; i++){
                            if(selectedItems[i]){
                                choice.add(allCategories.get(i));
                            }
                        }
                        String s = "";
                        for(int i=0; i < choice.size(); i++){
                            s += String.valueOf(choice.get(i).getInterest_id());
                            if(i != choice.size() - 1){
                                s += ",";
                            }
                        }
                        UpdateInterestTask task = new UpdateInterestTask(getContext());
                        task.execute(SignInActivity.user.getEmailId(), s);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
