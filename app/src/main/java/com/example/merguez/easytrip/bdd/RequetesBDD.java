package com.example.merguez.easytrip.bdd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.merguez.easytrip.bdd.table_vols.Vol;

import java.util.ArrayList;

/**
 * Created by user1 on 15/09/2016.
 */
public class RequetesBDD {

    private SQLiteDatabase bdd;

    private ListeTablesBDD maBaseSQLite;

    public RequetesBDD(Context context){
        //On créer la BDD et sa table
        maBaseSQLite = new ListeTablesBDD(context);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = maBaseSQLite.getReadableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }


    public ArrayList<Vol> getVolsWithAita(String depAita, String arrAita){
        String s = "SELECT * FROM vols WHERE vols.AEROPORT_DEPART LIKE ? AND vols.AEROPORT_ARRIVEE LIKE ?";
        Cursor c = bdd.rawQuery(s, new String[]{depAita, arrAita});

        if (c.getCount()>0) {
            c.moveToFirst();
            ArrayList<Vol> liste = new ArrayList<>();
            while (!c.isAfterLast()) {
                Vol vol = new Vol();
                //on lui affecte toutes les infos grâce aux infos contenues dans le Cursor
                vol.setID(c.getInt(0));
                vol.setAeroportDepart(c.getString(1));
                vol.setAeroportArrivee(c.getString(2));
                vol.setHeureDepart(c.getString(3));
                vol.setHeureArrivee(c.getString(4));
                vol.setCompagnieID(c.getInt(5));
                vol.setClasseID(c.getInt(6));
                vol.setPrix(c.getDouble(7));
                liste.add(vol);
                c.moveToNext();
            }
            c.close();
            return liste;
        }
        else
        {   c.close();
            return null;
        }
    }
}