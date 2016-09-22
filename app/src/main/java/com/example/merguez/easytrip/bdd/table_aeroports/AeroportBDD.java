package com.example.merguez.easytrip.bdd.table_aeroports;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.merguez.easytrip.bdd.ListeTablesBDD;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by merguez on 13/09/2016.
 */
public class AeroportBDD extends ListeTablesBDD {

    private static final String TABLE_AEROPORTS = "aeroports";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_AITA = "AITA";
    private static final int NUM_COL_AITA = 1;
    private static final String COL_NOM = "NOM";
    private static final int NUM_COL_NOM = 2;
    private static final String COL_VILLE = "VILLE";
    private static final int NUM_COL_VILLE = 3;
    private static final String COL_PAYS = "PAYS";
    private static final int NUM_COL_PAYS = 4;
    private static final String COL_LATITUDE = "LATITUDE";
    private static final int NUM_COL_LATITUDE = 5;
    private static final String COL_LONGITUDE = "LONGITUDE";
    private static final int NUM_COL_LONGITUDE = 6;
    private static final String COL_TIMEZONE = "TIMEZONE";
    private static final int NUM_COL_TIMEZONE = 7;

    public AeroportBDD(Context context) {
        super(context);
    }

    public static String getTableAeroports() {
        return TABLE_AEROPORTS;
    }

    public static String getColId() {
        return COL_ID;
    }

    public static int getNumColId() {
        return NUM_COL_ID;
    }

    public static String getColAita() {
        return COL_AITA;
    }

    public static int getNumColAita() {
        return NUM_COL_AITA;
    }

    public static String getColNom() {
        return COL_NOM;
    }

    public static int getNumColNom() {
        return NUM_COL_NOM;
    }

    public static String getColVille() {
        return COL_VILLE;
    }

    public static int getNumColVille() {
        return NUM_COL_VILLE;
    }

    public static String getColPays() {
        return COL_PAYS;
    }

    public static int getNumColPays() {
        return NUM_COL_PAYS;
    }

    public static String getColLatitude() {
        return COL_LATITUDE;
    }

    public static int getNumColLatitude() {
        return NUM_COL_LATITUDE;
    }

    public static String getColLongitude() {
        return COL_LONGITUDE;
    }

    public static int getNumColLongitude() {
        return NUM_COL_LONGITUDE;
    }

    public static String getColTimezone() {
        return COL_TIMEZONE;
    }

    public static int getNumColTimezone() {
        return NUM_COL_TIMEZONE;
    }

