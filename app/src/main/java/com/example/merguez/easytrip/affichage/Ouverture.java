package com.example.merguez.easytrip.affichage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.merguez.easytrip.R;
import com.example.merguez.easytrip.bdd.InsertionDonnees;
import com.example.merguez.easytrip.bdd.ListeTablesBDD;


public class Ouverture extends AppCompatActivity {
  Button resaButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ouverture_main);
       /* ListeTablesBDD listeTablesBDD = new ListeTablesBDD(this);
        listeTablesBDD.open(this);
        InsertionDonnees.insertionDonnees(this);
        listeTablesBDD.close();*/

        //recup du nbre de passagers
        final String[] nb_passagers = new String[1];


        //déclarer le Button Resa
        resaButton=(Button)findViewById(R.id.resa_button);


        //mise en place de l'intent pour aller a TravelBooking;
        resaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadNewActivity = new Intent(Ouverture.this,Accueil.class);
                startActivity(intentLoadNewActivity);


            }
        });


    }
}
