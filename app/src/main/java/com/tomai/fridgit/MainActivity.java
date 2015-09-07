package com.tomai.fridgit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.tomai.fridgit.Adapters.TabAdapter;
import com.tomai.fridgit.Adapters.TodoAdapter;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    android.support.v7.app.ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ViewPager pager = (ViewPager)findViewById(R.id.pager);
        final TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());

        pager.setAdapter(tabAdapter);

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //TODO Check google play tab design. Is it worth it? (google play tab aren't deprecated.
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

            actionBar.addTab(actionBar.newTab().setText("Shopping List").setTabListener(tabListener));
            actionBar.addTab(actionBar.newTab().setText("Fridge List").setTabListener(tabListener));
        }

        FloatingActionButton floatingButton = (FloatingActionButton)findViewById(R.id.fab);

        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Create fragment for search that will connect to ingridients api and add to shopping list
            }
        });
        //TODO Start connecting 'Make food' button to search api from fridge list





        ///////USE FOR API FOR SEARCH RECIPES
//        RecipeAPI searchAPi = new RecipeAPI(this);
//        String[] strings = {"Ham"};
//        Handler handler = new Handler();
//      final TextView result = (TextView)findViewById(R.id.result);
//
//        ConnectivityManager connMgr = (ConnectivityManager)
//                getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected()) {
//            new Thread() {
//                @Override
//                public void run() {
//                    super.run();
//                    searchAPi.Search(strings);
//
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                result.setText(searchAPi.getSearchResult().get(0).toString());
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                }
//            }.start();
//        } else {
//            Toast.makeText(this,"No internet connection.",Toast.LENGTH_LONG).show();
//        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
