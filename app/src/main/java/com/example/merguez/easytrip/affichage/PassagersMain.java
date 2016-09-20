package com.example.merguez.easytrip.affichage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passagers_main);

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
