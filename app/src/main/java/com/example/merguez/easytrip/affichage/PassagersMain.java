package com.example.merguez.easytrip.affichage;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.merguez.easytrip.R;



public class PassagersMain extends AppCompatActivity {

    private EditText passagerAdulteditText;
    private static EditText passagerEnfantedittext;
    private static EditText classeditText;
    private static Button retourAccueilBtn;
    Reservation reservation;
    private static Intent retourAccueil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passagers_main);

        passagerAdulteditText=(EditText)findViewById(R.id.passagerAdulteditText);
        passagerEnfantedittext=(EditText)findViewById(R.id.passagersEnfanteditText);
        classeditText=(EditText)findViewById(R.id.classeditText);
       retourAccueilBtn=(Button)findViewById(R.id.retourAccueilBtn);
        retourAccueil = new Intent(PassagersMain.this, Accueil.class);

        String theText=passagerAdulteditText.getText().toString();
//        Intent i = new Intent(PassagersMain.this,Ouverture.class);
//        i.putExtra("nb_passagers",theText);
//        startActivity(i);

        passagerAdulteditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PassagersMain.this, " Il y aura  "+ passagerAdulteditText.getText().toString(),Toast.LENGTH_LONG).show();
            }
        });

        passagerEnfantedittext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(PassagersMain.this,"Il y aura  "+ passagerEnfantedittext.getText().toString(),Toast.LENGTH_LONG).show();
            }
        });

        classeditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PassagersMain.this,"Votre classe sera  "+ classeditText.getText().toString(),Toast.LENGTH_LONG).show();

            }
        });
        retourAccueilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reservation = (Reservation) getIntent().getExtras().getSerializable(Accueil.RESERVATION);
                reservation.setNbAdultes(2);
                reservation.setNbEnfants(2);
                reservation.setClasse(classeditText.getText().toString());
                retourAccueil.putExtra(Accueil.RESERVATION,reservation);
                startActivity(retourAccueil);

            }
        });


    }

    private void afficherClavier() {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        );

    }
}
