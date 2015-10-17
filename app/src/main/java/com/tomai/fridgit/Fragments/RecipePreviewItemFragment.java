package com.tomai.fridgit.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tomai.fridgit.MainActivity;
import com.tomai.fridgit.R;
import com.tomai.fridgit.RecipeAPI;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by admin on 9/4/15.
 */
public class RecipePreviewItemFragment extends Fragment {

    RecipeAPI searchAPi = new RecipeAPI(getActivity());

    static public int recipeCount;

    Drawable drawable;
    String outof;

    TextView title;
    TextView missingAmount;
    ImageView thumbnail;
    ProgressBar progressBar;
    RelativeLayout mainLayout;
    TextView nothingInFridge;

     public interface ChosenRecipeListener {
         void getID(int id);
         void getCount (int count);
    }

    ChosenRecipeListener mCallback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (ChosenRecipeListener)activity; //the activity who attached the fragment will implement the interface
        }catch (ClassCastException ex) {
            throw new ClassCastException(activity.toString() + " must implement OnRegisterFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View recipeView = inflater.inflate(R.layout.recipe_preview_item_fragment, container, false);

        title = (TextView)recipeView.findViewById(R.id.recipe_title);
        missingAmount = (TextView)recipeView.findViewById(R.id.recipe_missing_ingredients);
        thumbnail = (ImageView)recipeView.findViewById(R.id.recipe_img);
        progressBar = (ProgressBar)recipeView.findViewById(R.id.progressBar);
        mainLayout = (RelativeLayout)recipeView.findViewById(R.id.recipe_layout);
        nothingInFridge = (TextView) recipeView.findViewById(R.id.nothing_in_fridge);

        final Handler handler = new Handler();

        ConnectivityManager connMgr = (ConnectivityManager)
               getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new Thread() {
                JSONObject currentRecipe;
                @Override
                public void run() {
                    super.run();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    });
                    if (MainActivity.fridgeItems.size() == 0){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                nothingInFridge.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }

                    else {
                        searchAPi.Search(MainActivity.fridgeItems);
                        try {
//                            for (int i = 0 ; searchAPi.getSearchResult().length()-1 > i ; i++) {
//                                int firstnum = searchAPi.getSearchResult().getJSONObject(i).getInt("missedIngredientCount");
//                                for (int y = 0 ; searchAPi.getSearchResult().length()-1 > y ; y++) {
//                                    int secondnumb = searchAPi.getSearchResult().getJSONObject(y).getInt("missedIngredientCount");
//                                    if(firstnum <= secondnumb) {
//                                        currentRecipe = searchAPi.getSearchResult().getJSONObject(i);
//                                        mCallback.getCount(i);
//                                        recipeCount = i;
//                                    }
//                                    else {
//                                        currentRecipe = searchAPi.getSearchResult().getJSONObject(y);
//                                        mCallback.getCount(y);
//                                        recipeCount = y;
//                                    }
//                                }
//                            }
                            currentRecipe = searchAPi.getSearchResult().getJSONObject(0);
                            drawable = getthumbnail(currentRecipe.getString("image"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    nothingInFridge.setVisibility(View.GONE);
                                    mCallback.getID(currentRecipe.getInt("id"));
                                    title.setText(currentRecipe.getString("title"));
                                    outof = currentRecipe.getInt("usedIngredientCount") + "/" + currentRecipe.getInt("missedIngredientCount");
                                    missingAmount.setText(outof);
                                    //TODO Chosse either to set image as background or as a imageView.
                                    //thumbnail.setImageDrawable(getthumbnail(oneRecipe.getString("image"))); //IMAGEVIEW
                                    mainLayout.setBackground(drawable); //BACKGROUND
                                    progressBar.setVisibility(View.GONE);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }.start();
        } else {
            Toast.makeText(getActivity(),"No internet connection.", Toast.LENGTH_LONG).show();
        }
        return recipeView;
    }


    public Drawable getthumbnail (final String url) {
         InputStream[] is = {null};
        try {
            is[0] = (InputStream) new URL(url).getContent();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Drawable d = Drawable.createFromStream(is[0], "src name");
        return d;
    }

    public void nextRecipe (int currentItemCount) {
        final JSONObject nextRecipe;
//TODO check about a issue with images not changing.
        try {
        if (searchAPi.getSearchResult().length()-1 == currentItemCount) {
            recipeCount = 0;
            nextRecipe = searchAPi.getSearchResult().getJSONObject(recipeCount);
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        drawable = getthumbnail(nextRecipe.getString("image"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            mCallback.getID(nextRecipe.getInt("id"));
            mCallback.getCount(recipeCount);
            title.setText(nextRecipe.getString("title"));
            outof = nextRecipe.getInt("usedIngredientCount") + "/" + nextRecipe.getInt("missedIngredientCount");
            missingAmount.setText(outof);
            //TODO Chosse either to set image as background or as a imageView.
            //thumbnail.setImageDrawable(getthumbnail(oneRecipe.getString("image"))); //IMAGEVIEW
            mainLayout.setBackground(drawable); //BACKGROUND
        }
            else {
            recipeCount = currentItemCount + 1;
            nextRecipe = searchAPi.getSearchResult().getJSONObject(recipeCount);
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        drawable = getthumbnail(nextRecipe.getString("image"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
            mCallback.getID(nextRecipe.getInt("id"));
            mCallback.getCount(recipeCount);
            title.setText(nextRecipe.getString("title"));
            outof = nextRecipe.getInt("usedIngredientCount") + "/" + nextRecipe.getInt("missedIngredientCount");
            missingAmount.setText(outof);
            //TODO Chosse either to set image as background or as a imageView.
            //thumbnail.setImageDrawable(getthumbnail(oneRecipe.getString("image"))); //IMAGEVIEW
            mainLayout.setBackground(drawable); //BACKGROUND
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
