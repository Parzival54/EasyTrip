package com.example.merguez.easytrip.affichage;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.merguez.easytrip.R;
import com.example.merguez.easytrip.bdd.ListeTablesBDD;
import com.example.merguez.easytrip.bdd.table_classes.ClasseBDD;


public class PassagersMain extends AppCompatActivity {

    private EditText passagerAdulteditText;
    private static EditText passagerEnfantedittext;
    private static Spinner passagerSPclasse;
    private static ArrayAdapter<CharSequence> adapter;
    private static Button retourAccueilBtn;
    private static Reservation reservation;
    private static Intent retourAccueil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passagers_main);

        retourAccueil = new Intent();

        reservation = (Reservation) getIntent().getSerializableExtra(Accueil.RESERVATION);
        getIntent().putExtra(Accueil.RESERVATION,reservation);

        passagerAdulteditText=(EditText)findViewById(R.id.passagerAdulteditText);
        passagerEnfantedittext=(EditText)findViewById(R.id.passagersEnfanteditText);
        passagerSPclasse=(Spinner) findViewById(R.id.passagerSPclasse);
        adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.passagerSPclasse,R.layout.passager_spinner);
        passagerSPclasse.setAdapter(adapter);
        ListeTablesBDD listeTablesBDD = new ListeTablesBDD(this);
        listeTablesBDD.open(this);
        ClasseBDD.getClasseIDwithNom(passagerSPclasse.getSelectedItem().toString());
        listeTablesBDD.close();
        retourAccueilBtn=(Button)findViewById(R.id.retourAccueilBtn);

        passagerAdulteditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        passagerEnfantedittext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

        passagerSPclasse.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ListeTablesBDD listeTablesBDD = new ListeTablesBDD(getApplicationContext());
                listeTablesBDD.open(getApplicationContext());
                Log.w("TAG",passagerSPclasse.getSelectedItem().toString());
                int classeID = ClasseBDD.getClasseIDwithNom(passagerSPclasse.getSelectedItem().toString());
                listeTablesBDD.close();
                reservation.setClasse(classeID);
                Log.w("TAG","" + classeID);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        retourAccueilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passagerAdulteditText.getText().toString().length() > 0){
                    reservation.setNbAdultes(Integer.parseInt(passagerAdulteditText.getText().toString()));
                } else {
                    reservation.setNbAdultes(0);
                }

                if (passagerEnfantedittext.getText().toString().length() > 0){
                    reservation.setNbEnfants(Integer.parseInt(passagerEnfantedittext.getText().toString()));
                } else {
                    reservation.setNbEnfants(0);
                }

                if (!(passagerSPclasse.getSelectedItem().toString().length() > 0)){
                    passagerSPclasse.setSelection(0);
                }
                retourAccueil.putExtra(Accueil.RESERVATION,reservation);
                setResult(2,retourAccueil);
                finish();
            }
        });


    }

}
