package com.tomai.fridgit.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tomai.fridgit.ChoosenRecipeActivity;
import com.tomai.fridgit.Converters;
import com.tomai.fridgit.Item;
import com.tomai.fridgit.MainActivity;
import com.tomai.fridgit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 9/23/15.
 */
public class IngredientsForRecipeListAdapter extends ArrayAdapter<JSONObject> {

    public IngredientsForRecipeListAdapter(Context context, ArrayList<JSONObject> resource) {
        super(context, R.layout.ingredients_for_recipe_list_adapter ,resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View customView = inflater.inflate(R.layout.ingredients_for_recipe_list_adapter, parent, false);

        JSONObject oneItem = getItem(position);

        TextView nameItem = (TextView)customView.findViewById(R.id.name_ingredient);
        TextView amountItem = (TextView)customView.findViewById(R.id.amount_ingredient);

        String nameIngredient = null;
        int amount = 0;
        String amountKind = null;
        try {
            nameIngredient = oneItem.getString("name");
            amount =  oneItem.getInt("amount");
            amountKind = oneItem.getString("unitLong");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        nameItem.setTextColor(Color.RED);
        for (int i = 0 ; MainActivity.fridgeItems.size() > i ; i++) {
            //TODO Decide were the color should be represented. in the background or on the text (For example) ?
            if (MainActivity.fridgeItems.get(i).getName().equals(nameIngredient)) {
                nameItem.setTextColor(Color.GREEN);
                ChoosenRecipeActivity.hasIngredients = false;
                //TODO amount converter
//                Item missingItem = new Item(nameIngredient,amount,amountKind);
//                ChoosenRecipeActivity.missingItems.add(missingItem);
            }
        }
            nameItem.setText(nameIngredient);
            amountItem.setText( amount + " " + amountKind);

        return customView;
    }
}
