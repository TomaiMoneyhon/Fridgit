package com.tomai.fridgit.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.tomai.fridgit.Interfaces.DialogListener;
import com.tomai.fridgit.Item;
import com.tomai.fridgit.MainActivity;
import com.tomai.fridgit.R;

/**
 * Created by admin on 9/12/15.
 */
public class AddAmountDialog extends DialogFragment {

    private DialogListener dialogListener;
    private Item itemForEdit;
    private Activity activity;
    private static AddAmountDialogListener mListener;
    private int itemPosition;

    public static void setListener(AddAmountDialogListener listener) {
        mListener = listener;
    }

   public interface AddAmountDialogListener {
        void changeCheckedState ();
    }

    public static AddAmountDialog newAddAmountDialog(int position) {
        AddAmountDialog addAmountDialog = new AddAmountDialog();

        Bundle args = new Bundle();
        args.putInt("position", position);
        addAmountDialog.setArguments(args);

        return  addAmountDialog;
    }

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
        View view = inflater.inflate(R.layout.add_amount_dialog, null);
        builder.setView(view);


        itemPosition = getArguments().getInt("position");
        itemForEdit = MainActivity.shoppingItems.get(itemPosition);
        final EditText amountinput = (EditText)view.findViewById(R.id.amount_input);

        TextView title = (TextView)view.findViewById(R.id.add_to_title);
        title.setText("Please add amount of " + itemForEdit.getName());

        final Spinner dropDown = (Spinner)view.findViewById(R.id.drop_down);
        String[] amounts = new String[]{"Grams","Liters","Units"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,amounts);
        dropDown.setAdapter(adapter);

        Button addButton = (Button) view.findViewById(R.id.add_ingredient_btn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Item.amounts amountkind = null;
                 switch (dropDown.getSelectedItem().toString()) {
                     case "Grams":
                         itemForEdit.setAmountKind(Item.amounts.Grams);
                         break;
                     case "Liters":
                         itemForEdit.setAmountKind(Item.amounts.Liters);
                         break;
                     case "Units":
                         itemForEdit.setAmountKind(Item.amounts.Units);
                         break;
                 }

                if (amountinput.getText().toString().equals("")) {
                    amountinput.setError("No amount found");
                    amountinput.getError();
                }
                else {
                    int amount = Integer.parseInt(amountinput.getText().toString());
                    itemForEdit.setAmount(amount);
                    MainActivity.shoppingItems.remove(itemForEdit);
                    MainActivity.fridgeItems.add(itemForEdit);
                    getDialog().dismiss();
                }
            }
        });
        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mListener.changeCheckedState();
    }
}

