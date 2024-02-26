package com.salazarisaiahnoel.shoppinglist.others;

import androidx.annotation.NonNull;

public class StringTriple {
    public final String data;
    public final String number;
    public final String price;

    public StringTriple(String data, String number, String price){
        this.data = data;
        this.number = number;
        this.price = price;
    }

    @NonNull
    public String toString(){
        return "(" + this.data + ", " + this.number + ", " + this.price + ")";
    }
}
