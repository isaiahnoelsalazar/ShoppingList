package com.salazarisaiahnoel.shoppinglist.others;

import java.util.Comparator;

public class StringTripleComparator implements Comparator<StringTriple> {
    @Override
    public int compare(StringTriple o1, StringTriple o2) {
        return o1.toString().compareTo(o2.toString());
    }
}
