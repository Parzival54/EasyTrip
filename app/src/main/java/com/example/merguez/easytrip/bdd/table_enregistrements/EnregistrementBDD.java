package com.example.merguez.easytrip.bdd.table_enregistrements;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.merguez.easytrip.bdd.ListeTablesBDD;
import com.example.merguez.easytrip.bdd.table_users.User;

import java.util.ArrayList;

/**
 * Created by grap on 23/09/2016.
 */
public class EnregistrementBDD extends ListeTablesBDD {
    private static final String TABLE_ENREGISTREMENTS = "enregistrements";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_USERID = "USER_ID";
    private static final int NUM_COL_USERID = 1;
    private static final String COL_VOL_ALLER_ID = "VOL_ALLER_ID";
    private static final int NUM_COL_VOL_ALLER_ID = 2;
    private static final String COL_VOL_RETOUR_ID = "VOL_RETOUR_ID";
    private static final int NUM_COL_VOL_RETOUR_ID = 3;
    private static final String COL_NB_ADULTES = "NB_ADULTES";
    private static final int NUM_COL_NB_ADULTES = 4;
    private static final String COL_NB_ENFANTS = "NB_ENFANTS";
    private static final int NUM_COL_NB_ENFANTS = 5;
    private static final String COL_PRIX = "PRIX_TOTAL";
    private static final int NUM_COL_PRIX = 6;
    private static final String COL_DATE_CREATION = "DATE_CREATION";
    private static final int NUM_COL_DATE_CREATION = 7;


    public EnregistrementBDD(Context context) {
        super(context);
    }

    public static String getTableEnregistrements() {
        return TABLE_ENREGISTREMENTS;
    }

    public static String getColId() {
        return COL_ID;
    }

    public static int getNumColId() {
        return NUM_COL_ID;
    }

    public static String getColUserid() {
        return COL_USERID;
    }

    public static int getNumColUserid() {
        return NUM_COL_USERID;
    }

    public static String getColVolAllerId() {
        return COL_VOL_ALLER_ID;
    }

    public static int getNumColVolAllerId() {
        return NUM_COL_VOL_ALLER_ID;
    }

    public static String getColVolRetourId() {
        return COL_VOL_RETOUR_ID;
    }

    public static int getNumColVolRetourId() {
        return NUM_COL_VOL_RETOUR_ID;
    }

    public static String getColNbAdultes() {
        return COL_NB_ADULTES;
    }

    public static int getNumColNbAdultes() {
        return NUM_COL_NB_ADULTES;
    }

    public static String getColNbEnfants() {
        return COL_NB_ENFANTS;
    }

    public static int getNumColNbEnfants() {
        return NUM_COL_NB_ENFANTS;
    }

    public static String getColPrix() {
        return COL_PRIX;
    }

    public static int getNumColPrix() {
        return NUM_COL_PRIX;
    }

    public static String getColDateCreation() {
        return COL_DATE_CREATION;
    }

    public static int getNumColDateCreation() {
        return NUM_COL_DATE_CREATION;
    }

    public static long insertEnregistrement(Enregistrement enregistrement){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_USERID, enregistrement.getUserID());
        values.put(COL_VOL_ALLER_ID, enregistrement.getVolAllerID());
        values.put(COL_VOL_RETOUR_ID, enregistrement.getVolRetourID());
        values.put(COL_NB_ADULTES, enregistrement.getNbAdultes());
        values.put(COL_NB_ENFANTS, enregistrement.getNbEnfants());
        values.put(COL_PRIX, enregistrement.getPrixTotal());
        values.put(COL_DATE_CREATION, enregistrement.getDateCreation());
        //on insère l'objet dans la BDD via le ContentValues
        return ListeTablesBDD.getBdd().insert(TABLE_ENREGISTREMENTS, null, values);
    }

    public static ArrayList<Enregistrement> getEnregistrementsWithUserID(int userID) {
        Cursor cursor = ListeTablesBDD.getBdd().query(TABLE_ENREGISTREMENTS,new String[] {COL_ID, COL_USERID, COL_VOL_ALLER_ID, COL_VOL_RETOUR_ID, COL_NB_ADULTES, COL_NB_ENFANTS,
                COL_PRIX, COL_DATE_CREATION},
                COL_USERID + " = " + userID, null, null, null, null);
        return cursorToEnregistrement(cursor);
    }



    private static ArrayList<Enregistrement> cursorToEnregistrement(Cursor c) {
        ArrayList<Enregistrement> listeEnregistrement = new ArrayList<>();
        try {
            while (c.moveToNext()){
                Enregistrement enregistrement = new Enregistrement();
                enregistrement.setId(c.getInt(NUM_COL_ID));
                enregistrement.setUserID(c.getInt(NUM_COL_USERID));
                enregistrement.setVolAllerID(c.getInt(NUM_COL_VOL_ALLER_ID));
                enregistrement.setVolRetourID(c.getInt(NUM_COL_VOL_RETOUR_ID));
                enregistrement.setNbAdultes(c.getInt(NUM_COL_NB_ADULTES));
                enregistrement.setNbEnfants(c.getInt(NUM_COL_NB_ENFANTS));
                enregistrement.setPrixTotal(c.getDouble(NUM_COL_PRIX));
                enregistrement.setDateCreation(c.getString(NUM_COL_DATE_CREATION));
                listeEnregistrement.add(enregistrement);
            }
        } finally {
            c.close();
            return listeEnregistrement;
        }
    }

}
