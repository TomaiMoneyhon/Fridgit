package com.tomai.fridgit;

import java.io.Serializable;

/**
 * Created by admin on 9/7/15.
 */
public class Item implements Serializable {
    private String name;
    private double amount;
    private double amountPercentage;
    private amounts amountKind;
    public enum amounts {
            Grams,
            Liters,
            Units
    }

    //TODO have a option to set either imperial or metric


    public Item(String name, double amount,  amounts amountKind) {
        this.name = name;
        this.amount = amount;
        this.amountKind = amountKind;
        amountPercentage = 1;
    }

    public Item(String name, amounts amountKind) {
        this.amountKind = amountKind;
        this.name = name;
        amountPercentage = 1;
        this.amount = -1;
    }

    public void setAmountKind(amounts amountKind) {
        this.amountKind = amountKind;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public amounts getAmountKind() {
        return amountKind;
    }

    public void changePercentage(double forRemove){
        if (amountKind == amounts.Grams) {
            forRemove = forRemove * 1000;
            amountPercentage = amountPercentage - (forRemove/amount);
        }
        else amountPercentage = amountPercentage - (forRemove/amount);
    }
}
