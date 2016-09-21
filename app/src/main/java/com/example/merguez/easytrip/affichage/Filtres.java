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

    private CheckBox filtreCbHeureDepMin, filtreCbHeureArrMax, filtreCbPrixMax;
    private Spinner filtreEdtHeureDepMin, filtreEdtHeureArrMax;
    private EditText filtreEdtPrixMax;
    private Button filtrebtnFiltrer;
    private static Reservation reservation;
    private static VolList listeVols;
    private static VolList listeVolsFiltree = new VolList();
    private static Intent filtresToRecherche;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtres);
        listeVols = (VolList) getIntent().getParcelableExtra(Accueil.LISTE_VOLS);
        filtreCbHeureDepMin = (CheckBox) findViewById(R.id.filtreCbHeureDepMin);
        filtreCbHeureArrMax = (CheckBox) findViewById(R.id.filtreCbHeureArrMax);
        filtreCbPrixMax = (CheckBox) findViewById(R.id.filtreCbPrixMax);
        filtreEdtHeureDepMin = (Spinner) findViewById(R.id.filtreEdtHeureDepMin);
        filtreEdtHeureArrMax = (Spinner) findViewById(R.id.filtreEdtHeureArrMax);
        filtreEdtPrixMax = (EditText) findViewById(R.id.filtreEdtPrixMax);
        reservation = (Reservation) getIntent().getSerializableExtra(Accueil.RESERVATION);
        listeVols = (VolList) getIntent().getParcelableExtra(Accueil.LISTE_VOLS);
        filtresToRecherche = new Intent(Filtres.this, Recherche.class);
        addListenerOnButton();
        addItemsOnSpinners();
        filtreEdtHeureDepMin.setEnabled(false);
        filtreEdtHeureDepMin.setClickable(false);
        filtreEdtHeureArrMax.setEnabled(false);
        filtreEdtHeureArrMax.setClickable(false);
        filtreEdtPrixMax.setEnabled(false);
        filtreEdtPrixMax.setClickable(false);

        filtreCbHeureDepMin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (filtreCbHeureDepMin.isChecked()) {
                    filtreEdtHeureDepMin.setEnabled(true);
                    filtreEdtHeureDepMin.setClickable(true);
                }
                else {
                    filtreEdtHeureDepMin.setEnabled(false);
                    filtreEdtHeureDepMin.setClickable(false);
                }
            }
        });

        filtreCbHeureArrMax.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (filtreCbHeureArrMax.isChecked()) {
                    filtreEdtHeureArrMax.setEnabled(true);
                    filtreEdtHeureArrMax.setClickable(true);
                }
                else {
                    filtreEdtHeureArrMax.setEnabled(false);
                    filtreEdtHeureArrMax.setClickable(false);
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
        filtreEdtHeureDepMin.setAdapter(adapter);
        filtreEdtHeureArrMax.setAdapter(adapter);
    }


    /*public void addListenerOnChkHeureDepMin() {
        filtreCbHeureDepMin = (CheckBox) findViewById(R.id.filtreCbHeureDepMin);
        filtreCbHeureDepMin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {

                }
            }
        });
    }

    public void addListenerOnChkHeureDepMax() {
        filtreCbHeureDepMax = (CheckBox) findViewById(R.id.filtreCbHeureDepMax);
        filtreCbHeureDepMax.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {

                }
            }
        });
    }

    public void addListenerOnChkPrix() {
        filtreCbPrixMax = (CheckBox) findViewById(R.id.filtreCbPrixMax);
        filtreCbPrixMax.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {

                }
            }
        });
    }*/

    public int stringHeureToInt (String heure){
        String s = heure.substring(0,2)+heure.substring(3,5);
        return Integer.parseInt(s);
    }

    public void addListenerOnButton() {
        filtrebtnFiltrer = (Button) findViewById(R.id.filtrebtnFiltrer);
        filtrebtnFiltrer.setOnClickListener(new OnClickListener() {
            //Run when button is clicked
            @Override
            public void onClick(View v) {
                listeVolsFiltree.clear();
                for (int i=0; i<listeVols.size();i++){
                    double prixTotal = (double)(((int)(listeVols.get(i).getPrix()*(reservation.getNbAdultes()+reservation.getNbEnfants()*0.8)*100))/100);
                    if ((!(filtreCbHeureDepMin.isChecked()) || (stringHeureToInt(filtreEdtHeureDepMin.getSelectedItem().toString())<stringHeureToInt(listeVols.get(i).getHeureDepart())))
                            &&(!(filtreCbHeureArrMax.isChecked()) || (stringHeureToInt(filtreEdtHeureArrMax.getSelectedItem().toString())>stringHeureToInt(listeVols.get(i).getHeureArrivee())))
                            && (!(filtreCbPrixMax.isChecked()) || (Double.parseDouble(filtreEdtPrixMax.getText().toString())> prixTotal)))
                    {
                        listeVolsFiltree.add(listeVols.get(i));
                    }
                }
                filtresToRecherche.putExtra(Recherche.LISTE_VOLS_FILTREE,(Parcelable)listeVolsFiltree);
                filtresToRecherche.putExtra(Accueil.LISTE_VOLS, (Parcelable)listeVols);
                filtresToRecherche.putExtra(Accueil.RESERVATION, reservation);
                startActivity(filtresToRecherche);
            }
        });


    }
}
