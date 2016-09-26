package com.example.merguez.easytrip.bdd.table_vols;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.merguez.easytrip.bdd.ListeTablesBDD;
import com.example.merguez.easytrip.bdd.table_aeroports.Aeroport;

import java.util.ArrayList;

/**
 * Created by merguez on 13/09/2016.
 */
public class VolBDD extends ListeTablesBDD {

    private static final String TABLE_VOLS = "vols";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_AEROPORT_DEPART = "AEROPORT_DEPART";
    private static final int NUM_COL_AEROPORT_DEPART = 1;
    private static final String COL_AEROPORT_ARRIVEE = "AEROPORT_ARRIVEE";
    private static final int NUM_COL_AEROPORT_ARRIVEE = 2;
    private static final String COL_HEURE_DEPART = "HEURE_DEPART";
    private static final int NUM_COL_HEURE_DEPART = 3;
    private static final String COL_HEURE_ARRIVEE = "HEURE_ARRIVEE";
    private static final int NUM_COL_HEURE_ARRIVEE = 4;
    private static final String COL_COMPAGNIE_ID = "ID_COMPAGNIE";
    private static final int NUM_COL_COMPAGNIE_ID = 5;
    private static final String COL_CLASSE_ID = "ID_CLASSE";
    private static final int NUM_COL_CLASSE_ID = 6;
    private static final String COL_PRIX = "PRIX";
    private static final int NUM_COL_PRIX = 7;

    public VolBDD(Context context) {
        super(context);
    }

    public static String getTableVols() {
        return TABLE_VOLS;
    }

    public static String getColId() {
        return COL_ID;
    }

    public static int getNumColId() {
        return NUM_COL_ID;
    }

    public static String getColAeroportDepart() {
        return COL_AEROPORT_DEPART;
    }

    public static int getNumColAeroportDepart() {
        return NUM_COL_AEROPORT_DEPART;
    }

    public static String getColAeroportArrivee() {
        return COL_AEROPORT_ARRIVEE;
    }

    public static int getNumColAeroportArrivee() {
        return NUM_COL_AEROPORT_ARRIVEE;
    }

    public static String getColHeureDepart() {
        return COL_HEURE_DEPART;
    }

    public static int getNumColHeureDepart() {
        return NUM_COL_HEURE_DEPART;
    }

    public static String getColHeureArrivee() {
        return COL_HEURE_ARRIVEE;
    }

    public static int getNumColHeureArrivee() {
        return NUM_COL_HEURE_ARRIVEE;
    }

    public static String getColCompagnieId() {
        return COL_COMPAGNIE_ID;
    }

    public static int getNumColCompagnieId() {
        return NUM_COL_COMPAGNIE_ID;
    }

    public static String getColClasseId() {
        return COL_CLASSE_ID;
    }

    public static int getNumColClasseId() {
        return NUM_COL_CLASSE_ID;
    }

    public static String getColPrix() {
        return COL_PRIX;
    }

    public static int getNumColPrix() {
        return NUM_COL_PRIX;
    }

    public static long insertVol(Vol vol) {
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_AEROPORT_DEPART, vol.getAeroportDepart());
        values.put(COL_AEROPORT_ARRIVEE, vol.getAeroportArrivee());
        values.put(COL_HEURE_DEPART, vol.getHeureDepart());
        values.put(COL_HEURE_ARRIVEE, vol.getHeureArrivee());
        values.put(COL_COMPAGNIE_ID, vol.getCompagnieID());
        values.put(COL_CLASSE_ID, vol.getClasseID());
        values.put(COL_PRIX, vol.getPrix());
        //on insère l'objet dans la BDD via le ContentValues
        return ListeTablesBDD.getBdd().insert(TABLE_VOLS, null, values);
    }

    public static int updateAeroport(Vol vol) {
        int id = vol.getId();
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_AEROPORT_DEPART, vol.getAeroportDepart());
        values.put(COL_AEROPORT_ARRIVEE, vol.getAeroportArrivee());
        values.put(COL_HEURE_DEPART, vol.getHeureDepart());
        values.put(COL_HEURE_ARRIVEE, vol.getHeureArrivee());
        values.put(COL_COMPAGNIE_ID, vol.getCompagnieID());
        values.put(COL_CLASSE_ID, vol.getClasseID());
        values.put(COL_PRIX, vol.getPrix());
        return ListeTablesBDD.getBdd().update(TABLE_VOLS, values, COL_ID + " = " + id, null);
    }

    public static int removeAeroportWithID(Aeroport aeroport) {
        int id = aeroport.getId();
        //Suppression d'un livre de la BDD grâce à l'ID
        return ListeTablesBDD.getBdd().delete(TABLE_VOLS, COL_ID + " = " + id, null);
    }

    public static int removeAll() {
        //Suppression d'un livre de la BDD grâce à l'ID
        return ListeTablesBDD.getBdd().delete(TABLE_VOLS, null, null);
    }

    public static Vol getVolWithID(int id) {
        Cursor cursor = ListeTablesBDD.getBdd().query(TABLE_VOLS,new String[] {COL_ID, COL_AEROPORT_DEPART, COL_AEROPORT_ARRIVEE, COL_HEURE_DEPART, COL_HEURE_ARRIVEE,
                COL_COMPAGNIE_ID, COL_CLASSE_ID, COL_PRIX},
                COL_ID + " LIKE \"" + id + "\"", null, null, null, null);
        return cursorToVol(cursor);
    }



    private static Vol cursorToVol(Cursor c) {
        Vol vol = new Vol();
        try {
            c.moveToFirst();
            vol = new Vol();
            vol.setID(c.getInt(NUM_COL_ID));
            vol.setAeroportDepart(c.getString(NUM_COL_AEROPORT_DEPART));
            vol.setAeroportArrivee(c.getString(NUM_COL_AEROPORT_ARRIVEE));
            vol.setHeureDepart(c.getString(NUM_COL_HEURE_DEPART));
            vol.setHeureArrivee(c.getString(NUM_COL_HEURE_ARRIVEE));
            vol.setCompagnieID(c.getInt(NUM_COL_COMPAGNIE_ID));
            vol.setClasseID(c.getInt(NUM_COL_CLASSE_ID));
            vol.setPrix(c.getInt(NUM_COL_PRIX));
        } finally {
            c.close();
            return vol;
        }
    }
}
