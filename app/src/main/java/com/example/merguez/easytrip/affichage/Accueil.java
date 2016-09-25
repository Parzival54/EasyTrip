package com.example.merguez.easytrip.affichage;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
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
import com.example.merguez.easytrip.bdd.RequetesBDD;
import com.example.merguez.easytrip.bdd.table_aeroports.AeroportBDD;
import com.example.merguez.easytrip.bdd.table_classes.ClasseBDD;
import com.example.merguez.easytrip.bdd.table_users.User;
import com.example.merguez.easytrip.bdd.table_vols.Vol;
import com.example.merguez.easytrip.bdd.table_vols.VolList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Accueil extends AppCompatActivity {
    public static boolean accueilToRecherche = true;
    private static EditText accueilETchoixDepart;
    private static EditText accueilETchoixArrivee;
    private static TextView accueilTVdateDepart;
    private static TextView accueilTVdateArrivee;
    private static EditText accueilETdateDepart;
    private static EditText accueilETdateArrivee;
    private static CheckBox accueilCBallerRetour;
    private static Button accueilBTvalider;
    private static Button accueilNbPassagersButton;
    private static Button accueilBTdetail;
    private static Intent accueil_to_lieu;
    private static Intent accueil_to_resultat;
    private static Intent accueil_to_passagers;
    private static Spinner accueilSPclasse;
    private static ArrayAdapter<CharSequence> classeAdapter;
    final static String DEPART_OU_ARRIVEE = "depart ou arrivee";
    final static String RESERVATION = "reservation";
    private static Reservation reservation ;
    final static String LISTE_VOLS = "liste vols";
    private static VolList listeVols;
    final static String LISTE_VOLS_RETOUR = "liste vols retour";
    private static VolList listeVolsRetour;
    private static boolean estDepart;
    private static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil_main);
        listeVols = new VolList();
        reservation = new Reservation();
        accueilETchoixDepart = (EditText)findViewById(R.id.accueilETChoixDepart);
        accueilETchoixArrivee = (EditText)findViewById(R.id.accueilETChoixArrivee);
        accueilETdateDepart = (EditText)findViewById(R.id.accueilETdateDepart);
        accueilETdateArrivee = (EditText)findViewById(R.id.accueilETdateArrivee);
        accueilTVdateDepart = (TextView)findViewById(R.id.accueilTVdateDepart);
        accueilTVdateArrivee = (TextView)findViewById(R.id.accueilTVdateArrivee);
        accueilCBallerRetour = (CheckBox)findViewById(R.id.accueilCBallerRetour);
        accueilBTvalider = (Button)findViewById(R.id.accueilBTvalider);
        accueilBTdetail = (Button)findViewById(R.id.accueilBTdetail);
        //declarer le Button Nb passagers + class
        accueilNbPassagersButton=(Button)findViewById(R.id.accueilNbPassagersButton);
        accueilTVdateArrivee.setVisibility(View.GONE);
        accueilETdateArrivee.setVisibility(View.GONE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                reservation.setNbAdultes(1);
                ListeTablesBDD listeTablesBDD = new ListeTablesBDD(getApplicationContext());
                listeTablesBDD.open(getApplicationContext());
                reservation.setClasse(ClasseBDD.getFirstID());
                listeTablesBDD.close();
                accueil_to_lieu = new Intent(Accueil.this,ChoixLieu.class);
                accueil_to_resultat = new Intent(Accueil.this,Recherche.class);
                accueil_to_passagers = new Intent(Accueil.this,PassagersMain.class);
            }
        }).start();

        //ToDo: créer une icone (+ splash?)
        //ToDo: ajouter récap nb passagers + classe sur la page accueil



        // sélection de l'aéroport de départ -> ouverture de l'activité ChoixLieu
        accueilETchoixDepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        estDepart = true;
                        accueil_to_lieu.putExtra(Accueil.DEPART_OU_ARRIVEE, estDepart);
                        accueil_to_lieu.putExtra(Accueil.RESERVATION, reservation);
                        startActivityForResult(accueil_to_lieu, 1);
                    }
                }).start();
            }
        });

        // sélection de l'aéroport d'arrivée -> ouverture de l'activité ChoixLieu

        accueilETchoixArrivee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        estDepart = false;
                        accueil_to_lieu.putExtra(Accueil.DEPART_OU_ARRIVEE, estDepart);
                        accueil_to_lieu.putExtra(Accueil.RESERVATION, reservation);
                        startActivityForResult(accueil_to_lieu, 1);
                    }
                }).start();
            }
        });


        // sélection de la date de départ -> ouverture de l'activité Calendrier (DialogFragment)

        accueilETdateDepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        estDepart = true;
                        ouvrirCalendrier();
                    }
                }).start();
            }
        });


        // sélection de la date de retour -> ouverture de l'activité Calendrier (DialogFragment)
        accueilETdateArrivee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        estDepart = false;
                        if (accueilCBallerRetour.isChecked()){
                            ouvrirCalendrier();
                        }
                    }
                }).start();
            }
        });


        // vérification du choix aller simple ou aller retour via un checkbox
        accueilCBallerRetour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (accueilCBallerRetour.isChecked()){
                            accueilTVdateArrivee.post(new Runnable() {
                                @Override
                                public void run() {
                                    accueilTVdateArrivee.setVisibility(View.VISIBLE);
                                }
                            });
                            accueilETdateArrivee.post(new Runnable() {
                                @Override
                                public void run() {
                                    accueilETdateArrivee.setVisibility(View.VISIBLE);
                                }
                            });
                            reservation.setAllerRetour(true);
                        } else {
                            accueilTVdateArrivee.post(new Runnable() {
                                @Override
                                public void run() {
                                    accueilTVdateArrivee.setVisibility(View.GONE);
                                }
                            });
                            accueilETdateArrivee.post(new Runnable() {
                                @Override
                                public void run() {
                                    accueilETdateArrivee.setVisibility(View.GONE);
                                }
                            });
                            reservation.setAllerRetour(false);
                        }
                    }
                }).start();
            }
        });

        accueilBTdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(Accueil.this).create();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        afficherDetail(alertDialog);
                    }
                }).start();
                alertDialog.show();
            }
        });


        // recherche des aéroports avec les données choisies puis passage à l'activité Recherche pour l'affichage des résultats
        accueilBTvalider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (accueilETdateDepart.getText().toString().length() > 0){
                            reservation.setDateAller(accueilETdateDepart.getText().toString());
                        }
                        if (accueilETdateArrivee.getText().toString().length() > 0){
                            reservation.setDateRetour(accueilETdateArrivee.getText().toString());
                        }
                        if (reservation.estComplete() && !reservation.getAitaDepart().equals(reservation.getAitaArrivee())){
                            selectionListeVols();
                            setDecalageHoraire();
                            accueil_to_resultat.putExtra(Accueil.RESERVATION, reservation);
                            accueil_to_resultat.putExtra(Accueil.LISTE_VOLS,(Parcelable)listeVols);
                            if (reservation.isAllerRetour())
                                accueil_to_resultat.putExtra(Accueil.LISTE_VOLS_RETOUR, (Parcelable)listeVolsRetour);
                            startActivity(accueil_to_resultat);
                        } else {
                            messageErreur();
                        }
                    }
                }).start();
            }
        });


        // sélection du nombre de passagers et de la classe -> ouverture de l'activité Passagers
        accueilNbPassagersButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        accueil_to_passagers.putExtra(Accueil.RESERVATION,reservation);
                        startActivityForResult(accueil_to_passagers, 2);
                    }
                }).start();
            }
        });




    }

    private void messageErreur() {
        String messageErreur = "Veuillez renseigner les infos suivantes :";
        if (reservation.getAitaDepart() == null) messageErreur += "\nAéroport de départ";
        if (reservation.getAitaArrivee() == null) messageErreur += "\nAéroport d'arrivée";
        if (reservation.getDateAller() == null) messageErreur += "\nDate de départ";
        if (reservation.getDateRetour() == null && reservation.isAllerRetour()) messageErreur += "\nDate de retour";
        if (reservation.getNbAdultes() + reservation.getNbEnfants() == 0) messageErreur += "\nNombre de passagers";
        if (reservation.getClasse() == 0) messageErreur += "\nClasse";
        if (accueilETchoixDepart.getText().toString().equals(accueilETchoixArrivee.getText().toString())) {
            messageErreur += "\n\nVeuillez corriger l'erreur suivante:\nL'aéroport d'arrivée est identique\nà l'aéroport de départ";
        }
        Toast.makeText(getApplicationContext(),messageErreur, Toast.LENGTH_LONG).show();
    }

    private void afficherDetail(AlertDialog alertDialog) {
        alertDialog.setTitle("Récapitulatif");
        String recap = "";
        if (reservation.getAitaDepart() != null) {
            recap += reservation.getNomDepart() + " (" + reservation.getAitaDepart() + ")";
        } else {
            recap += "?";
        }

        if (reservation.getAitaArrivee() != null) {
            recap += " \u2794 " + reservation.getNomArrivee() + " (" + reservation.getAitaArrivee() + ")";
        } else {
            recap += " \u2794 " + "?";
        }

        if (accueilETdateDepart.getText().toString().length() > 0) {
            recap += "\nDate de départ : " + accueilETdateDepart.getText().toString();
        } else {
            recap += "\nDate de départ : ?";
        }

        if (accueilETdateArrivee.getText().toString().length() > 0) {
            recap += "\nDate de retour : " + accueilETdateArrivee.getText().toString();
        } else if (reservation.isAllerRetour()){
            recap += "\nDate de retour : ?";
        }

        recap += "\nNb Passagers : " + (reservation.getNbAdultes() + reservation.getNbEnfants()) + "\n"
                + "Adultes : "+ reservation.getNbAdultes() + "\n"
                + "Enfants : "+ reservation.getNbEnfants() + "\n";

        if (reservation.getClasse() > 0) {
            ListeTablesBDD listeTablesBDD = new ListeTablesBDD(getApplicationContext());
            listeTablesBDD.open(getApplicationContext());
            recap += "Classe : " + ClasseBDD.getClasseNomwithID(reservation.getClasse());
            listeTablesBDD.close();
        } else {
            recap += "Classe : ?";
        }

        alertDialog.setMessage(recap);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
    }

    private void setDecalageHoraire() {
        ListeTablesBDD listeTablesBDD = new ListeTablesBDD(getApplicationContext());
        listeTablesBDD.open(getApplicationContext());
        int decalageHoraire = AeroportBDD.getTimezoneWithAita(reservation.getAitaArrivee())
                - AeroportBDD.getTimezoneWithAita(reservation.getAitaDepart());
        listeTablesBDD.close();
        reservation.setDecalageHoraire(decalageHoraire);
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

    private void selectionListeVols() {
        int idClasse = 1;
        String depAita = reservation.getAitaDepart();
        String arrAita = reservation.getAitaArrivee();
        RequetesBDD requeteBDD = new RequetesBDD(this);
        requeteBDD.open();
        listeVols = requeteBDD.getVolsWithAita(depAita, arrAita, idClasse);
        if (reservation.isAllerRetour())
            listeVolsRetour = new VolList();
            listeVolsRetour = requeteBDD.getVolsWithAita(arrAita, depAita ,idClasse);
        requeteBDD.close();
        }
    }


