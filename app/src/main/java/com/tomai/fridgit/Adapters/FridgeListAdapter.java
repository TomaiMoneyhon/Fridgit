package com.tomai.fridgit.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tomai.fridgit.Item;
import com.tomai.fridgit.R;

import java.util.ArrayList;


/**
 * Created by admin on 9/7/15.
 */
public class FridgeListAdapter extends ArrayAdapter<Item> {

    Item oneItem;

    public FridgeListAdapter(Context context, ArrayList<Item> items) {
        super(context, R.layout.fridge_list_adapter, items);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View customView = inflater.inflate(R.layout.fridge_list_adapter, parent, false);

        oneItem = getItem(position);

        TextView nameItem = (TextView)customView.findViewById(R.id.name_item);
        TextView amountItem = (TextView)customView.findViewById(R.id.amount_item);

        nameItem.setText(oneItem.getName());
        amountItem.setText(oneItem.getAmount() + " " + oneItem.getAmountKind());

        return customView;

    }
}
