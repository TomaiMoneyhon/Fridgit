package com.tomai.fridgit.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.tomai.fridgit.Adapters.SuggestionAdapter;
import com.tomai.fridgit.Interfaces.DialogListener;
import com.tomai.fridgit.Item;
import com.tomai.fridgit.MainActivity;
import com.tomai.fridgit.R;

/**
 * Created by admin on 9/12/15.
 */
public class AddDialog extends DialogFragment {

    private DialogListener dialogListener;
    private boolean alreadyHasIngredient = false;
    private Activity activity;

    private EditText amountinput;
    private AutoCompleteTextView autoCompleteInput;
    private Spinner dropDown;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        dialogListener = (DialogListener)activity;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_dialog,null);
        builder.setView(view);

        amountinput = (EditText)view.findViewById(R.id.amount_input);

        autoCompleteInput = (AutoCompleteTextView) view.findViewById(R.id.autocomplete_input);
        autoCompleteInput.setAdapter(new SuggestionAdapter(getActivity(), autoCompleteInput.getText().toString()));

        dropDown = (Spinner)view.findViewById(R.id.drop_down);
        String[] amounts = new String[]{"Grams","Liters","Units"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,amounts);
        dropDown.setAdapter(adapter);

        Button addButton = (Button) view.findViewById(R.id.add_ingredient_btn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = autoCompleteInput.getText().toString();
                Item.amounts amountkind = null;
                 switch (dropDown.getSelectedItem().toString()) {
                     case "Grams":
                         amountkind = Item.amounts.Grams;
                         break;
                     case "Liters":
                         amountkind = Item.amounts.Liters;
                         break;
                     case "Units":
                         amountkind = Item.amounts.Units;
                         break;
                 }
                if (name.equals("")){
                    autoCompleteInput.setError("No ingridient found");
                    autoCompleteInput.getError();
                }
                else if (amountinput.getText().toString().equals("")) {
                    Item createdItem = new Item(name, amountkind);
                    checkCreatedItem(createdItem);
                }
                else {
                    double amount = Double.parseDouble(amountinput.getText().toString());
                    Item createdItem = new Item(name, amount, amountkind);
                    checkCreatedItem(createdItem);
                }
            }
        });
        return builder.create();
    }

    public void checkCreatedItem (Item createdItem) {
        for(int i = 0; MainActivity.shoppingItems.size() > i ; i++){
            if(MainActivity.shoppingItems.get(i).getName().equals(createdItem.getName())){
                alreadyHasIngredient = true;
            }
        }
        if(alreadyHasIngredient){
            autoCompleteInput.setError("You already have this in your fridge");
            autoCompleteInput.getError();
        }
        else {
            dialogListener.OnAddListener(createdItem);
            getDialog().dismiss();
        }
    }
}
