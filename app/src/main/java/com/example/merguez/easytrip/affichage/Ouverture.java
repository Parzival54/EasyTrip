package com.example.merguez.easytrip.affichage;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.merguez.easytrip.R;
import com.example.merguez.easytrip.bdd.InsertionDonnees;
import com.example.merguez.easytrip.bdd.ListeTablesBDD;


public class Ouverture extends AppCompatActivity {
  Button resaButton;
    private static Button ouvertureBTconnexion;
    private static Intent ouvertureToConnexion;
    private static Intent ouvertureToAccueil;
    private static TextView ouvertureTVidentifiant;
    final static String PREFERENCES = "PREFERENCES";
    final static String EMAIL = "EMAIL";
    final static String CONNECTE = "CONNECTE";
    private static SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ouverture_main);

        preferences = getBaseContext().getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        preferences
                .edit()
                .putBoolean(CONNECTE, false)
                .apply();

        ouvertureBTconnexion = (Button)findViewById(R.id.ouvertureBTconnexion);
        ouvertureTVidentifiant = (TextView)findViewById(R.id.ouvertureTVidentifiant);

        /*ListeTablesBDD listeTablesBDD = new ListeTablesBDD(this);
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
                ouvertureToAccueil = new Intent(Ouverture.this,Accueil.class);
                startActivity(ouvertureToAccueil);


            }
        });

        ouvertureBTconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!preferences.getBoolean(CONNECTE, false)) {
                    ouvertureToConnexion = new Intent(Ouverture.this,Connexion.class);
                    startActivity(ouvertureToConnexion);
                } else {
                    afficherMessage();
                }

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (preferences.getBoolean(CONNECTE, false)) {
            ouvertureBTconnexion.setText("DECONNEXION");
            ouvertureTVidentifiant.setText("Connecté en tant que : " + preferences.getString(EMAIL,""));
        }
    }

    private void afficherMessage() {
        final AlertDialog alertDialog = new AlertDialog.Builder(Ouverture.this).create();
        alertDialog.setTitle("ATTENTION");
        alertDialog.setMessage("Voulez-vous vous déconnecter");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "ANNULER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "DECONNEXION", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                preferences
                        .edit()
                        .putBoolean(CONNECTE, false)
                        .putString(EMAIL, null)
                        .apply();
                ouvertureBTconnexion.setText("CONNEXION");
                ouvertureTVidentifiant.setText("");
            }
        });
        alertDialog.show();
    }

}
