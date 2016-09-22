package com.example.merguez.easytrip.bdd.table_classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.merguez.easytrip.bdd.ListeTablesBDD;

/**
 * Created by merguez on 13/09/2016.
 */
public class ClasseBDD extends ListeTablesBDD{
    private static final String TABLE_CLASSE = "classes";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_LIBELLE = "LIBELLE";
    private static final int NUM_COL_LIBELLE = 1;

    public ClasseBDD(Context context) {
        super(context);
    }

    public static String getTableClasse() {
        return TABLE_CLASSE;
    }

    public static String getColId() {
        return COL_ID;
    }

    public static int getNumColId() {
        return NUM_COL_ID;
    }

    public static String getColLibelle() {
        return COL_LIBELLE;
    }

    public static int getNumColLibelle() {
        return NUM_COL_LIBELLE;
    }

    public long insertClasse(Classe classe){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_LIBELLE, classe.getClasse());
        //on insère l'objet dans la BDD via le ContentValues
        return ListeTablesBDD.getBdd().insert(TABLE_CLASSE, null, values);
    }

    public int updateClasse(Classe classe){
        int id = classe.getId();
        //La mise à jour d'un livre dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle livre on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_LIBELLE, classe.getClasse());
        return ListeTablesBDD.getBdd().update(TABLE_CLASSE, values, COL_ID + " = " +id, null);
    }

    public int removeClasse(Classe classe){
        int id = classe.getId();
        //Suppression d'un aéroport de la BDD grâce à l'ID
        return ListeTablesBDD.getBdd().delete(TABLE_CLASSE, COL_ID + " = " + id, null);
    }

    public int removeAll() {
        //Suppression d'un livre de la BDD grâce à l'ID
        return ListeTablesBDD.getBdd().delete(TABLE_CLASSE, null, null);
    }

    public static int getClasseIDwithNom(String classe, Context context){
        ListeTablesBDD listeTablesBDD = new ListeTablesBDD(context);
        listeTablesBDD.open(context);
        Cursor cursor = ListeTablesBDD.getBdd().query(TABLE_CLASSE,new String[] {COL_ID},
                COL_LIBELLE + " LIKE \'" + classe + "\'", null, null, null, null);
        listeTablesBDD.close();
        return cursorToClasse(cursor).getId();
    }

    public static int getClasseNomwithID(int classeID, Context context){
        ListeTablesBDD listeTablesBDD = new ListeTablesBDD(context);
        listeTablesBDD.open(context);
        Cursor cursor = ListeTablesBDD.getBdd().query(TABLE_CLASSE,new String[] {COL_LIBELLE},
                COL_ID + " LIKE \'" + classeID + "\'", null, null, null, null);
        listeTablesBDD.close();
        return cursorToClasse(cursor).getId();
    }

    private static Classe cursorToClasse(Cursor c) {
        Classe classe = new Classe();
        try {
            c.moveToNext();
                classe.setId(c.getInt(NUM_COL_ID));
                classe.setClasse(c.getString(NUM_COL_LIBELLE));
        } finally {
            c.close();
            return classe;
        }
    }
}
