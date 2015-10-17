package com.tomai.fridgit;

import java.io.Serializable;

/**
 * Created by admin on 9/7/15.
 */
public class Item implements Serializable {
    private String name;
    private int amount;
    private amounts amountKind;
    public enum amounts {
            Grams,
            Liters,
            Units
    }
    //TODO have a option to set either imperial or metric


    public Item(String name, int amount,  amounts amountKind) {
        this.name = name;
        this.amount = amount;
        this.amountKind = amountKind;
    }

    public Item(String name, amounts amountKind) {
        this.amountKind = amountKind;
        this.name = name;

        //TODO ask teacher if this is good practice
        this.amount = -1;
    }

    public void setAmountKind(amounts amountKind) {
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
