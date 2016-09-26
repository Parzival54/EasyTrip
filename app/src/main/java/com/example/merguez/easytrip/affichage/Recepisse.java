package com.example.merguez.easytrip.affichage;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.merguez.easytrip.R;
import com.example.merguez.easytrip.bdd.ListeTablesBDD;
import com.example.merguez.easytrip.bdd.table_classes.ClasseBDD;
import com.example.merguez.easytrip.bdd.table_enregistrements.Enregistrement;
import com.example.merguez.easytrip.bdd.table_enregistrements.EnregistrementBDD;
import com.example.merguez.easytrip.bdd.table_users.UserBDD;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Recepisse extends AppCompatActivity {

    private static Reservation reservation;
    private static TextView recepisseTVrecap;
    private static TextView recepisseTVidentifiant;
    private static Button recepisseBTconfirmer;
    private static String recap;
    private static String trajet;
    private static String prix;
    private static String classe;
    private static String trajetAller;
    private static String trajetRetour = "";
    private static SharedPreferences preferences;
    private static Intent recepisseToConnexion;
    private static Intent recepisseToOuverture;
    private static Enregistrement enregistrement;
    private static ListeTablesBDD listeTablesBDD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recepisse_main);
        listeTablesBDD = new ListeTablesBDD(getApplicationContext());
        recepisseTVrecap = (TextView)findViewById(R.id.recepisseTVrecap);
        recepisseTVidentifiant = (TextView)findViewById(R.id.recepisseTVidentifiant);
        recepisseBTconfirmer = (Button)findViewById(R.id.recepisseBTconfirmer);
        preferences = getBaseContext().getSharedPreferences(Ouverture.PREFERENCES, MODE_PRIVATE);
        recepisseToConnexion = new Intent(Recepisse.this,Connexion.class);
        recepisseToOuverture = new Intent(Recepisse.this,Ouverture.class);
        enregistrement = new Enregistrement();

        if (preferences.getBoolean(Ouverture.CONNECTE, false)) {
            recepisseTVidentifiant.setText("Connecté en tant que : " + preferences.getString(Ouverture.EMAIL,""));
        }

        reservation = (Reservation) getIntent().getSerializableExtra(Accueil.RESERVATION);
        trajet = getIntent().getExtras().getString("TRAJET");
        prix = getIntent().getExtras().getString("PRIX");
        classe = getIntent().getExtras().getString("CLASSE");
        classe = classe.substring(classe.indexOf(":")+2);

        if (reservation.isAllerRetour()) {
            trajetAller = "TRAJET ALLER : " + trajet + "\n"
                    + "DATE ALLER : " + reservation.toString().substring(reservation.toString().indexOf("dateAller") + 11,
                    reservation.toString().indexOf("dateAller") + 21) + "\n";
            trajetRetour = "TRAJET RETOUR : " + trajet + "\n"
                    + "DATE RETOUR : " + reservation.toString().substring(reservation.toString().indexOf("dateRetour") + 12,
                    reservation.toString().indexOf("dateRetour") + 22) + "\n";
        } else {
            trajetAller = "TRAJET : " + trajet + "\n"
                    + "DATE : " + reservation.toString().substring(reservation.toString().indexOf("dateAller") + 11,
                    reservation.toString().indexOf("dateAller") + 21) + "\n";
        }

        recap = trajetAller + trajetRetour
                + "PRIX : " + prix.substring(prix.indexOf("total:") + 7) + "\n"
                + "CLASSE : " + classe;

        recepisseTVrecap.setText(recap);

        recepisseBTconfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dureeConnexion = (int)(System.currentTimeMillis() - preferences.getLong(Ouverture.DERNIERE_CONNEXION,System.currentTimeMillis()));
                if (preferences.getBoolean(Ouverture.CONNECTE, false)) {
                    if (dureeConnexion < Ouverture.DUREE_CONNEXION_MAXI) {
                        afficherMessageValidation();
                        completerEnregistrement();
                        // TODO : ajouter vol retour
                        // TODO : intégrer Maps
                    } else {
                        preferences
                                .edit()
                                .putBoolean(Ouverture.CONNECTE, false)
                                .apply();
                        afficherMessageReconnexion();
                    }

                } else {
                    afficherMessageConnexion();
                }
            }
        });

    }

    private void afficherMessageConnexion() {
        final AlertDialog alertDialog = new AlertDialog.Builder(Recepisse.this).create();
        alertDialog.setTitle("ATTENTION");
        alertDialog.setMessage("Afin de pouvoir enregistrer votre vol,\nveuillez-vous connecter.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "ANNULER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "CONNEXION", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(recepisseToConnexion);
            }
        });
        alertDialog.show();
    }

    private void afficherMessageReconnexion() {
        final AlertDialog alertDialog = new AlertDialog.Builder(Recepisse.this).create();
        alertDialog.setTitle("CONNEXION EXPIREE");
        alertDialog.setMessage("Veuillez vous reconnecter pour valider votre réservation.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "ANNULER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                recepisseTVidentifiant.setText("");
                alertDialog.dismiss();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "RECONNEXION", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(recepisseToConnexion);
            }
        });
        alertDialog.show();
    }

    private void afficherMessageValidation() {
        final AlertDialog alertDialog = new AlertDialog.Builder(Recepisse.this).create();
        alertDialog.setTitle("DERNIERE ETAPE");
        alertDialog.setMessage("Voulez-vous confirmer cette réservation ?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "ANNULER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "CONFIRMER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                preferences
                        .edit()
                        .putBoolean(Ouverture.VOL_ENREGISTRE, true)
                        .apply();
                startActivity(recepisseToOuverture);
            }
        });
        alertDialog.show();
    }

    private void completerEnregistrement() {
        listeTablesBDD.open(getApplicationContext());
        int userID = UserBDD.getUserIDithNom(preferences.getString(Ouverture.EMAIL, null));
        enregistrement.setUserID(userID);
        // TODO : récupérer volID
        enregistrement.setVolAllerID(1);
        enregistrement.setVolRetourID(1);
        enregistrement.setNbAdultes(reservation.getNbAdultes());
        enregistrement.setNbEnfants(reservation.getNbEnfants());
        enregistrement.setPrixTotal(Double.parseDouble(prix.substring(prix.indexOf("total:") + 7, prix.indexOf("€") - 1)));
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.FRENCH);
        String timestamp = format.format(System.currentTimeMillis());
        enregistrement.setDateCreation(timestamp);
        EnregistrementBDD.insertEnregistrement(enregistrement);
        listeTablesBDD.close();
    }

}
