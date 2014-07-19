package com.example.guessnumberapp.app;

import java.util.Comparator;

class ScoreCompare implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        // write comparison logic here like below , it's just a sample
        String or1 = o1.substring(o1.length()-1,o1.length());
        String or2 = o2.substring(o2.length()-1,o2.length());
        return or2.compareTo(or1);
    }
}
