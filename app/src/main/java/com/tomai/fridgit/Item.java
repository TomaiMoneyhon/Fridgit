package com.tomai.fridgit;

import java.io.Serializable;

/**
 * Created by admin on 9/7/15.
 */
public class Item implements Serializable {
    private String name;
    private int amount;
    amounts amountKind;
    public enum amounts {
            Grams,
            Liters,
            Units
    }
    //TODO add kind of amount (grams, liter, amount) and have a option to set either imperial or metric


    public Item(String name, int amount,  amounts amountKind) {
        this.name = name;
        this.amount = amount;
        this.amountKind = amountKind;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public amounts getAmountKind() {
        return amountKind;
    }
}
