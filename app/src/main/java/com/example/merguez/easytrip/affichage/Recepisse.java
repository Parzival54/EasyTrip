package com.example.merguez.easytrip.affichage;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.merguez.easytrip.R;
import com.example.merguez.easytrip.bdd.ListeTablesBDD;
import com.example.merguez.easytrip.bdd.table_classes.ClasseBDD;

public class Recepisse extends AppCompatActivity {

    private static Reservation reservation;
    private static TextView recepisseTVrecap;
    private static Button recepisseBTconfirmer;
    private static String recap;
    private static String trajet;
    private static String prix;
    private static String classe;
    private static String trajetAller;
    private static String trajetRetour = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recepisse_main);
        recepisseTVrecap = (TextView)findViewById(R.id.recepisseTVrecap);
        recepisseBTconfirmer = (Button)findViewById(R.id.recepisseBTconfirmer);

        reservation = (Reservation) getIntent().getSerializableExtra(Accueil.RESERVATION);
        trajet = getIntent().getExtras().getString("TRAJET");
        prix = getIntent().getExtras().getString("PRIX");
        classe = getIntent().getExtras().getString("CLASSE");
        ListeTablesBDD listeTablesBDD = new ListeTablesBDD(this);
        listeTablesBDD.open(this);
        classe = ClasseBDD.getClasseNomwithID(Integer.parseInt(classe.substring(classe.indexOf(":")+2)));
        listeTablesBDD.close();

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
                if (!reservation.isConnecte()) {
                    afficherMessage();
                }
            }
        });

    }

    private void afficherMessage() {
        final AlertDialog alertDialog = new AlertDialog.Builder(Recepisse.this).create();
        alertDialog.setTitle("ATTENTION");
        alertDialog.setMessage("Afin de pouvoir enregistrer votre vol,\nveuillez vous connecter.");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "ANNULER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "CONNEXION", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //ToDo: interface de connexion
            }
        });
        alertDialog.show();
    }
}
