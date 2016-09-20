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

import com.example.merguez.easytrip.R;



public class PassagersMain extends AppCompatActivity {

    private EditText passagerAdulteditText;
    private static EditText passagerEnfantedittext;
    private static EditText classeditText;
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
        classeditText=(EditText)findViewById(R.id.classeditText);
       retourAccueilBtn=(Button)findViewById(R.id.retourAccueilBtn);

        String theText=passagerAdulteditText.getText().toString();
//        Intent i = new Intent(PassagersMain.this,Ouverture.class);
//        i.putExtra("nb_passagers",theText);
//        startActivity(i);

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

        classeditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        retourAccueilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reservation.setNbAdultes(Integer.parseInt(passagerAdulteditText.getText().toString()));
                reservation.setNbEnfants(Integer.parseInt(passagerEnfantedittext.getText().toString()));
                reservation.setClasse(classeditText.getText().toString());
                retourAccueil.putExtra(Accueil.RESERVATION,reservation);
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
}
