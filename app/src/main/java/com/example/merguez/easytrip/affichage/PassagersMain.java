package com.example.merguez.easytrip.affichage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.merguez.easytrip.R;



public class PassagersMain extends AppCompatActivity {

    public static EditText passagerAdulteditText;
    public static EditText passagerEnfantedittext;
    public static EditText classeditText;
    public static Button retourAccueilBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passagers_main);

        passagerAdulteditText=(EditText)findViewById(R.id.passagerAdulteditText);
        passagerEnfantedittext=(EditText)findViewById(R.id.passagersEnfanteditText);
        classeditText=(EditText)findViewById(R.id.classeditText);
        retourAccueilBtn=(Button)findViewById(R.id.retourAccueilBtn);







    }
}
