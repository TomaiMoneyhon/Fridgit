package com.tomai.fridgit.Fragments;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.tomai.fridgit.Adapters.IngredientsForRecipeListAdapter;
import com.tomai.fridgit.ChoosenRecipeActivity;
import com.tomai.fridgit.MainActivity;
import com.tomai.fridgit.R;
import com.tomai.fridgit.RecipeAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 9/22/15.
 */
public class IngredientsForRecipeFragment extends Fragment {
    private JSONArray ingredientsjsonArray;
    public static ArrayList<JSONObject> ingredientsArrayList = new ArrayList<>();
    private JSONObject recipe;
    private Handler handler = new Handler();

    private IngredientsForRecipeListAdapter customAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View listFragment = inflater.inflate(R.layout.ingredients_for_recipe_fragment, container, false);
        final ListView ingredientsList = (ListView)listFragment.findViewById(R.id.ingredients_for_recipe_list);

        ConnectivityManager connMgr = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        ingredientsArrayList.clear();

        customAdapter = new IngredientsForRecipeListAdapter(getContext(),ingredientsArrayList);
        ingredientsList.setAdapter(customAdapter);
        if (networkInfo != null && networkInfo.isConnected()) {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    recipe =  new RecipeAPI(getContext()).getRecipe(ChoosenRecipeActivity.recipeID + "");
                    try {
                        ingredientsjsonArray = recipe.getJSONArray("extendedIngredients");
                        for (int i = 0 ; ingredientsjsonArray.length() > i ; i++) {
                            ingredientsArrayList.add((JSONObject)ingredientsjsonArray.get(i));
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                customAdapter.notifyDataSetChanged();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.start();



        }

        Button changeServingsBTN = (Button)listFragment.findViewById(R.id.change_servings_btn);
        changeServingsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO create a custom dialog to change serving size of all ingredients.
            }
        });
//        final Handler handler = new Handler();

        Button addToShoppingListBTN = (Button)listFragment.findViewById(R.id.add_to_shopping_list_btn);
        addToShoppingListBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {addMissingIngredients(getContext());

                ///USING CONVERTERS!!
//                new Thread() {
//
//                    @Override
//                    public void run() {
//                        super.run();
//                        Converters converters = new Converters(getContext());
//                        final String result = converters.cookingUnitConverter(15,"tablespoonUS","liter", Converters.ConverterKind.CookingUnits);
//
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
//
//                            }
//                        });
//
//                    }
//                }.start();
            }
        });
        return listFragment;
    }
    public static void addMissingIngredients (Context context){
        for(int i = 0; ChoosenRecipeActivity.missingItems.size() > i ; i++){
            boolean hasIngredient = false;
            for(int y = 0; MainActivity.shoppingItems.size()>y; y++){
                if(MainActivity.shoppingItems.get(y).getName().equals(ChoosenRecipeActivity.missingItems.get(i).getName())) {
                    hasIngredient = true;
                }
            }
            if(!hasIngredient) {
                MainActivity.shoppingItems.add(ChoosenRecipeActivity.missingItems.get(i));
            }
        }
        ChoosenRecipeActivity.missingItems.clear();
        Toast.makeText(context,"Missing ingredients have been added to your shopping list",Toast.LENGTH_LONG).show();
    }
}
