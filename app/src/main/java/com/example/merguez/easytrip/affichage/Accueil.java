package com.example.merguez.easytrip.affichage;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.merguez.easytrip.R;

public class Accueil extends AppCompatActivity {

    private static EditText accueilETchoixDepart;
    private static EditText accueilETchoixArrivee;
    private static EditText accueilETdateDepart;
    private static EditText accueilETdateArrivee;
    private static Intent accueil_to_lieu;
    private static Bundle extras;
    final static String DEPART = "depart";
    final static String ARRIVEE = "arrivee";
    final static String DEPART_OU_ARRIVEE = "depart ou arrivee";
    private static boolean estDepart;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil_main);

        accueilETchoixDepart = (EditText)findViewById(R.id.accueilETChoixDepart);
        accueilETchoixArrivee = (EditText)findViewById(R.id.accueilETChoixArrivee);
        accueilETdateDepart = (EditText)findViewById(R.id.accueilETdateDepart);
        accueilETdateArrivee = (EditText)findViewById(R.id.accueilETdateArrivee);
        accueil_to_lieu = new Intent(Accueil.this,ChoixLieu.class);

        accueilETchoixDepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estDepart = true;
                accueil_to_lieu.putExtra(Accueil.DEPART_OU_ARRIVEE, estDepart);
                startActivityForResult(accueil_to_lieu, REQUEST_CODE);
            }
        });

        accueilETchoixArrivee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estDepart = false;
                accueil_to_lieu.putExtra(Accueil.DEPART_OU_ARRIVEE, estDepart);
                startActivityForResult(accueil_to_lieu, REQUEST_CODE);
            }
        });

        accueilETdateDepart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estDepart = true;
                ouvrirCalendrier();
            }
        });

        accueilETdateArrivee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estDepart = false;
                ouvrirCalendrier();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE == 1) {
            if (resultCode == Activity.RESULT_OK) {
                if (estDepart) {
                    accueilETchoixDepart.setText(data.getStringExtra(Accueil.DEPART));
                } else {
                    accueilETchoixArrivee.setText(data.getStringExtra(Accueil.ARRIVEE));
                }
            }
        }
    }

    private void ouvrirCalendrier(){
        DialogFragment dialogFragment = Calendrier.newInstance(1);
        dialogFragment.show(getFragmentManager(), "dialog");
    }

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

}
