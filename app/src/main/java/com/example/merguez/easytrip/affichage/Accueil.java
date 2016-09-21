package com.example.merguez.easytrip.affichage;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.merguez.easytrip.R;
import com.example.merguez.easytrip.bdd.InsertionDonnees;
import com.example.merguez.easytrip.bdd.ListeTablesBDD;
import com.example.merguez.easytrip.bdd.table_aeroports.AeroportBDD;

import java.util.List;

public class Accueil extends AppCompatActivity {

    private static EditText accueilETchoixDepart;
    private static EditText accueilETchoixArrivee;
    private static TextView accueilTVdateDepart;
    private static TextView accueilTVdateArrivee;
    private static EditText accueilETdateDepart;
    private static EditText accueilETdateArrivee;
    private static CheckBox accueilCBallerRetour;
    private static Button accueilBTvalider;
    private static Button accueilNbPassagersButton;
    private static Intent accueil_to_lieu;
    private static Intent accueil_to_resultat;
    private static Intent accueil_to_passagers;
    private static Spinner accueilSPclasse;
    private static ArrayAdapter<CharSequence> classeAdapter;
    final static String DEPART_OU_ARRIVEE = "depart ou arrivee";
    final static String RESERVATION = "reservation";
    private static Reservation reservation = new Reservation();
    private static boolean estDepart;
    private static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil_main);

        accueilETchoixDepart = (EditText)findViewById(R.id.accueilETChoixDepart);
        accueilETchoixArrivee = (EditText)findViewById(R.id.accueilETChoixArrivee);
        accueilETdateDepart = (EditText)findViewById(R.id.accueilETdateDepart);
        accueilETdateArrivee = (EditText)findViewById(R.id.accueilETdateArrivee);
        accueilTVdateDepart = (TextView)findViewById(R.id.accueilTVdateDepart);
        accueilTVdateArrivee = (TextView)findViewById(R.id.accueilTVdateArrivee);
        accueilCBallerRetour = (CheckBox)findViewById(R.id.accueilCBallerRetour);
        accueilBTvalider = (Button)findViewById(R.id.accueilBTvalider);
        //declarer le Button Nb passagers + class
        accueilNbPassagersButton=(Button)findViewById(R.id.accueilNbPassagersButton);
        accueilTVdateArrivee.setVisibility(View.GONE);
        accueilETdateArrivee.setVisibility(View.GONE);

        accueil_to_lieu = new Intent(Accueil.this,ChoixLieu.class);
        accueil_to_resultat = new Intent(Accueil.this,Recherche.class);
        accueil_to_passagers = new Intent(Accueil.this,PassagersMain.class);


        // sélection de l'aéroport de départ -> ouverture de l'activité ChoixLieu
        accueilETchoixDepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estDepart = true;
                accueil_to_lieu.putExtra(Accueil.DEPART_OU_ARRIVEE, estDepart);
                accueil_to_lieu.putExtra(Accueil.RESERVATION, reservation);
                startActivityForResult(accueil_to_lieu, 1);
            }
        });

        // sélection de l'aéroport d'arrivée -> ouverture de l'activité ChoixLieu
        accueilETchoixArrivee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estDepart = false;
                accueil_to_lieu.putExtra(Accueil.DEPART_OU_ARRIVEE, estDepart);
                accueil_to_lieu.putExtra(Accueil.RESERVATION, reservation);
                startActivityForResult(accueil_to_lieu, 1);
            }
        });

        // sélection de la date de départ -> ouverture de l'activité Calendrier (DialogFragment)
        accueilETdateDepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estDepart = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ouvrirCalendrier();
                    }
                }).start();
            }
        });

        // sélection de la date de retour -> ouverture de l'activité Calendrier (DialogFragment)
        accueilETdateArrivee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estDepart = false;
                if (accueilCBallerRetour.isChecked()){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ouvrirCalendrier();
                        }
                    }).start();
                }
            }
        });

        // vérification du choix aller simple ou aller retour via un checkbox
        accueilCBallerRetour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (accueilCBallerRetour.isChecked()){
                    accueilTVdateArrivee.setVisibility(View.VISIBLE);
                    accueilETdateArrivee.setVisibility(View.VISIBLE);
                    reservation.setAllerRetour(true);
                } else {
                    accueilTVdateArrivee.setVisibility(View.GONE);
                    accueilETdateArrivee.setVisibility(View.GONE);
                    reservation.setAllerRetour(false);
                }
            }
        });

        // recherche des aéroports avec les données choisies puis passage à l'activité Recherche pour l'affichage des résultats
        accueilBTvalider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accueilETdateDepart.getText().toString().length() > 0){
                    reservation.setDateAller(accueilETdateDepart.getText().toString());
                }
                if (accueilETdateArrivee.getText().toString().length() > 0){
                    reservation.setDateRetour(accueilETdateArrivee.getText().toString());
                }
                if (reservation.estComplete() && !reservation.getAitaDepart().equals(reservation.getAitaArrivee())){
                    startActivity(accueil_to_resultat);
                } else {
                    String messageErreur = "Veuillez renseigner les infos suivantes :";
                    if (reservation.getAitaDepart() == null) messageErreur += "\nAéroport de départ";
                    if (reservation.getAitaArrivee() == null) messageErreur += "\nAéroport d'arrivée";
                    if (reservation.getDateAller() == null) messageErreur += "\nDate de départ";
                    if (reservation.getDateRetour() == null && reservation.isAllerRetour()) messageErreur += "\nDate de retour";
                    if (reservation.getNbAdultes() + reservation.getNbEnfants() == 0) messageErreur += "\nNombre de passagers";
                    if (reservation.getClasse() == null) messageErreur += "\nClasse";
                    if (accueilETchoixDepart.getText().toString().equals(accueilETchoixArrivee.getText().toString())) {
                        messageErreur += "\n\nVeuillez corriger l'erreur suivante:\nL'aéroport d'arrivée est identique\nà l'aéroport de départ";
                    }

                    Toast.makeText(getApplicationContext(),messageErreur, Toast.LENGTH_LONG).show();
                }

            }
        });

        // sélection du nombre de passagers et de la classe -> ouverture de l'activité Passagers
        accueilNbPassagersButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                accueil_to_passagers.putExtra(Accueil.RESERVATION,reservation);
                startActivityForResult(accueil_to_passagers, 2);
            }
        });



    }

    @Override
    // affichage des aéroports en fonction de la sélection faite dans l'activité ChoixLieu
    // + récupération du nombre de passagers et de la classe
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        reservation = (Reservation) data.getSerializableExtra(RESERVATION);
        switch (resultCode) {
            case 0:
                break;
            case 1:
            if (estDepart) {
                    String affichageDepart = reservation.getNomDepart() + " (" + reservation.getAitaDepart() + ")";
                    accueilETchoixDepart.setText(affichageDepart);
                } else {
                    String affichageArrivee = reservation.getNomArrivee() + " (" + reservation.getAitaArrivee() + ")";
                    accueilETchoixArrivee.setText(affichageArrivee);
                }
                break;
            case 2:

                break;

        }

    }

    // ouverture du DialogFragment Calendrier pour choisir une date
    private void ouvrirCalendrier(){
        DialogFragment dialogFragment = Calendrier.newInstance(1);
        dialogFragment.show(getFragmentManager(), "dialog");
    }

    // getters et setters

    public static void setAccueilETdateDepart(String date){
        accueilETdateDepart.setText(date);
    }

    public static void setAccueilETdateArrivee(String date){
        accueilETdateArrivee.setText(date);
    }

    public static String getAccueilETdateDepart(){
        return accueilETdateDepart.getText().toString();
    }

    public static String getAccueilETdateArrivee(){
        return accueilETdateArrivee.getText().toString();
    }

    public static boolean getEstDepart(){
        return estDepart;
    }

    public Context getContext() {
        return getApplicationContext();
    }

}
