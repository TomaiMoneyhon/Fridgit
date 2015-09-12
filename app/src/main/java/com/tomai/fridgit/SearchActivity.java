package com.tomai.fridgit;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.tomai.fridgit.Adapters.SuggestionAdapter;

import java.util.ArrayList;

/**
 * Created by admin on 9/12/15.
 */
public class SearchActivity extends Activity {
    SearchAutoCompleteAPI searchAutoCompleteAPI = new SearchAutoCompleteAPI(this);
    Handler handler = new Handler();
    ArrayList<String> autoCompleteResults = new ArrayList<>();

    AutoCompleteTextView autoCompleteInput;
    Button addButton;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        autoCompleteInput = (AutoCompleteTextView)findViewById(R.id.autocomplete_input);
        autoCompleteInput.setAdapter(new SuggestionAdapter(this,autoCompleteInput.getText().toString()));



//        autoCompleteInput.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(final CharSequence s, int start, int before, int count) {
//                new Thread() {
//                    @Override
//                    public void run() {
//                        super.run();
//                        autoCompleteResults = searchAutoCompleteAPI.autoComplete(s.toString());
//                        String [] autoCompleteStringarray = new String[autoCompleteResults.size()];
//                        autoCompleteStringarray = autoCompleteResults.toArray(autoCompleteStringarray);
//                        adapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_dropdown_item_1line, autoCompleteStringarray);
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                autoCompleteInput.setAdapter(adapter);
//                            }
//                        });
//                    }}.start();
//            }
//
//            @Override
//            public void afterTextChanged(final Editable s) {
//
//                //adapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_dropdown_item_1line, (String[]) searchAutoCompleteAPI.autoComplete(s.toString()).toArray());
//            }
//        });

        addButton = (Button)findViewById(R.id.add_ingredient_btn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
