package com.example.merguez.easytrip.affichage;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.merguez.easytrip.R;
import com.example.merguez.easytrip.bdd.RequetesBDD;
import com.example.merguez.easytrip.bdd.table_aeroports.AeroportBDD;
import com.example.merguez.easytrip.bdd.table_vols.Vol;
import com.example.merguez.easytrip.bdd.table_vols.VolBDD;
import com.example.merguez.easytrip.bdd.table_vols.VolList;

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
    Button volsBtnVol;
    Spinner spinner;
    private static Reservation reservation;
    private static VolList listeVols;
    private static VolList listeVolsFiltree = new VolList();
    final static String LISTE_VOLS_FILTREE = "liste vols filtree";
    private static Intent rechercheToFiltre;
    private static Intent rechercheToAccueil;
    private static ListAdapter adapter;
    private static ListView vue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recherche_main);
        reservation = (Reservation) getIntent().getSerializableExtra(Accueil.RESERVATION);
        listeVols = (VolList) getIntent().getParcelableExtra(Accueil.LISTE_VOLS);
        selectionListeFiltree();
        volsBtnFiltrer = (Button) findViewById(R.id.volsBtnFiltrer);
        volsBtnRetour = (Button) findViewById(R.id.volsBtnRetour);
        volsTvSelection = (TextView) findViewById(R.id.volsTvSelection);
        volsElvListeVols = (ListView) findViewById(R.id.volsElvListeVols);
        volsBtnVol = (Button) findViewById(R.id.VolReserver);
        addItemsOnSpinner();
        volsTvSelection.setText("Selectionner votre vol en aller simple");
        volsTvSelection.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        rechercheToFiltre = new Intent(Recherche.this, Filtres.class);
        rechercheToAccueil = new Intent(Recherche.this, Accueil.class);
        remplirListView();

        volsBtnFiltrer.setOnClickListener(new View.OnClickListener()

                                          {
                                              @Override
                                              public void onClick(View v) {
                                                  //ArrayList<Vol> listeNonFiltree = new ArrayList<Vol>();
                                                  //listeNonFiltree = liste;
                                                  rechercheToFiltre.putExtra(Accueil.LISTE_VOLS, (Parcelable) listeVols);
                                                  rechercheToFiltre.putExtra(Accueil.RESERVATION, reservation);
                                                  startActivity(rechercheToFiltre);
                                              }
                                          }

        );

        volsBtnRetour.setOnClickListener(new View.OnClickListener()

                                         {
                                             @Override
                                             public void onClick(View v) {
                                                 Accueil.accueilToRecherche = true;
                                                 startActivity(rechercheToAccueil);
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

    private void remplirListView() {
        vue = (ListView) findViewById(R.id.volsElvListeVols);
        List<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> element;
        if (listeVolsFiltree == null || listeVolsFiltree.size()==0) {
            element = new HashMap<String, String>();
            element.put("Horaires","Il n'y a aucun vol correspondant à votre recherche.");
            //volsBtnVol.setVisibility(View.INVISIBLE);
            listItem.add(element);
        }
        else {
            int nbVols = listeVolsFiltree.size();
            String depAita = reservation.getAitaDepart();
            String arrAita = reservation.getAitaArrivee();
            int nbAdulte = reservation.getNbAdultes();
            int nbEnfants = reservation.getNbEnfants();
            String libelleAdultes = "";
            String libelleEnfants = "";
            if (nbAdulte == 1)
                libelleAdultes = "1 adulte  ";
            if (nbAdulte > 1)
                libelleAdultes = nbAdulte + " adultes  ";
            if (nbEnfants == 1)
                libelleEnfants = "1 enfant    ";
            if (nbEnfants > 1)
                libelleEnfants = nbEnfants + " enfants    ";
            RequetesBDD requetesBDD = new RequetesBDD(this);
            requetesBDD.open();
            int decHorDep = requetesBDD.getAeroportTimezoneWithAita(reservation.getAitaDepart());
            int decHorArr = requetesBDD.getAeroportTimezoneWithAita(reservation.getAitaArrivee());
            requetesBDD.close();

            for (int i = 0; i < nbVols; i++) {
                Vol v = listeVolsFiltree.get(i);
                String heureDep = v.getHeureDepart();
                String heureArr = v.getHeureArrivee();
                if (heureArr.substring(heureArr.length() - 1).equals("1"))
                    heureArr =ajouterTimeToHeure(heureArr,24);
                heureArr = heureArr.substring(0, heureArr.length() - 2);
                heureArr = ajouterTimeToHeure(heureArr,decHorArr-decHorDep);
                String lendemain = "";
                if (Integer.parseInt(heureArr.substring(0,2))>=24) {
                    heureArr = ajouterTimeToHeure(heureArr,-24);
                    lendemain = "lendemain";
                }
                double prixTotal = (double) ((int) (v.getPrix() * (nbAdulte + nbEnfants * 0.8) * 100)) / 100;
                element = new HashMap<String, String>();
                element.put("Horaires", heureDep + " (" + depAita + ")  \u2794  " + heureArr + " (" + arrAita + ") " + lendemain);
                element.put("nbPassagersEtPrix", libelleAdultes + libelleEnfants + "Prix total: " + prixTotal + " €");
                element.put("classe", "Classe: " + reservation.getClasse());
                listItem.add(element);
            }
        }

       /* ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, listItem);
        //On attribut à notre listView l'adapter que l'on vient de créer
        volsElvListeVols.setAdapter(itemsAdapter);*/
        adapter = new SimpleAdapter(this, listItem, R.layout.recherche_vols, new String[]{"Horaires", "nbPassagersEtPrix", "classe"}, new int[]{R.id.horaires, R.id.nbPassagersEtPrix, R.id.classe});
        vue.setAdapter(adapter);
    }

     private void selectionListeFiltree() {
        if (Accueil.accueilToRecherche) {
            listeVolsFiltree = listeVols;
            Accueil.accueilToRecherche = false;
        } else
            listeVolsFiltree = (VolList) getIntent().getParcelableExtra(Recherche.LISTE_VOLS_FILTREE);
    }

    public void clickHandler(View v){
        RelativeLayout viewParentRow = (RelativeLayout)v.getParent();
        Button btnChild = (Button)viewParentRow.getChildAt(3);

    }

    private String ajouterTimeToHeure(String heure, int time) {
        String chiffresHeure = String.valueOf(Integer.parseInt(heure.substring(0,2))+time);
        return chiffresHeure+heure.substring(2);
    }
}