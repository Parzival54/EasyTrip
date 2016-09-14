package com.example.merguez.easytrip.bdd;

import android.content.Context;

import com.example.merguez.easytrip.R;
import com.example.merguez.easytrip.bdd.table_aeroports.Aeroport;
import com.example.merguez.easytrip.bdd.table_aeroports.AeroportBDD;
import com.example.merguez.easytrip.bdd.table_vols.Vol;
import com.example.merguez.easytrip.bdd.table_vols.VolBDD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by merguez on 13/09/2016.
 */
public class InsertionDonnees {

    public static void insertionDonnees(Context context) {

        InputStream fic = context.getResources().openRawResource(R.raw.aeroports);
        InputStreamReader streamReader = new InputStreamReader(fic);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while (( line = bufferedReader.readLine()) != null) {
                Aeroport aeroport = new Aeroport();
                text.append(line);
                text.append('\n');

                int index1 = line.indexOf(",");
                int index2 = line.indexOf(",",index1+1);
                int index3 = line.indexOf(",",index2+1);
                int index4 = line.indexOf(",",index3+1);
                int index5 = line.indexOf(",",index4+1);
                int index6 = line.indexOf(",",index5+1);
                int index7 = line.indexOf(",",index6+1);
                int index8 = line.indexOf(",",index7+1);

                aeroport.setAita(line.substring(index4 + 2,index5 - 1));
                aeroport.setNom(line.substring(index1 + 2,index2 - 1));
                aeroport.setVille(line.substring(index2 + 2,index3 - 1));
                aeroport.setPays(line.substring(index3 + 2,index4 - 1));
                aeroport.setLatitude(Double.parseDouble(line.substring(index5 + 1,index6)));
                aeroport.setLongitude(Double.parseDouble(line.substring(index6 + 1,index7)));
                aeroport.setTimezone(Integer.parseInt(line.substring(index7 + 1,index8)));
                AeroportBDD.insertAeroport(aeroport);
            }
        } catch (IOException e) {
            e.getMessage();
        }

        InputStream fic2 = context.getResources().openRawResource(R.raw.vols);
        InputStreamReader streamReader2 = new InputStreamReader(fic2);
        BufferedReader bufferedReader2 = new BufferedReader(streamReader2);
        String line2;
        StringBuilder text2 = new StringBuilder();

        try {
            while (( line2 = bufferedReader2.readLine()) != null) {
                Vol vol = new Vol();
                text2.append(line2);
                text2.append('\n');

                int index1 = line2.indexOf(",");
                int index2 = line2.indexOf(",",index1+1);
                int index3 = line2.indexOf(",",index2+1);
                int index4 = line2.indexOf(",",index3+1);
                int index5 = line2.indexOf(",",index4+1);
                int index6 = line2.indexOf(",",index5+1);

                vol.setAeroportDepart(line2.substring(0,index1));
                vol.setAeroportArrivee(line2.substring(index1 + 1,index2));
                vol.setHeureDepart(line2.substring(index2 + 1,index3));
                vol.setHeureArrivee(line2.substring(index3 + 1,index4));
                vol.setCompagnieID(Integer.parseInt(line2.substring(index4 + 1,index5)));
                vol.setClasseID(Integer.parseInt(line2.substring(index5 + 1,index6)));
                vol.setPrix(Double.parseDouble(line2.substring(index6 + 1)));
                VolBDD.insertVol(vol);
            }
        } catch (IOException e) {
            e.getMessage();
        }

    }
}
