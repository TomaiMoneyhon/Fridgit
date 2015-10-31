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

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by admin on 9/23/15.
 */
public class IngredientsForRecipeListAdapter extends ArrayAdapter<JSONObject> {

    private boolean available = false;
    private String amountKind;
    private String nameIngredient;
    private Double amount;
    private DecimalFormat decimalFormat = new DecimalFormat("#");

    private View customView;


    private Converters converters;

    public IngredientsForRecipeListAdapter(Context context, ArrayList<JSONObject> resource) {
        super(context, R.layout.ingredients_for_recipe_list_adapter ,resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        customView = inflater.inflate(R.layout.ingredients_for_recipe_list_adapter, parent, false);

        JSONObject oneItem = getItem(position);

        TextView nameItem = (TextView)customView.findViewById(R.id.name_ingredient);
        TextView amountItem = (TextView)customView.findViewById(R.id.amount_ingredient);

        try {
            nameIngredient = oneItem.getString("name");
            if(oneItem.getDouble("amount") >= 1)amount = Double.parseDouble(decimalFormat.format(oneItem.getDouble("amount")));
            else amount =  oneItem.getDouble("amount");
            amountKind = oneItem.getString("unit");//TODO Check consistancy (maybe should change to "unitLong"
            //TODO check if amountKind is "" (empty) then it should show text of amount of some sort
        } catch (JSONException e) {
            e.printStackTrace();
        }

        nameItem.setTextColor(Color.RED);
        for (int i = 0 ; MainActivity.fridgeItems.size() > i ; i++) {
            Item checkingItem = MainActivity.fridgeItems.get(i);
            //TODO Decide were the color should be represented. in the background or on the text (For example) ?
            if (checkingItem.getName().equals(nameIngredient)) {

                double amountNeeded = 0;
                try {
                    amountNeeded = checkAmountKind(amount,oneItem.getString("unitShort"),checkingItem);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (amountNeeded > checkingItem.getAmount()){
                    nameItem.setTextColor(Color.rgb(255,153,0));
                }
                else {
                    nameItem.setTextColor(Color.GREEN);
                    available = true;
                }
            }
        }
        if(!available){
            Item missingIngredient = new Item (nameIngredient, Item.amounts.Units);
            ChoosenRecipeActivity.missingItems.add(missingIngredient);
            ChoosenRecipeActivity.hasIngredients = false;
        }else available = false;

            nameItem.setText(nameIngredient);
            amountItem.setText(amount + " " + amountKind);

        return customView;
    }
    public double checkAmountKind (Double startingAmount , String startingUnit, Item itemForCheck){
        converters = new Converters(getContext());
        Double resultConverted = startingAmount;

            switch(startingUnit)

            {

                //teaspoon
                case "t":
                    resultConverted = Double.parseDouble(converters.cookingUnitConverter(startingAmount, "teaspoonUK", "liter", Converters.ConverterKind.CookingUnits));

                    break;
                //tablespoon
                case "T":
                    resultConverted = Double.parseDouble(converters.cookingUnitConverter(startingAmount, "tablespoonUK", "liter", Converters.ConverterKind.CookingUnits));

                    break;
                //cups
                case "c":
                    resultConverted = Double.parseDouble(converters.cookingUnitConverter(startingAmount, "cupUS", "liter", Converters.ConverterKind.CookingUnits));

                    break;
                //pound
                case "lb":
                    resultConverted = Double.parseDouble(converters.cookingUnitConverter(startingAmount, "PoundsTroy", "Grams", Converters.ConverterKind.WeightUnits));

                    break;
                //servings
                case "servings":

                    break;
                //ounces
                case "oz":
                    resultConverted = Double.parseDouble(converters.cookingUnitConverter(startingAmount, "OuncesTroyApoth", "Grams", Converters.ConverterKind.WeightUnits));

                    break;
                //bunch
                case "bunch":

                    break;
                //dash
                case "dash":
                    resultConverted = Double.parseDouble(converters.cookingUnitConverter(startingAmount, "dash", "liter", Converters.ConverterKind.CookingUnits));

                    break;
                //slices
                case "slices":

                    break;
                //cloves
                case "cloves":

                    break;
                //normal units
                case "":

                    break;

            }
        double checked = check(resultConverted,itemForCheck);
        return checked;
    }
    public double check (double forCheck, Item itemForCheck) {
        if (itemForCheck.getAmountKind() == Item.amounts.Grams) {
            return forCheck * 1000;
        } else return forCheck;
    }
}
