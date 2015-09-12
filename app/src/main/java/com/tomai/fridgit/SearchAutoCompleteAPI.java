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
public class SearchAutoCompleteAPI {
    private String keyString = "55f1aa50cea6951604000005";
    private static JSONArray autoCompleteResult;
    private Context context;
    private ArrayList<String> suggestions = new ArrayList<>();

    public SearchAutoCompleteAPI(Context context) {
        this.context = context;
    }

    public ArrayList<String> getsuggestions() {
        return suggestions;
    }

    public ArrayList autoComplete (String searchTerm) {

        try {
            String urlString = "http://api.recipeon.us/2.0/ingredient/suggest/";
            URL fullurl = new URL(urlString + keyString+ "/" + searchTerm.toLowerCase() + "/");
            HttpURLConnection conn = (HttpURLConnection) fullurl.openConnection();
//            conn.setRequestProperty("X-Mashape-Key", keyString);
//            conn.setRequestProperty("Accept", "application/json");

            conn.connect();

            int response = conn.getResponseCode();

            if (response >= 400) {
                Toast.makeText(context,"Somthing bad happened",Toast.LENGTH_LONG).show();
                suggestions = null;
            }
            else {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();

                 autoCompleteResult = new JSONArray(sb.toString());
                suggestions = new ArrayList<>();
                for (int i = 0 ; i < autoCompleteResult.length() ; i++) {
                     JSONObject jsonObject = autoCompleteResult.getJSONObject(i);
                    String suggestion = jsonObject.getString("label");
                    suggestions.add(suggestion);
                }

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return suggestions;
    }


}
