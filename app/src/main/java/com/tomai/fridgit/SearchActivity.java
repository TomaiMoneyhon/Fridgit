package com.tomai.fridgit;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.tomai.fridgit.Adapters.SuggestionAdapter;

/**
 * Created by admin on 9/12/15.
 */
public class SearchActivity extends Activity {

    AutoCompleteTextView autoCompleteInput;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        autoCompleteInput = (AutoCompleteTextView)findViewById(R.id.autocomplete_input);
        autoCompleteInput.setAdapter(new SuggestionAdapter(this,autoCompleteInput.getText().toString()));


        addButton = (Button)findViewById(R.id.add_ingredient_btn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
