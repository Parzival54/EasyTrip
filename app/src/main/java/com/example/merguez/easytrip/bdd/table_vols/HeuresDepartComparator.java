package com.example.merguez.easytrip.bdd.table_vols;

import java.util.Comparator;

/**
 * Created by Jean-Philippe on 27/09/2016.
 */
public class HeuresDepartComparator implements Comparator<Vol>{
    @Override
    public int compare(Vol v1, Vol v2) {
        String heureDepart1 = v1.getHeureDepart();
        String heureDepart2 = v2.getHeureDepart();
        return heureDepart1.compareTo(heureDepart2);
    }
}
