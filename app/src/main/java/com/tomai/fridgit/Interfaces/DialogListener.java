package com.tomai.fridgit.Interfaces;

import com.tomai.fridgit.Item;

/**
 * Created by admin on 9/23/15.
 */
public interface DialogListener {
    void OnAddListener (Item item);
    void OnEditListener (Item original, Item afterEdit, String fromList);
    void OnDeleteListener (Item item, String fromList);
}
