package com.example.merguez.easytrip.affichage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.merguez.easytrip.R;
import com.example.merguez.easytrip.bdd.table_vols.Vol;
import com.example.merguez.easytrip.bdd.table_vols.VolList;

import java.util.ArrayList;
import java.util.List;

public class Filtres extends Activity {

    private CheckBox filtreCbHeureDepMin, filtreCbHeureArrMax, filtreCbPrixMax, filtreCbHeureDepMinRet,filtreCbHeureArrMaxRet;
    private Spinner filtreSpHeureDepMin, filtreSpHeureArrMax, filtreSpHeureDepMinRet, filtreSpHeureArrMaxRet;
    private EditText filtreEdtPrixMax;
    private Button filtrebtnFiltrer;
    private static Reservation reservation;
    private static VolList listeVols;
    private static VolList listeVolsRetour;
    private static VolList listeVolsFiltree;
    private static Intent filtresToRecherche;
    private static final double coeffReductionEnfant = 0.8;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtres);
        listeVols = (VolList) getIntent().getParcelableExtra(Accueil.LISTE_VOLS);
        reservation = (Reservation) getIntent().getSerializableExtra(Accueil.RESERVATION);
        if (reservation.isAllerRetour())
        listeVolsRetour =(VolList) getIntent().getParcelableExtra(Accueil.LISTE_VOLS_RETOUR);
        listeVolsFiltree = new VolList();
       // listeVolsFiltreeRetour = new VolList();
        filtreCbHeureDepMin = (CheckBox) findViewById(R.id.filtreCbHeureDepMin);
        filtreCbHeureArrMax = (CheckBox) findViewById(R.id.filtreCbHeureArrMax);
        filtreCbHeureDepMinRet = (CheckBox) findViewById(R.id.filtreCbHeureDepMinRet);
        filtreCbHeureArrMaxRet = (CheckBox) findViewById(R.id.filtreCbHeureArrMaxRet);
        filtreCbPrixMax = (CheckBox) findViewById(R.id.filtreCbPrixMax);
        filtreSpHeureDepMin = (Spinner) findViewById(R.id.filtreSpHeureDepMin);
        filtreSpHeureArrMax = (Spinner) findViewById(R.id.filtreSpHeureArrMax);
        filtreSpHeureDepMinRet = (Spinner) findViewById(R.id.filtreSpHeureDepMinRet);
        filtreSpHeureArrMaxRet = (Spinner) findViewById(R.id.filtreSpHeureArrMaxRet);
        filtreEdtPrixMax = (EditText) findViewById(R.id.filtreEdtPrixMax);
        filtresToRecherche = new Intent(Filtres.this, Recherche.class);
        addListenerOnButton();
        addItemsOnSpinners();
        config();
        filtreSpHeureDepMin.setEnabled(false);
        filtreSpHeureDepMin.setClickable(false);
        filtreSpHeureArrMax.setEnabled(false);
        filtreSpHeureArrMax.setClickable(false);
        filtreEdtPrixMax.setEnabled(false);
        filtreEdtPrixMax.setClickable(false);

        filtreCbHeureDepMin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (filtreCbHeureDepMin.isChecked()) {
                    filtreSpHeureDepMin.setEnabled(true);
                    filtreSpHeureDepMin.setClickable(true);
                }
                else {
                    filtreSpHeureDepMin.setEnabled(false);
                    filtreSpHeureDepMin.setClickable(false);
                }
            }
        });

        filtreCbHeureArrMax.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (filtreCbHeureArrMax.isChecked()) {
                    filtreSpHeureArrMax.setEnabled(true);
                    filtreSpHeureArrMax.setClickable(true);
                }
                else {
                    filtreSpHeureArrMax.setEnabled(false);
                    filtreSpHeureArrMax.setClickable(false);
                }
            }
        });

        filtreCbHeureDepMinRet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (filtreCbHeureDepMinRet.isChecked()) {
                    filtreSpHeureDepMinRet.setEnabled(true);
                    filtreSpHeureDepMinRet.setClickable(true);
                }
                else {
                    filtreSpHeureDepMinRet.setEnabled(false);
                    filtreSpHeureDepMinRet.setClickable(false);
                }
            }
        });

        filtreCbHeureArrMaxRet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (filtreCbHeureArrMaxRet.isChecked()) {
                    filtreSpHeureArrMaxRet.setEnabled(true);
                    filtreSpHeureArrMaxRet.setClickable(true);
                }
                else {
                    filtreSpHeureArrMaxRet.setEnabled(false);
                    filtreSpHeureArrMaxRet.setClickable(false);
                }
            }
        });

        filtreCbPrixMax.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (filtreCbPrixMax.isChecked()) {
                    filtreEdtPrixMax.setEnabled(true);
                    filtreEdtPrixMax.setClickable(true);
                }
                else {
                    filtreEdtPrixMax.setEnabled(false);
                    filtreEdtPrixMax.setClickable(false);
                }
            }
        });
    }
    public void addItemsOnSpinners() {
        List<String> listeHoraires = new ArrayList<String>();
        for (int i = 4; i<10;i++){
            listeHoraires.add("0"+i+":00");
        }
        for (int i = 10; i<24;i++) {
            listeHoraires.add(i + ":00");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listeHoraires);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filtreSpHeureDepMin.setAdapter(adapter);
        filtreSpHeureArrMax.setAdapter(adapter);
        filtreSpHeureDepMinRet.setAdapter(adapter);
        filtreSpHeureArrMaxRet.setAdapter(adapter);
    }

    private int stringHeureToInt (String heure){
        String s = heure.substring(0,2)+heure.substring(3,5);
        return Integer.parseInt(s);
    }

    private void addListenerOnButton() {
        filtrebtnFiltrer = (Button) findViewById(R.id.filtrebtnFiltrer);
        filtrebtnFiltrer.setOnClickListener(new OnClickListener() {
            //Run when button is clicked
            @Override
            public void onClick(View v) {
                int nbAdultes = reservation.getNbAdultes();
                int nbEnfants = reservation.getNbEnfants();
                if (!reservation.isAllerRetour()) {
                    for (int i = 0; i < listeVols.size(); i++) {
                        Vol aller = listeVols.get(i);
                        if (horairesValidesAller(aller) && (prixValideAllerSimple(aller, nbAdultes, nbEnfants)))
                            listeVolsFiltree.add(listeVols.get(i));
                    }
                }
                else {
                    for (int i = 0; i < listeVols.size(); i++) {
                        Vol aller = listeVols.get(i);
                        for (int j = 0; j < listeVolsRetour.size(); j++) {
                            Vol retour = listeVolsRetour.get(j);
                            if (horairesValidesAller(aller) && horairesValidesRetour(retour)
                            &&(prixValideAllerRetour(aller, retour, nbAdultes, nbEnfants)))
                                listeVolsFiltree.add(listeVols.get(i));
                                listeVolsFiltree.add(listeVolsRetour.get(j));
                        }
                    }
                    filtresToRecherche.putExtra(Accueil.LISTE_VOLS_RETOUR, (Parcelable)listeVolsRetour);
                }
                filtresToRecherche.putExtra(Recherche.LISTE_VOLS_FILTREE,(Parcelable)listeVolsFiltree);
                filtresToRecherche.putExtra(Accueil.LISTE_VOLS, (Parcelable)listeVols);
                filtresToRecherche.putExtra(Accueil.RESERVATION, reservation);
                startActivity(filtresToRecherche);
            }
        });
    }

    private boolean horairesValidesAller(Vol v) {
        return ((!(filtreCbHeureDepMin.isChecked())||(stringHeureToInt(filtreSpHeureDepMin.getSelectedItem().toString())<stringHeureToInt(v.getHeureDepart())))
                &&(!(filtreCbHeureArrMax.isChecked())||(stringHeureToInt(filtreSpHeureArrMax.getSelectedItem().toString())>Recherche.mod(stringHeureToInt(v.getHeureArrivee())+reservation.getDecalageHoraire()*100,2400))));
    }

    private boolean horairesValidesRetour(Vol v){
        return ((!(filtreCbHeureDepMinRet.isChecked())||(stringHeureToInt(filtreSpHeureDepMinRet.getSelectedItem().toString())<stringHeureToInt(v.getHeureDepart())))
                &&(!(filtreCbHeureArrMaxRet.isChecked())||(stringHeureToInt(filtreSpHeureArrMaxRet.getSelectedItem().toString())>Recherche.mod(stringHeureToInt(v.getHeureArrivee())-reservation.getDecalageHoraire()*100,2400))));
    }

    private boolean prixValideAllerSimple(Vol v, int nbAdultes, int nbEnfants) {
        return (!(filtreCbPrixMax.isChecked())
                ||(Double.parseDouble(filtreEdtPrixMax.getText().toString())> prixTotal(v.getPrix(),nbAdultes,nbEnfants)));
    }

    private boolean prixValideAllerRetour(Vol aller, Vol retour, int nbAdultes, int nbEnfants) {
        return (!(filtreCbPrixMax.isChecked())
                ||(Double.parseDouble(filtreEdtPrixMax.getText().toString())> prixTotal(aller.getPrix()+retour.getPrix(),nbAdultes,nbEnfants)));
    }

    private double prixTotal(double prix, int nbAdultes, int nbEnfants) {
        return (double)(((int)(prix*(nbAdultes + nbEnfants*coeffReductionEnfant)*100))/100);
    }

    private void config(){
        if (!reservation.isAllerRetour()) {
            filtreSpHeureArrMaxRet.setVisibility(View.INVISIBLE);
            filtreSpHeureDepMinRet.setVisibility(View.INVISIBLE);
            filtreCbHeureDepMinRet.setVisibility(View.INVISIBLE);
            filtreCbHeureArrMaxRet.setVisibility(View.INVISIBLE);
        }
        else {
            filtreSpHeureArrMaxRet.setVisibility(View.VISIBLE);
            filtreSpHeureDepMinRet.setVisibility(View.VISIBLE);
            filtreCbHeureDepMinRet.setVisibility(View.VISIBLE);
            filtreCbHeureArrMaxRet.setVisibility(View.VISIBLE);
        }
    }
}
