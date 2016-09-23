package com.example.merguez.easytrip.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.merguez.easytrip.affichage.Accueil;
import com.example.merguez.easytrip.bdd.table_aeroports.Aeroport;
import com.example.merguez.easytrip.bdd.table_aeroports.AeroportBDD;
import com.example.merguez.easytrip.bdd.table_classes.ClasseBDD;
import com.example.merguez.easytrip.bdd.table_users.User;
import com.example.merguez.easytrip.bdd.table_users.UserBDD;
import com.example.merguez.easytrip.bdd.table_vols.VolBDD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by merguez on 13/09/2016.
 */
public class ListeTablesBDD extends SQLiteOpenHelper {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "easytrip.db";
    private ListeTablesBDD listeTablesBDD;
    private static SQLiteDatabase bdd;
    private static final String AUCUNE_REPONSE = "Aucune réponse";
    private static final String REPONSE_VIDE = "";
    private static final String NOM = "Nom";
    private static final String VILLE = "Ville";
    private final Context myContext;

    private static final String CREATE_TABLE_AEROPORTS = "CREATE TABLE " + AeroportBDD.getTableAeroports() + " ("
            + AeroportBDD.getColId() + " INTEGER PRIMARY KEY AUTOINCREMENT, " + AeroportBDD.getColAita() + " TEXT NOT NULL, "
            + AeroportBDD.getColNom() + " TEXT NOT NULL, " + AeroportBDD.getColVille() + " TEXT NOT NULL, "
            + AeroportBDD.getColPays() + " TEXT NOT NULL, " + AeroportBDD.getColLatitude() + " DOUBLE NOT NULL, "
            + AeroportBDD.getColLongitude() + " DOUBLE NOT NULL, " + AeroportBDD.getColTimezone() + " INTEGER NOT NULL);";

    private static final String CREATE_TABLE_VOLS = "CREATE TABLE " + VolBDD.getTableVols() + " ("
            + VolBDD.getColId() + " INTEGER PRIMARY KEY AUTOINCREMENT, " + VolBDD.getColAeroportDepart() + " TEXT NOT NULL, "
            + VolBDD.getColAeroportArrivee() + " TEXT NOT NULL, " + VolBDD.getColHeureDepart() + " TEXT NOT NULL, "
            + VolBDD.getColHeureArrivee() + " TEXT NOT NULL, " + VolBDD.getColCompagnieId() + " INTEGER NOT NULL, "
            + VolBDD.getColClasseId() + " INTEGER NOT NULL, " + VolBDD.getColPrix() + " DOUBLE NOT NULL);";

    private static final String CREATE_TABLE_CLASSES = "CREATE TABLE " + ClasseBDD.getTableClasse() + " ("
            + ClasseBDD.getColId() + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ClasseBDD.getColLibelle() + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + UserBDD.getTableUser() + " ("
            + UserBDD.getColId() + " INTEGER PRIMARY KEY AUTOINCREMENT, " + UserBDD.getColEmail() + " TEXT NOT NULL, "
            + UserBDD.getColPassword() + " TEXT NOT NULL);";

    public ListeTablesBDD(Context context) {
        super(context, NOM_BDD, null, VERSION_BDD);
        this.myContext = context;
    }

    public static SQLiteDatabase getBdd() {
        return bdd;
    }

    public static String getAucuneReponse() {
        return AUCUNE_REPONSE;
    }

    public static String getReponseVide() {
        return REPONSE_VIDE;
    }

    public static String getNOM() {
        return NOM;
    }

    public static String getVILLE() {
        return VILLE;
    }

    public void open(Context context){
        listeTablesBDD = new ListeTablesBDD(context);
        bdd = listeTablesBDD.getWritableDatabase();
    }

    @Override
    public void close(){
        bdd.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_AEROPORTS);
        db.execSQL(CREATE_TABLE_VOLS);
        db.execSQL(CREATE_TABLE_CLASSES);
        db.execSQL(CREATE_TABLE_USERS);
        /*ListeTablesBDD listeTablesBDD = new ListeTablesBDD(myContext);
        listeTablesBDD.open(myContext);
        InsertionDonnees.insertionDonnees(myContext);
        listeTablesBDD.close();*/
        /*new Thread(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(context,"La bdd est complétée",Toast.LENGTH_LONG).show();
            }
        }).start();*/


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + AeroportBDD.getTableAeroports() + " ;");
        db.execSQL("DROP TABLE " + VolBDD.getTableVols() + " ;");
        db.execSQL("DROP TABLE " + ClasseBDD.getTableClasse() + " ;");
        db.execSQL("DROP TABLE " + UserBDD.getTableUser() + " ;");
        onCreate(db);
    }

    public ArrayList<HashMap<String,String>> RechercheAeroport(String saisieTexte){
        boolean estContenu;
        ArrayList<Aeroport> listeAeroport;
        ArrayList<HashMap<String,String>> listeDetailleeAeroport = new ArrayList<>();
        HashMap<String,String> Aeroport;
        String[] mots = saisieTexte.split(" ");

        if (saisieTexte.length() >= 3) {
            estContenu = true;
            listeAeroport = AeroportBDD.getAeroportWithNom(mots[0]);
            for (int i = 0; i < listeAeroport.size(); i++) {
                    Aeroport = new HashMap<>();


                for (String mot : mots) {
                    if (!listeAeroport.get(i).toString().toUpperCase().contains(mot.toUpperCase())) {
                        estContenu = false;
                    }
                }

                    if (estContenu) {
                        String timeZone = Integer.toString(listeAeroport.get(i).getTimezone());
                        if (listeAeroport.get(i).getTimezone() > 0) {
                            timeZone = "+" + timeZone;
                        }
                        Aeroport.put(NOM,listeAeroport.get(i).getNom() + " (" + listeAeroport.get(i).getAita() + ")");
                        Aeroport.put(VILLE,listeAeroport.get(i).getVille()+ " (UTC " + timeZone + ":00)");
                        listeDetailleeAeroport.add(Aeroport);
                    } // end if
            } // end for


            if (listeDetailleeAeroport.size() != 0){
                return listeDetailleeAeroport;
            }
            else {
                Aeroport = new HashMap<>();
                Aeroport.put(NOM,AUCUNE_REPONSE);
                listeDetailleeAeroport.add(Aeroport);
                return listeDetailleeAeroport;
            }
        } else {
            HashMap<String,String> listeVide = new HashMap<>();
            listeVide.put(NOM,REPONSE_VIDE);
            listeDetailleeAeroport.add(listeVide);
            return listeDetailleeAeroport;
        }

    }

}
