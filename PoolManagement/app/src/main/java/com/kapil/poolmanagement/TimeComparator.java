package com.kapil.poolmanagement;

import java.util.Comparator;

public class TimeComparator implements Comparator<checkinitem> {

    @Override
    public int compare(checkinitem o1, checkinitem o2) {
        int yo1 = Integer.parseInt(o1.times.substring(0,2));
        int yo2 = Integer.parseInt(o2.times.substring(0,2));

        if(yo1 > yo2) {
            return -1;
        }
        else if(yo1 < yo2) {
            return 1;
        }

        //Comparing minutes
        yo1 = Integer.parseInt(o1.times.substring(3,5));
        yo2 = Integer.parseInt(o2.times.substring(3,5));

        if(yo1 > yo2) {
            return -1;
        }
        else if(yo1 < yo2) {
            return 1;
        }

        //Comparing seconds
        yo1 = Integer.parseInt(o1.times.substring(6,8));
        yo2 = Integer.parseInt(o2.times.substring(6,8));

        if(yo1 > yo2) {
            return -1;
        }
        else if(yo1 < yo2) {
            return 1;
        }


        //Equal Objects
        return 0;
    }
}
