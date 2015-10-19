package com.tomai.fridgit.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.tomai.fridgit.Fragments.IngredientsForRecipeFragment;
import com.tomai.fridgit.R;

/**
 * Created by admin on 9/12/15.
 */
public class MissingIngredientsDialog extends DialogFragment {

    private Activity activity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.missing_ingredients_dialog,null);
        builder.setView(view);

        Button addMissingBTN = (Button)view.findViewById(R.id.add_missing_btn);
        addMissingBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IngredientsForRecipeFragment.addMissingIngredients(activity);
            }
        });
        return builder.create();
    }
}
