package com.tomai.fridgit.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.tomai.fridgit.Dialogs.AddAmountDialog;
import com.tomai.fridgit.Dialogs.EditDialog;
import com.tomai.fridgit.Item;
import com.tomai.fridgit.MainActivity;
import com.tomai.fridgit.R;

import java.util.ArrayList;


/**
 * Created by admin on 9/7/15.
 */
public class ShoppingListAdapter extends ArrayAdapter<Item> implements AddAmountDialog.AddAmountDialogListener{
    public static final String SHOPPINGLISTKEY = "shopping";
    private Item oneItem;
    private Handler handler = new Handler();
    private Activity activity;
    private Item checkedItem;
    private CheckBox checkBox;

    public ShoppingListAdapter(Context context, ArrayList<Item> items) {
        super(context, R.layout.shopping_list_adapter, items);
        activity = (Activity)context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View customView = inflater.inflate(R.layout.shopping_list_adapter, parent, false);

        oneItem = getItem(position);

        TextView nameItem = (TextView)customView.findViewById(R.id.name_item);
        TextView amountItem = (TextView)customView.findViewById(R.id.amount_item);

        nameItem.setText(oneItem.getName());

        if (oneItem.getAmount() == -1) {
            amountItem.setVisibility(View.INVISIBLE);
        }

        else {
            amountItem.setVisibility(View.VISIBLE);
            amountItem.setText(oneItem.getAmount() + " " + oneItem.getAmountKind());
        }

        checkBox = (CheckBox)customView.findViewById(R.id.checkbox_item);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkedItem = MainActivity.shoppingItems.get(position);
                if (isChecked) {
                    if (checkedItem.getAmount() == -1) {
                        AddAmountDialog addAmountDialog = AddAmountDialog.newAddAmountDialog(position);
                        AddAmountDialog.setListener(ShoppingListAdapter.this);
                        addAmountDialog.show(activity.getFragmentManager(), "addAmountDialog");
                    } else {
                        new Thread() {
                            @Override
                            public void run() {
                                try {
                                    sleep(500, 0);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        customView.setVisibility(View.GONE);
                                    }
                                });
                                MainActivity.shoppingItems.remove(checkedItem);
                                MainActivity.fridgeItems.add(checkedItem);
                            }
                        }.start();
                    }
                }
            }
        });

        Button editshoppingBTN = (Button)customView.findViewById(R.id.edit_shopping_btn_item);
        editshoppingBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDialog editDialog = EditDialog.newEditDialog(position,SHOPPINGLISTKEY);
                editDialog.show(activity.getFragmentManager(),"editDialog");
            }
        });

        return customView;

    }

    public void changeCheckedState () {
       if(checkedItem.getAmount() == -1) {
           checkBox.setChecked(false);
           this.notifyDataSetChanged();
       }
    }
}
