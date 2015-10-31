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
import com.tomai.fridgit.Converters;
import com.tomai.fridgit.Dialogs.MissingIngredientsDialog;
import com.tomai.fridgit.Item;
import com.tomai.fridgit.MainActivity;
import com.tomai.fridgit.R;
import com.tomai.fridgit.RecipeAPI;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 9/22/15.
 */
public class DirectionsForRecipeFragment extends Fragment {

    private JSONObject recipe;
    private WebView webview;
    private Handler handler = new Handler();

    private Item itemFromFridge;
    private double ingredientForRecipeAmount;
    private JSONObject ingredientForRecipe;

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

        Button finnishedCooking = (Button)listFragment.findViewById(R.id.finnished_cooking_btn);
        finnishedCooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ChoosenRecipeActivity.hasIngredients){
                    MissingIngredientsDialog missingIngredientsDialog = new MissingIngredientsDialog();
                    missingIngredientsDialog.show(getActivity().getFragmentManager(),"missing dialog");
                }
                else{
                   for(int i = 0; IngredientsForRecipeFragment.ingredientsArrayList.size() > i ;i++){
                       for(int y = 0; MainActivity.fridgeItems.size() > y; y++) {
                           try {
                               ingredientForRecipe = IngredientsForRecipeFragment.ingredientsArrayList.get(i);
                               ingredientForRecipeAmount = ingredientForRecipe.getDouble("amount");
                               itemFromFridge = MainActivity.shoppingItems.get(y);
                               if (ingredientForRecipe.getString("name").equals(itemFromFridge.getName())){
                                   switch (ingredientForRecipe.getString("unitShort")){
                                       //teaspoon
                                       case "t":
                                           convertAndDeduct("teaspoonUK","liter",Converters.ConverterKind.CookingUnits);
                                           break;
                                       //tablespoon
                                       case "T":
                                           convertAndDeduct("tablespoonUK","liter",Converters.ConverterKind.CookingUnits);
                                           break;
                                       //cups
                                       case "c":
                                           convertAndDeduct("cupUS","liter",Converters.ConverterKind.CookingUnits);
                                           break;
                                       //pound
                                       case "lb":
                                           convertAndDeduct("PoundsTroy","Grams",Converters.ConverterKind.WeightUnits);
                                           break;
                                       //servings
                                       case "servings":

                                           break;
                                       //ounces
                                       case "oz":
                                           convertAndDeduct("OuncesTroyApoth","Grams",Converters.ConverterKind.WeightUnits);
                                           break;
                                       //bunch
                                       case "bunch":

                                           break;
                                       //dash
                                       case "dash":
                                           convertAndDeduct("dash","liter",Converters.ConverterKind.CookingUnits);
                                           break;
                                       //slices
                                       case "slices":

                                           break;
                                       //cloves
                                       case "cloves":
                                           itemFromFridge.changePercentage(ingredientForRecipeAmount);
                                           break;
                                       //normal units
                                       case "":
                                           itemFromFridge.changePercentage(ingredientForRecipeAmount);
                                           break;

                                   }
                                   //TODO find out who all mesurments are written from api.
                               }
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                       }
                   }
                }
            }
        });
        return listFragment;
    }
    public void convertAndDeduct(final String amountkind, final String toAmount ,final Converters.ConverterKind converterKind) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                Converters converters = new Converters(getContext());
                double resultTo = Double.parseDouble(converters.cookingUnitConverter(ingredientForRecipeAmount, amountkind, toAmount, converterKind));
                itemFromFridge.changePercentage(resultTo);
            }//Converters.ConverterKind.CookingUnits
        }.start();
    }
}
