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

import com.tomai.fridgit.Adapters.FridgeListAdapter;
import com.tomai.fridgit.Adapters.ShoppingListAdapter;
import com.tomai.fridgit.Adapters.SuggestionAdapter;
import com.tomai.fridgit.Interfaces.DialogListener;
import com.tomai.fridgit.Item;
import com.tomai.fridgit.MainActivity;
import com.tomai.fridgit.R;

/**
 * Created by admin on 9/23/15.
 */
public class EditDialog extends DialogFragment {

    private DialogListener dialogListener;
    private String fromList;
    private boolean alreadyHasIgredient = false;
    private Item itemForEdit;

    private AutoCompleteTextView nameInput;
    private EditText amountInput;
    private Spinner dropDown;

    public static EditDialog newEditDialog(int position, String list) {
        EditDialog editDialog = new EditDialog();

        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("list", list);
        editDialog.setArguments(args);

        return  editDialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        dialogListener = (DialogListener)activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view =  inflater.inflate(R.layout.edit_dialog, null);
        builder.setView(view);

        int itemPosition = getArguments().getInt("position");
        fromList = getArguments().getString("list");
        itemForEdit = null;
        switch (fromList) {
            case ShoppingListAdapter.SHOPPINGLISTKEY:
                itemForEdit = MainActivity.shoppingItems.get(itemPosition);
                break;
            case FridgeListAdapter.FRIDGELISTKEY:
                itemForEdit = MainActivity.fridgeItems.get(itemPosition);
                break;
        }

        nameInput = (AutoCompleteTextView)view.findViewById(R.id.autocomplete_input);
        nameInput.setAdapter(new SuggestionAdapter(getActivity(), nameInput.getText().toString()));
        nameInput.setText(itemForEdit.getName());

        amountInput = (EditText)view.findViewById(R.id.amount_input);

        if (itemForEdit.getAmount() == -1) {
            amountInput.setText("");
        }
        else amountInput.setText(itemForEdit.getAmount() + "");

        dropDown = (Spinner)view.findViewById(R.id.drop_down);
        String[] amounts = new String[]{"Grams","Liters","Units"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,amounts);
        dropDown.setAdapter(adapter);

        switch (itemForEdit.getAmountKind().toString()) {
            case "Grams":
                dropDown.setSelection(adapter.getPosition("Grams"));
                break;
            case "Liters":
                dropDown.setSelection(adapter.getPosition("Liters"));
                break;
            case "Units":
                dropDown.setSelection(adapter.getPosition("Units"));
                break;
        }

        Button addButton = (Button) view.findViewById(R.id.add_ingredient_btn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString();
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
                if (name.equals("")) {
                    nameInput.setError("No ingridient found");
                    nameInput.getError();
                } else if (amountInput.getText().toString().equals("")) {
                    amountInput.setError("No amount found");
                    amountInput.getError();
                } else {
                    double amount = Double.parseDouble(amountInput.getText().toString());
                    Item editedItem = new Item(name, amount, amountkind);
                    switch (fromList){
                        case ShoppingListAdapter.SHOPPINGLISTKEY:
                            for(int i = 0; MainActivity.shoppingItems.size() > i ; i++){
                                if(MainActivity.shoppingItems.get(i).getName().equals(itemForEdit.getName())) {
                                    alreadyHasIgredient = false;
                                }
                                else if(MainActivity.shoppingItems.get(i).getName().equals(editedItem.getName())){
                                    alreadyHasIgredient = true;
                                }
                            }
                            break;
                        case FridgeListAdapter.FRIDGELISTKEY:
                            for(int i = 0; MainActivity.fridgeItems.size() > i ; i++){
                                if(MainActivity.fridgeItems.get(i).getName().equals(itemForEdit.getName())) {
                                    alreadyHasIgredient = false;
                                }
                                else if(MainActivity.fridgeItems.get(i).getName().equals(editedItem.getName())){
                                    alreadyHasIgredient = true;
                                }
                            }
                            break;
                    }

                    if(alreadyHasIgredient){
                        nameInput.setError("You already have this ingredient in the list");
                        nameInput.getError();
                    }
                    else {
                        dialogListener.OnEditListener(itemForEdit, editedItem, fromList);
                        getDialog().dismiss();
                    }

                }
            }
        });

        Button deleteBTN = (Button)view.findViewById(R.id.delete_ingredient_btn);
        final Item finalItemForEdit1 = itemForEdit;
        deleteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListener.OnDeleteListener(finalItemForEdit1,fromList);
                getDialog().dismiss();
            }
        });

        return builder.create();
    }
}
