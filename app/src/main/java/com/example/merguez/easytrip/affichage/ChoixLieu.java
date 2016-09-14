package com.example.merguez.easytrip.affichage;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.text.TextWatcher;
import android.widget.SimpleAdapter;

import com.example.merguez.easytrip.R;
import com.example.merguez.easytrip.bdd.ListeTablesBDD;
import com.example.merguez.easytrip.bdd.table_aeroports.AeroportBDD;

import java.util.ArrayList;
import java.util.HashMap;

public class ChoixLieu extends Activity implements TextWatcher {

    private static EditText lieuETchoixAeroport;
    private static ListView lieuLVlisteAeroports;
    private static ListAdapter simpleadapter;
    private static ArrayList<HashMap<String,String>> listeMaj;
    private static ListeTablesBDD listeTablesBDD;
    private Intent retourAccueil;
    private Bundle extras;
    private static String resultat;
    private static String aitaResultat;
    private static String nomResultat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lieuETchoixAeroport  = (EditText) findViewById(R.id.lieuETchoixAeroport);
        lieuLVlisteAeroports = (ListView) findViewById(R.id.list);
        listeTablesBDD = new ListeTablesBDD(this);
        listeTablesBDD.open(this);
        retourAccueil = new Intent();
        extras = getIntent().getExtras();

        lieuETchoixAeroport.addTextChangedListener(this);
        masquerClavier();

        lieuETchoixAeroport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afficherClavier();


            }
        });

        lieuLVlisteAeroports.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                aitaResultat = listeMaj.get(position).get("Nom").
                        substring(listeMaj.get(position).get("Nom").lastIndexOf("("),listeMaj.get(position).get("Nom").lastIndexOf(")") + 1);
                nomResultat = listeMaj.get(position).get("Ville").
                        substring(0,listeMaj.get(position).get("Ville").indexOf("("));
                resultat = nomResultat + aitaResultat;
                if (extras.getBoolean(Accueil.DEPART_OU_ARRIVEE)){
                    retourAccueil.putExtra(Accueil.DEPART,resultat);
                } else {
                    retourAccueil.putExtra(Accueil.ARRIVEE,resultat);
                }
                setResult(Activity.RESULT_OK,retourAccueil);
                finish();
            }
        });

    }

    private void afficherClavier() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        );

    }

    private void masquerClavier() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
        );

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String choix = lieuETchoixAeroport.getText().toString();
        listeMaj = listeTablesBDD.RechercheAeroport(choix);
        Log.w("TAG", "" + listeMaj.get(0).toString());
        simpleadapter = new SimpleAdapter(getApplicationContext(),listeMaj, R.layout.list_view_lieu, new String[]{"Nom","Ville"}, new int[] {R.id.list_content, R.id.list_content2});
        lieuLVlisteAeroports.setAdapter(simpleadapter);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
