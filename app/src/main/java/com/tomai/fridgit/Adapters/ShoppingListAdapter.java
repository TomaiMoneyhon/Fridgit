package com.tomai.fridgit.Adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.tomai.fridgit.Fragments.FridgeListFragment;
import com.tomai.fridgit.Item;
import com.tomai.fridgit.MainActivity;
import com.tomai.fridgit.R;

import java.util.ArrayList;


/**
 * Created by admin on 9/7/15.
 */
public class ShoppingListAdapter extends ArrayAdapter<Item> {
    Activity activity;
    Item oneItem;

    Handler handler = new Handler();

    public ShoppingListAdapter(Context context, ArrayList<Item> items) {
        super(context, R.layout.shopping_list_adapter, items);
        activity = (MainActivity) context;
    }

    TodoAdapterListenner todoAdapterListener;

    public interface TodoAdapterListenner {
        void getCheckedItem (Item item);
    }



    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View customView = inflater.inflate(R.layout.shopping_list_adapter, parent, false);

        oneItem = getItem(position);

        TextView nameItem = (TextView)customView.findViewById(R.id.name_item);
        TextView amountItem = (TextView)customView.findViewById(R.id.amount_item);

        nameItem.setText(oneItem.getName());
        amountItem.setText(oneItem.getAmount() + " " + oneItem.getAmountKind());


        final CheckBox checkBox = (CheckBox)customView.findViewById(R.id.checkbox_item);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBox.setChecked(true);

                if (isChecked) {
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
                            MainActivity.fridgeItems.add(oneItem);
                        }
                    }.start();

                   activity.getFragmentManager().findFragmentByTag("fridge_frag");

                }
            }
        });

        return customView;

    }
}
