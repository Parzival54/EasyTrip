package com.example.merguez.easytrip.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.merguez.easytrip.affichage.Accueil;
import com.example.merguez.easytrip.bdd.table_aeroports.Aeroport;
import com.example.merguez.easytrip.bdd.table_aeroports.AeroportBDD;
import com.example.merguez.easytrip.bdd.table_classes.ClasseBDD;
import com.example.merguez.easytrip.bdd.table_enregistrements.Enregistrement;
import com.example.merguez.easytrip.bdd.table_enregistrements.EnregistrementBDD;
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

    //Présentation Table Aéroport:
    private static final String CREATE_TABLE_AEROPORTS = "CREATE TABLE " + AeroportBDD.getTableAeroports() + " ("
            + AeroportBDD.getColId() + " INTEGER PRIMARY KEY AUTOINCREMENT, " + AeroportBDD.getColAita() + " TEXT NOT NULL, "
            + AeroportBDD.getColNom() + " TEXT NOT NULL, " + AeroportBDD.getColVille() + " TEXT NOT NULL, "
            + AeroportBDD.getColPays() + " TEXT NOT NULL, " + AeroportBDD.getColLatitude() + " DOUBLE NOT NULL, "
            + AeroportBDD.getColLongitude() + " DOUBLE NOT NULL, " + AeroportBDD.getColTimezone() + " INTEGER NOT NULL);";

    //Présentation table Vols :
    private static final String CREATE_TABLE_VOLS = "CREATE TABLE " + VolBDD.getTableVols() + " ("
            + VolBDD.getColId() + " INTEGER PRIMARY KEY AUTOINCREMENT, " + VolBDD.getColAeroportDepart() + " TEXT NOT NULL, "
            + VolBDD.getColAeroportArrivee() + " TEXT NOT NULL, " + VolBDD.getColHeureDepart() + " TEXT NOT NULL, "
            + VolBDD.getColHeureArrivee() + " TEXT NOT NULL, " + VolBDD.getColCompagnieId() + " INTEGER NOT NULL, "
            + VolBDD.getColClasseId() + " INTEGER NOT NULL, " + VolBDD.getColPrix() + " DOUBLE NOT NULL);";


    //Présentation table Classes:
    private static final String CREATE_TABLE_CLASSES = "CREATE TABLE " + ClasseBDD.getTableClasse() + " ("
            + ClasseBDD.getColId() + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ClasseBDD.getColLibelle() + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + UserBDD.getTableUser() + " ("
            + UserBDD.getColId() + " INTEGER PRIMARY KEY AUTOINCREMENT, " + UserBDD.getColEmail() + " TEXT NOT NULL, "
            + UserBDD.getColPassword() + " TEXT NOT NULL);";

    private static final String CREATE_TABLE_ENREGISTREMENTS = "CREATE TABLE " + EnregistrementBDD.getTableEnregistrements() + " ("
            + EnregistrementBDD.getColId() + " INTEGER PRIMARY KEY AUTOINCREMENT, " + EnregistrementBDD.getColUserid() + " INTEGER NOT NULL, "
            + EnregistrementBDD.getColVolAllerId() + " INTEGER NOT NULL, " + EnregistrementBDD.getColVolRetourId() + " INTEGER, "
            + EnregistrementBDD.getColNbAdultes() + " INTEGER NOT NULL, " + EnregistrementBDD.getColNbEnfants() + " INTEGER NOT NULL, "
            + EnregistrementBDD.getColPrix() + " REAL NOT NULL, " + EnregistrementBDD.getColDateCreation() + " TEXT NOT NULL);";

//Création du constructeur

    public ListeTablesBDD(Context context) {
        super(context, NOM_BDD, null, VERSION_BDD);
        this.myContext = context;
    }
//Getter pour la Bdd
    public static SQLiteDatabase getBdd() {
        return bdd;
    }
    //Getter pour Aucune réponse
    public static String getAucuneReponse() {
        return AUCUNE_REPONSE;
    }
    //Getter pour Réponse vide
    public static String getReponseVide() {
        return REPONSE_VIDE;
    }
    //Getter pour Le Nom Aéroport
    public static String getNOM() {
        return NOM;
    }
    //Getter pour la Ville de l'Aéroport
    public static String getVILLE() {
        return VILLE;
    }
    //Ouverture en ecriture de la liste des tables
    public void open(Context context){
        listeTablesBDD = new ListeTablesBDD(context);
        bdd = listeTablesBDD.getWritableDatabase();
    }

    @Override
    public void close(){
        bdd.close();
    }
    //création des diff tables de la classe Vol de l'appli :
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_AEROPORTS);
        db.execSQL(CREATE_TABLE_VOLS);
        db.execSQL(CREATE_TABLE_CLASSES);
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_ENREGISTREMENTS);
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
//Evolution de version de la base de données
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + AeroportBDD.getTableAeroports() + " ;");
        db.execSQL("DROP TABLE " + VolBDD.getTableVols() + " ;");
        db.execSQL("DROP TABLE " + ClasseBDD.getTableClasse() + " ;");
        db.execSQL("DROP TABLE " + UserBDD.getTableUser() + " ;");
        db.execSQL("DROP TABLE " + EnregistrementBDD.getTableEnregistrements() + " ;");
        onCreate(db);
    }
//Mise en place de la Méthode de recherche des aéroports
    public ArrayList<HashMap<String,String>> RechercheAeroport(String saisieTexte){
        boolean estContenu;
        ArrayList<Aeroport> listeAeroport;
        ArrayList<HashMap<String,String>> listeDetailleeAeroport = new ArrayList<>();
        HashMap<String,String> Aeroport;
        String[] mots = saisieTexte.split(" ");
//Permettre un choix après les 3 premiers caractères tapés,envoi de la liste des aéroports en rapport avec ces 3 caractères
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
                        //affichage Nom + Ville + TimeZone
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
