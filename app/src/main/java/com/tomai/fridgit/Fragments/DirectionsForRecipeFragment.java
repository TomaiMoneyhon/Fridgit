package com.tomai.fridgit.Fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.tomai.fridgit.ChoosenRecipeActivity;
import com.tomai.fridgit.R;
import com.tomai.fridgit.RecipeAPI;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 9/22/15.
 */
public class DirectionsForRecipeFragment extends Fragment {

    JSONObject recipe;
    WebView webview;
    Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View listFragment = inflater.inflate(R.layout.directions_for_recipe_fragment, container, false);
        webview = (WebView)listFragment.findViewById(R.id.webView);
        new Thread() {
            @Override
            public void run() {
                super.run();
                recipe =  new RecipeAPI(getContext()).getRecipe(ChoosenRecipeActivity.recipeID + "");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            webview.loadUrl(recipe.getString("sourceUrl"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        }.start();

        Button finnshedCooking = (Button)listFragment.findViewById(R.id.finnished_cooking_btn);
        finnshedCooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ChoosenRecipeActivity.hasIngredients){
                    //TODO Create dialog saying "you don't have all ingredient" with three options "i actually do have them", "add them to shopping list" and exit
                }
            }
        });
        return listFragment;
    }
}
