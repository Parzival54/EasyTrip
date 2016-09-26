package com.example.merguez.easytrip.affichage;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.UiThread;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private static TextView ouvertureTVlisteReservations;
    final static String PREFERENCES = "PREFERENCES";
    final static String EMAIL = "EMAIL";
    final static String CONNECTE = "CONNECTE";
    final static String VOL_ENREGISTRE = "VOL_ENREGISTRE";
    final static String DERNIERE_CONNEXION = "DERNIERE_CONNEXION";
    final static int DUREE_CONNEXION_MAXI = 15 * 1000 * 60; // La première valeur correspond au nombre de minutes
    private static SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ouverture_main);

        preferences = getBaseContext().getSharedPreferences(PREFERENCES, MODE_PRIVATE);

        ouvertureBTconnexion = (Button)findViewById(R.id.ouvertureBTconnexion);
        resaButton=(Button)findViewById(R.id.resa_button);
        ouvertureTVidentifiant = (TextView)findViewById(R.id.ouvertureTVidentifiant);
        ouvertureTVlisteReservations = (TextView)findViewById(R.id.ouvertureTVlisteReservations);
        ouvertureTVlisteReservations.setClickable(true);
        ouvertureToAccueil = new Intent(Ouverture.this,Accueil.class);
        ouvertureToConnexion = new Intent(Ouverture.this,Connexion.class);

        /*ListeTablesBDD listeTablesBDD = new ListeTablesBDD(this);
        listeTablesBDD.open(this);
        InsertionDonnees.insertionDonnees(this);
        listeTablesBDD.close();*/

        final AlertDialog connexion = new AlertDialog.Builder(Ouverture.this).create();
        verificationConnexion(connexion);

        //mise en place de l'intent pour aller a TravelBooking;
        resaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(ouvertureToAccueil);
                    }
                }).start();
            }
        });

        ouvertureBTconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(Ouverture.this).create();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!preferences.getBoolean(CONNECTE, false)) {
                            startActivity(ouvertureToConnexion);
                        } else {
                            afficherMessageDeconnexion(alertDialog);
                        }
                    }
                }).start();
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (preferences.getBoolean(VOL_ENREGISTRE, false)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            afficherMessageVolEnregistre();
                        }
                    });

                }
            }
        }).start();

        ouvertureTVlisteReservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO : afficher liste réservations
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        final AlertDialog reconnexion = new AlertDialog.Builder(Ouverture.this).create();
        new Thread(new Runnable() {
            @Override
            public void run() {
                verificationConnexion(reconnexion);
            }
        }).start();
    }

    private void verificationConnexion(final AlertDialog alertDialog) {
        int dureeConnexion = (int)(System.currentTimeMillis() - preferences.getLong(DERNIERE_CONNEXION,System.currentTimeMillis()));
        if (preferences.getBoolean(CONNECTE, false)) {
            if (dureeConnexion < DUREE_CONNEXION_MAXI) {
                ouvertureBTconnexion.post(new Runnable() {
                    @Override
                    public void run() {
                        ouvertureBTconnexion.setText("DECONNEXION");
                    }
                });
                ouvertureTVidentifiant.post(new Runnable() {
                    @Override
                    public void run() {
                        ouvertureTVidentifiant.setText("Connecté en tant que : \n" + preferences.getString(EMAIL,""));
                    }
                });
                ouvertureTVlisteReservations.post(new Runnable() {
                    @Override
                    public void run() {
                        ouvertureTVlisteReservations.setText("Vos réservations");
                        ouvertureTVlisteReservations.setBackgroundResource(R.drawable.rect_bleufonce_bord_noir);
                    }
                });
            } else {
                afficherMessageReconnexion(alertDialog);
            }

        } else {
            ouvertureBTconnexion.post(new Runnable() {
                @Override
                public void run() {
                    ouvertureBTconnexion.setText("CONNEXION");
                }
            });
            ouvertureTVidentifiant.post(new Runnable() {
                @Override
                public void run() {
                    ouvertureTVidentifiant.setText("");
                }
            });
            ouvertureTVlisteReservations.post(new Runnable() {
                @Override
                public void run() {
                    ouvertureTVlisteReservations.setText("");
                    ouvertureTVlisteReservations.setBackgroundResource(R.color.blue);
                }
            });
        }
    }

    private void afficherMessageDeconnexion(final AlertDialog alertDialog) {
        alertDialog.setTitle("ATTENTION");
        alertDialog.setMessage("Voulez-vous vous déconnecter ?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "ANNULER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog.dismiss();
                    }
                });
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
                verificationConnexion(alertDialog);
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.show();
            }
        });
    }

    private void afficherMessageReconnexion(final AlertDialog alertDialog) {
        preferences
                .edit()
                .putBoolean(CONNECTE, false)
                .apply();
        alertDialog.setTitle("CONNEXION EXPIREE");
        alertDialog.setMessage("Voulez-vous vous reconnecter ?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "ANNULER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ouvertureBTconnexion.post(new Runnable() {
                    @Override
                    public void run() {
                        ouvertureBTconnexion.setText("CONNEXION");
                    }
                });
                ouvertureTVidentifiant.post(new Runnable() {
                    @Override
                    public void run() {
                        ouvertureTVidentifiant.setText("");
                    }
                });
                ouvertureTVlisteReservations.post(new Runnable() {
                    @Override
                    public void run() {
                        ouvertureTVlisteReservations.setText("");
                        ouvertureTVlisteReservations.setBackgroundResource(R.color.blue);
                    }
                });
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog.dismiss();
                    }
                });
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "RECONNEXION", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(ouvertureToConnexion);
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.show();
            }
        });
    }

    private void afficherMessageVolEnregistre() {
        final AlertDialog alertDialog = new AlertDialog.Builder(Ouverture.this).create();
        verificationConnexion(alertDialog);
        alertDialog.setTitle("VOL ENREGISTRE");
        alertDialog.setMessage("Votre réservation a bien été validée");
        preferences
                .edit()
                .putBoolean(VOL_ENREGISTRE, false)
                .apply();
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

}
