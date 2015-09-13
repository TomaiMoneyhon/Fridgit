package com.tomai.fridgit.Adapters;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import com.tomai.fridgit.SearchAutoCompleteAPI;

import java.util.ArrayList;

/**
 * Created by admin on 9/12/15.
 */
public class SuggestionAdapter extends ArrayAdapter<String> {
    protected static final String TAG = "SuggestionAdapter";
    private ArrayList<String> suggestions;
    public SuggestionAdapter(Activity context, String nameFilter) {
        super(context, android.R.layout.simple_dropdown_item_1line);
        suggestions = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return suggestions.size();
    }

    @Override
    public String getItem(int index) {
        return suggestions.get(index);
    }

    @Override
    public Filter getFilter() {
        Filter myFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                SearchAutoCompleteAPI searchAutoCompleteAPI=new SearchAutoCompleteAPI(getContext());
                if (constraint != null) {
                    // A class that queries a web API, parses the data and
                    // returns an ArrayList<GoEuroGetSet>
                    ArrayList<String> new_suggestions =searchAutoCompleteAPI.autoComplete(constraint.toString());
                    suggestions.clear();
                    for (int i=0;i<new_suggestions.size();i++) {
                        suggestions.add(new_suggestions.get(i));
                    }

                    // Now assign the values and count to the FilterResults
                    // object
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence contraint,
                                          FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return myFilter;
    }

}

