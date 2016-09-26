package com.example.merguez.easytrip.affichage;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.merguez.easytrip.R;
import com.example.merguez.easytrip.bdd.ListeTablesBDD;
import com.example.merguez.easytrip.bdd.RequetesBDD;
import com.example.merguez.easytrip.bdd.table_classes.ClasseBDD;
import com.example.merguez.easytrip.bdd.table_enregistrements.Enregistrement;
import com.example.merguez.easytrip.bdd.table_enregistrements.EnregistrementBDD;
import com.example.merguez.easytrip.bdd.table_users.UserBDD;
import com.example.merguez.easytrip.bdd.table_vols.Vol;
import com.example.merguez.easytrip.bdd.table_vols.VolBDD;

import java.util.ArrayList;
import java.util.HashMap;

public class ListeReservations extends AppCompatActivity {

    private static ListView listeLVreservations;
    private static ListAdapter listAdapter;
    private static ArrayList<HashMap<String,String>> listeReservations;
    private static ArrayList<Enregistrement> listeEnregistrements;
    private static HashMap<String,String> reservation;
    private static SharedPreferences preferences;
    private static int userID;
    private static Vol volAller;
    private static Vol volRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_reservations_main);
        preferences = getBaseContext().getSharedPreferences(Ouverture.PREFERENCES, MODE_PRIVATE);

        ListeTablesBDD listeTablesBDD = new ListeTablesBDD(getApplicationContext());
        listeTablesBDD.open(getApplicationContext());
        userID = UserBDD.getUserIDithNom(preferences.getString(Ouverture.EMAIL, ""));
        listeEnregistrements = EnregistrementBDD.getEnregistrementsWithUserID(userID);
        listeReservations = listing(listeEnregistrements);
        listeTablesBDD.close();

        listeLVreservations = (ListView) findViewById(R.id.list);
        listAdapter = new SimpleAdapter(getApplicationContext(), listeReservations, R.layout.liste_reservations_listview,
                new String[] {"Vol", "Passagers", "Prix", "Classe", "Creation"}, new int[] {R.id.volAllerRetour, R.id.passagers, R.id.prix, R.id.classe, R.id.creation});
        listeLVreservations.setAdapter(listAdapter);
    }

    private ArrayList<HashMap<String,String>> listing(ArrayList<Enregistrement> enregistrements) {
        ArrayList<HashMap<String,String>> listeDetaillee = new ArrayList<>();

        for (Enregistrement enregistrement : enregistrements) {
            reservation = new HashMap<>();
            volAller = VolBDD.getVolWithID(enregistrement.getVolAllerID());
            String vol;

            if (enregistrement.getVolRetourID()>0){
                volRetour = VolBDD.getVolWithID(enregistrement.getVolRetourID());

                vol = "Vol aller : " + volAller.getAeroportDepart() + " \u2794 " + volAller.getAeroportArrivee();
                vol += "\nVol retour : " + volRetour.getAeroportDepart() + " \u2794 " + volRetour.getAeroportArrivee();

            } else {
                vol = "Vol : " + volAller.getAeroportDepart() + " \u2794 " + volAller.getAeroportArrivee();
            }
            reservation.put("Vol", vol);
            reservation.put("Passagers", "Nombre de passagers : " + enregistrement.getNbAdultes() + " adultes\n" + enregistrement.getNbEnfants() + " enfants");
            reservation.put("Prix", "Prix total : " + enregistrement.getPrixTotal() + " €");
            reservation.put("Classe", "Classe : " + ClasseBDD.getClasseNomwithID(volAller.getClasseID()));
            reservation.put("Creation", "Date de création : " + enregistrement.getDateCreation());
            listeDetaillee.add(reservation);

        }

        return listeDetaillee;
    }
}
