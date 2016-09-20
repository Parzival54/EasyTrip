package com.example.merguez.easytrip.affichage;

import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.merguez.easytrip.R;
import com.example.merguez.easytrip.bdd.RequetesBDD;
import com.example.merguez.easytrip.bdd.table_aeroports.AeroportBDD;
import com.example.merguez.easytrip.bdd.table_vols.Vol;
import com.example.merguez.easytrip.bdd.table_vols.VolBDD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user1 on 07/09/2016.
 */
public class Recherche extends AppCompatActivity {

    Button volsBtnFiltrer;
    Button volsBtnRetour;
    TextView volsTvSelection;
    ListView volsElvListeVols;
    Spinner spinner;
    private static ArrayList<Vol> liste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recherche_main);

        volsBtnFiltrer = (Button) findViewById(R.id.volsBtnFiltrer);
        volsBtnRetour = (Button) findViewById(R.id.volsBtnRetour);
        volsTvSelection = (TextView) findViewById(R.id.volsTvSelection);
        volsElvListeVols = (ListView) findViewById(R.id.volsElvListeVols);
        addItemsOnSpinner();
        volsTvSelection.setText("Selectionner votre vol en aller simple");
        volsTvSelection.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        fillList();


        volsBtnFiltrer.setOnClickListener(new View.OnClickListener()

                                          {
                                              @Override
                                              public void onClick(View v) {
                                                  ArrayList<Vol> listeNonFiltree = new ArrayList<Vol>();
                                                  listeNonFiltree = liste;
                                                  Intent envoieSurFiltre = new Intent(Recherche.this, Filtres.class);
                                                  envoieSurFiltre.putExtra("listeVols",listeNonFiltree);
                                                  startActivity(envoieSurFiltre);
                                              }
                                          }

        );

        volsBtnRetour.setOnClickListener(new View.OnClickListener()

                                         {
                                             @Override
                                             public void onClick(View v) {


                                             }
                                         }

        );
    }

    public void addItemsOnSpinner() {

        spinner = (Spinner) findViewById(R.id.spinner);
        List<String> listeTris = new ArrayList<String>();
        listeTris.add("par prix croissant");
        listeTris.add("par heure de départ");
        listeTris.add("par heure d'arrivée");
        listeTris.add("par durée du vol");
        ArrayAdapter<String> choixTris = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeTris);
        choixTris.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(choixTris);
    }

    /*private void filtrerMaxHeureDep(String heureMax){
        for (int i=0; i<liste.size();i++) {
            if (conversionStringEntier(liste.get(i).getHeureDepart())>conversionStringEntier(heureMax)) {
                liste.remove(i);
            }
        }
    }*/

    private int conversionStringEntier(String heure) {
        int res =  Integer.parseInt(heure.substring(0,2)+ heure.substring(2,4));
        return  res;
    }

    private void fillList() {
        //int depId = getIntent().getIntExtra(Accueil.NOMS_DEP,-1);
        //int arrId = getIntent().getIntExtra(Accueil.NOMS_ARR,-1);
        String depAita = "CDG";
        String arrAita = "ICN";
        ListView vue = (ListView) findViewById(R.id.volsElvListeVols);

        RequetesBDD requeteBDD = new RequetesBDD(this);
        requeteBDD.open();

        List<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> element;
        RequetesBDD requetesBDD = new RequetesBDD(this);
        requetesBDD.open();
        liste = requetesBDD.getVolsWithAita(depAita, arrAita);
        requetesBDD.close();
        int nbVols = liste.size();

        for (int i = 0; i < nbVols; i++) {
            String heureArr = liste.get(i).getHeureArrivee();
            String lendemain = "";
            if (heureArr.substring(heureArr.length() - 1).equals("1")) {
                lendemain = " (lendemain)";
            }
            element = new HashMap<String, String>();
            element.put("Depart", "Départ: " + " (" + depAita + ") " + liste.get(i).getHeureDepart());
            element.put("Arrivee", "Arrivée: " + " (" + arrAita + ") "  + heureArr.substring(0, heureArr.length() - 2) + lendemain);
            element.put("nbEtClasse", "1 adulte    ECO");
            element.put("compEtPrix", "Compagnie: KOREAN AIR    Prix: " + liste.get(i).getPrix());
            listItem.add(element);
        }
        requeteBDD.close();

       /* ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, listItem);
        //On attribut à notre listView l'adapter que l'on vient de créer
        volsElvListeVols.setAdapter(itemsAdapter);*/
        ListAdapter adapter = new SimpleAdapter(this, listItem, R.layout.recherche_vols, new String[]{"Depart", "Arrivee", "nbEtClasse", "compEtPrix"}, new int[]{R.id.depart, R.id.arrivee, R.id.nbEtClasse, R.id.compagnieEtPrix});
        vue.setAdapter(adapter);
    }
}