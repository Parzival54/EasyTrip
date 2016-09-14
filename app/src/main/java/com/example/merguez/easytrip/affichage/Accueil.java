package com.example.merguez.easytrip.affichage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.merguez.easytrip.R;

public class Accueil extends AppCompatActivity {

    private static EditText accueilETchoixDepart;
    private static EditText accueilETchoixArrivee;
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
        setContentView(R.layout.activity_accueil);

        accueilETchoixDepart = (EditText)findViewById(R.id.accueilETChoixDepart);
        accueilETchoixArrivee = (EditText)findViewById(R.id.accueilETChoixArrivee);
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
}
