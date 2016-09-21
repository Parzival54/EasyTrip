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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtres);
        filtreCbHeureDepMin = (CheckBox) findViewById(R.id.filtreCbHeureDepMin);
        filtreCbHeureArrMax = (CheckBox) findViewById(R.id.filtreCbHeureArrMax);
        filtreCbPrixMax = (CheckBox) findViewById(R.id.filtreCbPrixMax);
        filtreEdtHeureDepMin = (Spinner) findViewById(R.id.filtreEdtHeureDepMin);
        filtreEdtHeureArrMax = (Spinner) findViewById(R.id.filtreEdtHeureArrMax);
        filtreEdtPrixMax = (EditText) findViewById(R.id.filtreEdtPrixMax);
        Bundle b = getIntent().getExtras();
        VolList listeNonFiltree = b.getParcelable("listeVols");
        addListenerOnButton(listeNonFiltree);
        addItemsOnSpinners();
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

    public void addListenerOnButton(final VolList maListeDeVols) {
        filtrebtnFiltrer = (Button) findViewById(R.id.filtrebtnFiltrer);
        filtrebtnFiltrer.setOnClickListener(new OnClickListener() {
            //Run when button is clicked
            @Override
            public void onClick(View v) {
                ArrayList<Vol> listeFiltree = new ArrayList<Vol>();
                for (int i=0; i<maListeDeVols.size();i++){
                    if ((filtreCbHeureDepMin.isChecked() && (stringHeureToInt(filtreEdtHeureDepMin.getSelectedItem().toString())>stringHeureToInt(listeFiltree.get(i).getHeureDepart())))
                            &&(filtreCbHeureArrMax.isChecked() && (stringHeureToInt(filtreEdtHeureArrMax.getSelectedItem().toString())<stringHeureToInt(listeFiltree.get(i).getHeureArrivee())))
                            && (filtreCbPrixMax.isChecked() && (Double.parseDouble(filtreEdtPrixMax.getText().toString())>listeFiltree.get(i).getPrix())))
                    {
                        listeFiltree.add(maListeDeVols.get(i));
                    }
                }
                Intent retourSurRecherche = new Intent(Filtres.this, Recherche.class);
                retourSurRecherche.putExtra("listeVolsFiltree",(Parcelable)listeFiltree);
                startActivity(retourSurRecherche);
            }
        });


    }
}
