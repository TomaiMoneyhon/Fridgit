package com.tomai.fridgit;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


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

    public void Search (String [] ingredients) {
        int counter = 0;
        String recipeList = "";

        for (String ingredient : ingredients) {
            if (ingredients.length-1 == counter) recipeList = recipeList + ingredient;
            else {
                counter++;
                recipeList = recipeList + (ingredient + "%2C");
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
                Toast.makeText(context,"Somthing bad happened",Toast.LENGTH_LONG).show();
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


//        HttpGet request = new HttpGet(theUrl);
//        request.addHeader("x-api-key", apiKey);
//        HttpResponse response = httpclient.execute(request);

    }


}
