package com.tomai.fridgit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.tomai.fridgit.Adapters.RecipeTabAdapter;

import java.util.ArrayList;

/**
 * Created by admin on 9/22/15.
 */
public class ChoosenRecipeActivity extends ActionBarActivity {
    android.support.v7.app.ActionBar actionBar;
    public static int recipeID;
    public static ArrayList<Item> missingItems = new ArrayList<>();
    public static boolean hasIngredients;

    private ViewPager pager;
    private RecipeTabAdapter recipeTabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosen_recipe_activity);

        pager = (ViewPager)findViewById(R.id.pager_recipe);
        recipeTabAdapter = new RecipeTabAdapter(getSupportFragmentManager());

        Intent intent = getIntent();

        recipeID = intent.getIntExtra("id",0);

        pager.setAdapter(recipeTabAdapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
                //pager.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //TODO Check google play tab design. Is it worth it? (google play tab aren't deprecated).
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);

            android.support.v7.app.ActionBar.TabListener tabListener = new android.support.v7.app.ActionBar.TabListener() {
                @Override
                public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
                    pager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

                }

                @Override
                public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

                }
            };

            actionBar.addTab(actionBar.newTab().setText("Ingredients").setTabListener(tabListener));
            actionBar.addTab(actionBar.newTab().setText("Instructions").setTabListener(tabListener));
        }
    }
}
