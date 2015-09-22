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

     public interface ChosenRecipeListener {
         void getID(int id);
    }

    ChosenRecipeListener mCallback;

    static public int recipeCount;
    private int recipeID;

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

        final TextView title = (TextView)recipeView.findViewById(R.id.recipe_title);
        final TextView missingAmount = (TextView)recipeView.findViewById(R.id.recipe_missing_ingredients);
        final ImageView thumbnail = (ImageView)recipeView.findViewById(R.id.recipe_img);
        final ProgressBar progressBar = (ProgressBar)recipeView.findViewById(R.id.progressBar);
        final RelativeLayout mainLayout = (RelativeLayout)recipeView.findViewById(R.id.recipe_layout);

        final RecipeAPI searchAPi = new RecipeAPI(getActivity());
        final Handler handler = new Handler();

        ConnectivityManager connMgr = (ConnectivityManager)
               getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new Thread() {
                String outof;
                JSONObject oneRecipe;
                Drawable drawable;
                @Override
                public void run() {
                    super.run();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    });
                    searchAPi.Search(MainActivity.fridgeItems);
                    try {
                       oneRecipe = searchAPi.getSearchResult().getJSONObject(0);
                        drawable = getthumbnail(oneRecipe.getString("image"));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                mCallback.getID(oneRecipe.getInt("id"));
                               // recipeID = oneRecipe.getInt("id");
                                title.setText(oneRecipe.getString("title"));
                                outof = oneRecipe.getInt("usedIngredientCount") + "/" + oneRecipe.getInt("missedIngredientCount");
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
            }.start();
        } else {
            Toast.makeText(getActivity(),"No internet connection.", Toast.LENGTH_LONG).show();
        }
        return recipeView;
    }
    public Drawable getthumbnail (final String url) {
        final InputStream[] is = {null};
                try {
                        is[0] = (InputStream) new URL(url).getContent();
                    }
                catch (IOException e) {
                        e.printStackTrace();
                    }
            Drawable d = Drawable.createFromStream(is[0], "src name");
            return d;
    }
}
