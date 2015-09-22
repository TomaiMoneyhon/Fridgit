package com.tomai.fridgit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tomai.fridgit.Fragments.RecipePreviewItemFragment;

/**
 * Created by admin on 9/17/15.
 */
public class RecipesPreviewActivity extends Activity implements RecipePreviewItemFragment.ChosenRecipeListener {
    int iDDD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_preview_activity);


        Button noBTN = (Button)findViewById(R.id.no_recipe_btn);

        Button yesBTN = (Button)findViewById(R.id.yes_recipe_btn);
        yesBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecipeClickedListener(iDDD);

            }
        });

        View recipePreviewItemFragment = (View)findViewById(R.id.fragment);
        recipePreviewItemFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecipeClickedListener(iDDD);
            }
        });

        noBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Make "noBTN" changed the currently viewed recipe (use "recipeCount" variable from inside the "recipeFragment").
            }
        });

    }

    public void onRecipeClickedListener(int id) {
        Intent intent = new Intent(RecipesPreviewActivity.this,ChoosenRecipeActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    @Override
    public void getID(final int id) {
        iDDD = id;
    }
}
