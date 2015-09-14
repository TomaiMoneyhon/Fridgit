package com.tomai.fridgit;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.tomai.fridgit.Adapters.TabAdapter;
import com.tomai.fridgit.Dialogs.AddDialog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements AddDialog.OnAddDialogListener {
    android.support.v7.app.ActionBar actionBar;

    String SHOPPINGLIST_FILENAME = "shoppingList.dat";
    String FRIDGELIST_FILENAME = "fridgeList.dat";

    public static ArrayList<Item> fridgeItems;
    public static ArrayList<Item> shoppingItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO add a internal storage checker for "frideItems" and "shoppingItems" arrays lists.
        FileInputStream shoppingFIS = null;
        ObjectInputStream shoppingOIS = null;
        shoppingItems = null;

        FileInputStream fridgeFIS = null;
        ObjectInputStream fridgeOIS = null;
        fridgeItems = null;

        try {
            shoppingFIS = openFileInput(SHOPPINGLIST_FILENAME);
            shoppingOIS = new ObjectInputStream(shoppingFIS);
            shoppingItems = (ArrayList<Item>)shoppingOIS.readObject();
            fridgeFIS = openFileInput(FRIDGELIST_FILENAME);
            fridgeOIS = new ObjectInputStream(fridgeFIS);
            fridgeItems = (ArrayList<Item>)shoppingOIS.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
             try {
                 if (shoppingOIS != null) shoppingOIS.close();
                 if (fridgeOIS != null) fridgeOIS.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(shoppingItems == null) shoppingItems = new ArrayList<>();
        if(fridgeItems == null) fridgeItems = new ArrayList<>();

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
                pager.getAdapter().notifyDataSetChanged();

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

            actionBar.addTab(actionBar.newTab().setText("Shopping List").setTabListener(tabListener));
            actionBar.addTab(actionBar.newTab().setText("Fridge List").setTabListener(tabListener));
        }

        FloatingActionButton floatingButton = (FloatingActionButton)findViewById(R.id.fab);

        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment addIngredient = new AddDialog();
                addIngredient.show(getFragmentManager(),"addDialog");

            }
        });

        Button makeFoodBTN = (Button)findViewById(R.id.get_food_btn);
        makeFoodBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Start connecting 'Make food' button to search api from fridge list
            }
        });






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
    protected void onPause() {
        super.onPause();
        //TODO Save to internal storage "frideItems" and "shoppingItems" arrays lists.
        FileOutputStream shoppingFOS = null;
        ObjectOutputStream shoppingOOS = null;

        FileOutputStream fridgeFOS = null;
        ObjectOutputStream fridgeOOS = null;

        try {
            shoppingFOS = openFileOutput(SHOPPINGLIST_FILENAME,MODE_PRIVATE);
            shoppingOOS = new ObjectOutputStream(shoppingFOS);
            shoppingOOS.writeObject(shoppingItems);

            fridgeFOS = openFileOutput(FRIDGELIST_FILENAME,MODE_PRIVATE);
            fridgeOOS = new ObjectOutputStream(fridgeFOS);
            fridgeOOS.writeObject(fridgeItems);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
             try {
                 if (shoppingOOS != null) shoppingOOS.close();
                 if (fridgeOOS != null) fridgeOOS.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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

    @Override
    public void OnAddListener(Item item) {
        shoppingItems.add(item);
    }
}
