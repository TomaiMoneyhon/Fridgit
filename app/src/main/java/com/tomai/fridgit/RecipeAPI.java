package com.tomai.fridgit;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by admin on 9/3/15.
 */
public class RecipeAPI {
    private String keyString = "R4137EnBxzmshDqnV5fU7gnjxSjqp1EjeTPjsnaWFdR2Gxmn29";
    private static JSONArray searchResult;
    private Context context;


    public RecipeAPI(Context context) {
        this.context = context;
    }

    public JSONArray getSearchResult() {
        return searchResult;
    }

    public void Search (ArrayList<Item> ingredients) {
        String recipeList = "";
        for (int i = 0 ; i < ingredients.size(); i++) {
            String ingredientI = ingredients.get(i).getName().replaceAll(" ","+").toLowerCase();
            if (ingredients.size() == i + 1) {
                recipeList = recipeList + ingredientI;
            }
            else {
                recipeList = recipeList + ingredientI + "%2C";
            }
        }

        try {
            String urlString = "https://webknox-recipes.p.mashape.com/recipes/findByIngredients?ingredients=";
            String resultsAmount = "&number=5";
            URL fullurl = new URL(urlString + recipeList + resultsAmount);
            HttpURLConnection conn = (HttpURLConnection) fullurl.openConnection();
            conn.setRequestProperty("X-Mashape-Key", keyString);
            conn.setRequestProperty("Accept", "application/json");

            conn.connect();

            int response = conn.getResponseCode();

            if (response >= 400) {
                Toast.makeText(context,"Something bad happened",Toast.LENGTH_LONG).show();
            }
            else {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                searchResult = new JSONArray(sb.toString());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getRecipe (String id) {
        JSONObject recipe = null;
        try {
        String urlString = "https://webknox-recipes.p.mashape.com/recipes/"+ id +"/information";
            URL fullurl = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) fullurl.openConnection();
            conn.setRequestProperty("X-Mashape-Key", keyString);
            conn.setRequestProperty("Accept", "application/json");

            conn.connect();

            int response = conn.getResponseCode();

            if (response >= 400) {
                Toast.makeText(context,"Something bad happened",Toast.LENGTH_LONG).show();
            }
            else {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                 recipe = new JSONObject(sb.toString());
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipe;
    }


}
