package com.example.merguez.easytrip.bdd.table_vols;

import java.util.Comparator;

/**
 * Created by Jean-Philippe on 27/09/2016.
 */
public class PrixComparator implements Comparator<Vol> {
    @Override
    public int compare(Vol v1, Vol v2) {
        double diff = v2.getPrix()-v1.getPrix();
        if (diff<0)
            return -1;
        else if (diff>0)
            return 1;
        else
            return 0;
    }
}
