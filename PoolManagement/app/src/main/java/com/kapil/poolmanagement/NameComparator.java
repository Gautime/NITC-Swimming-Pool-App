package com.kapil.poolmanagement;

import java.util.Comparator;

public class NameComparator implements Comparator<useritem> {

    @Override
    public int compare(useritem o1, useritem o2) {
        //Comparing year
        return o1.names.compareToIgnoreCase(o2.names); // To compare string values
        //return 1;
        }
}
