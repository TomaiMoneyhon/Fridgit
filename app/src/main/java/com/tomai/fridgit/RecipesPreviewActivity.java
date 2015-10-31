package com.tomai.fridgit;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.tomai.fridgit.Fragments.RecipePreviewItemFragment;

/**
 * Created by admin on 9/17/15.
 */
public class RecipesPreviewActivity extends Activity implements RecipePreviewItemFragment.ChosenRecipeListener {
    private int recipeID;
    private int recipeCount;

    RecipePreviewItemFragment recipePreviewItemFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipes_preview_activity);

        Button noBTN = (Button)findViewById(R.id.no_recipe_btn);

        Button yesBTN = (Button)findViewById(R.id.yes_recipe_btn);
        yesBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecipeClickedListener(recipeID);

            }
        });

        FrameLayout fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        recipePreviewItemFragment = new RecipePreviewItemFragment();
        fragmentTransaction.add(R.id.fragment_container, recipePreviewItemFragment);
        fragmentTransaction.commit();

        fragmentContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecipeClickedListener(recipeID);
            }
        });



        noBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RecipePreviewItemFragment previewFragment = (RecipePreviewItemFragment) getFragmentManager().findFragmentById(R.id.fragment);
                recipePreviewItemFragment.nextRecipe(recipeCount);

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
        recipeID = id;
    }

    @Override
    public void getCount(int count) {
        recipeCount = count;
    }
}
