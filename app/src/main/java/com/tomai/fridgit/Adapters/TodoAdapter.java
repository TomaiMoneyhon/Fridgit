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
public class TodoAdapter extends ArrayAdapter<Item> {

    public TodoAdapter(Context context, ArrayList<Item> items) {
        super(context, R.layout.todo_adapter , items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.todo_adapter, parent, false);

        Item oneItem = getItem(position);

        TextView nameItem = (TextView)customView.findViewById(R.id.name_item);
        TextView amountItem = (TextView)customView.findViewById(R.id.amount_item);

        nameItem.setText(oneItem.getName());
        nameItem.setText(oneItem.getAmount() + "" + oneItem.getAmountKind());

        return customView;

    }
}