    public static long insertAeroport(Aeroport aeroport){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_AITA, aeroport.getAita());
        values.put(COL_NOM, aeroport.getNom());
        values.put(COL_VILLE, aeroport.getVille());
        values.put(COL_PAYS, aeroport.getPays());
        values.put(COL_LATITUDE, aeroport.getLatitude());
        values.put(COL_LONGITUDE, aeroport.getLongitude());
        values.put(COL_TIMEZONE, aeroport.getTimezone());
        //on insère l'objet dans la BDD via le ContentValues
        return ListeTablesBDD.getBdd().insert(TABLE_AEROPORTS, null, values);
    }

    public static int updateAeroport(Aeroport aeroport){
        int id = aeroport.getId();
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_AITA, aeroport.getAita());
        values.put(COL_NOM, aeroport.getNom());
        values.put(COL_VILLE, aeroport.getVille());
        values.put(COL_PAYS, aeroport.getPays());
        values.put(COL_LATITUDE, aeroport.getLatitude());
        values.put(COL_LONGITUDE, aeroport.getLongitude());
        values.put(COL_TIMEZONE, aeroport.getTimezone());
        return ListeTablesBDD.getBdd().update(TABLE_AEROPORTS, values, COL_ID + " = " +id, null);
    }

    public static int removeAeroport(Aeroport aeroport){
        int id = aeroport.getId();
        //Suppression d'un aéroport de la BDD grâce à l'ID
        return ListeTablesBDD.getBdd().delete(TABLE_AEROPORTS, COL_ID + " = " + id, null);
    }

    public static int removeAll() {
        //Suppression d'un livre de la BDD grâce à l'ID
        return ListeTablesBDD.getBdd().delete(TABLE_AEROPORTS, null, null);
    }

    public static ArrayList<Aeroport> getAeroportWithNom(String nom) {
        Cursor cursor = ListeTablesBDD.getBdd().query(TABLE_AEROPORTS,new String[] {COL_ID, COL_AITA, COL_NOM, COL_VILLE, COL_PAYS, COL_LATITUDE, COL_LONGITUDE, COL_TIMEZONE},
                COL_NOM + " LIKE \'%" + nom + "%\' COLLATE NOCASE OR "
                + COL_AITA + " LIKE \'%" + nom + "%\' COLLATE NOCASE OR "
                + COL_VILLE + " LIKE \'%" + nom + "%\' COLLATE NOCASE OR "
                + COL_PAYS + " LIKE \'%" + nom + "%\' COLLATE NOCASE"
                , null, null, null, null);
        return cursorToAeroport(cursor);
    }

    public static int getAeroportTimezoneWithAita(String aita) {
        Cursor cursor = ListeTablesBDD.getBdd().query(TABLE_AEROPORTS,new String[] {COL_ID, COL_AITA, COL_NOM, COL_VILLE, COL_PAYS, COL_LATITUDE, COL_LONGITUDE, COL_TIMEZONE},
                COL_AITA + " LIKE \'"  + aita + "\'" , null, null, null, null);
        return cursorToAeroport(cursor).get(0).getTimezone();
    }

    public static Aeroport getAeroportNomWithAita(String aita) {
        Cursor cursor = ListeTablesBDD.getBdd().query(TABLE_AEROPORTS,new String[] {COL_ID, COL_AITA, COL_NOM, COL_VILLE, COL_PAYS, COL_LATITUDE, COL_LONGITUDE, COL_TIMEZONE},
                COL_AITA + " LIKE \"" + aita + "\"", null, null, null, null);
        return cursorToAeroport(cursor).get(0);
    }

    public static ArrayList<Aeroport> getAeroportWithAita(String aita) {
        Cursor cursor = ListeTablesBDD.getBdd().query(TABLE_AEROPORTS,new String[] {COL_ID, COL_AITA, COL_NOM, COL_VILLE, COL_PAYS, COL_LATITUDE, COL_LONGITUDE, COL_TIMEZONE},
                COL_AITA + " LIKE \'" + aita + "\'", null, null, null, null);
        return cursorToAeroport(cursor);
    }

    public static int getTimezoneWithAita(String aita) {
        Cursor cursor = ListeTablesBDD.getBdd().query(TABLE_AEROPORTS,new String[] {COL_ID, COL_AITA, COL_NOM, COL_VILLE, COL_PAYS, COL_LATITUDE, COL_LONGITUDE, COL_TIMEZONE},
                COL_AITA + " LIKE \"" + aita + "\"", null, null, null, null);
        return cursorToAeroport(cursor).get(0).getTimezone();
    }



    private static ArrayList<Aeroport> cursorToAeroport(Cursor c) {
        ArrayList<Aeroport> listeAeroport = new ArrayList<>();
        try {
            while (c.moveToNext()){
                Aeroport aeroport = new Aeroport();
                aeroport.setId(c.getInt(NUM_COL_ID));
                aeroport.setAita(c.getString(NUM_COL_AITA));
                aeroport.setNom(c.getString(NUM_COL_NOM));
                aeroport.setVille(c.getString(NUM_COL_VILLE));
                aeroport.setPays(c.getString(NUM_COL_PAYS));
                aeroport.setLatitude(c.getDouble(NUM_COL_LATITUDE));
                aeroport.setLongitude(c.getDouble(NUM_COL_LONGITUDE));
                aeroport.setTimezone(c.getInt(NUM_COL_TIMEZONE));
                listeAeroport.add(aeroport);
            }
        } finally {
            c.close();
            return listeAeroport;
        }
    }

}
