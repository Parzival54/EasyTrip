package com.example.merguez.easytrip.bdd.table_users;

import android.content.ContentValues;
import android.content.Context;

import com.example.merguez.easytrip.bdd.ListeTablesBDD;

/**
 * Created by merguez on 23/09/2016.
 */
public class UserBDD extends ListeTablesBDD {
    private static final String TABLE_USER = "users";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_EMAIL = "EMAIL";
    private static final int NUM_COL_EMAIL = 1;
    private static final String COL_PASSWORD = "MOT_DE_PASSE";
    private static final int NUM_COL_PASSWORD = 2;


    public UserBDD(Context context) {
        super(context);
    }

    public static String getTableUser() {
        return TABLE_USER;
    }

    public static String getColId() {
        return COL_ID;
    }

    public static int getNumColId() {
        return NUM_COL_ID;
    }

    public static String getColEmail() {
        return COL_EMAIL;
    }

    public static int getNumColEmail() {
        return NUM_COL_EMAIL;
    }

    public static String getColPassword() {
        return COL_PASSWORD;
    }

    public static int getNumColPassword() {
        return NUM_COL_PASSWORD;
    }

    public static long insertUser(User user){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_EMAIL, user.getEmail());
        values.put(COL_PASSWORD, user.getPassword());
        //on insère l'objet dans la BDD via le ContentValues
        return ListeTablesBDD.getBdd().insert(TABLE_USER, null, values);
    }
}
