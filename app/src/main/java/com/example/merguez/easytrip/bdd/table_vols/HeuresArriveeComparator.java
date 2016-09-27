package com.example.merguez.easytrip.bdd.table_vols;

import java.util.Comparator;

/**
 * Created by Jean-Philippe on 27/09/2016.
 */
public class HeuresArriveeComparator implements Comparator<Vol>{
    @Override
    public int compare(Vol v1, Vol v2) {
        String heureArrivee1 = v1.getHeureArrivee();
        String heureArrivee2 = v2.getHeureArrivee();
        return heureArrivee1.compareTo(heureArrivee2);
    }
}
