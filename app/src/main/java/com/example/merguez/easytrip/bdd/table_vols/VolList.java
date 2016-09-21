package com.example.merguez.easytrip.bdd.table_vols;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/**
 * Created by user1 on 20/09/2016.
 */
public class VolList extends ArrayList<Vol> implements Parcelable{
    public VolList()
    {
    }

    public VolList(Parcel in)
    {
        this.getFromParcel(in);
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public VolList createFromParcel(Parcel in)
        {
            return new VolList(in);
        }

        @Override
        public Object[] newArray(int size) {
            return null;
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        //Taille de la liste
        int size = this.size();
        dest.writeInt(size);
        for(int i=0; i < size; i++)
        {
            Vol vol = this.get(i); //On vient lire chaque objet vol
            dest.writeInt(vol.getId());
            dest.writeString(vol.getAeroportDepart());
            dest.writeString(vol.getAeroportArrivee());
            dest.writeString(vol.getHeureDepart());
            dest.writeString(vol.getHeureArrivee());
            dest.writeInt(vol.getCompagnieID());
            dest.writeInt(vol.getClasseID());
            dest.writeDouble(vol.getPrix());
        }
    }

    public void getFromParcel(Parcel in)
    {
        // On vide la liste avant tout remplissage
        this.clear();

        //Récupération du nombre d'objet
        int size = in.readInt();

        //On repeuple la liste avec de nouveau objet
        for(int i = 0; i < size; i++)
        {
            Vol vol = new Vol();
            vol.setID(in.readInt());
            vol.setAeroportDepart(in.readString());
            vol.setAeroportArrivee(in.readString());
            vol.setHeureDepart(in.readString());
            vol.setHeureArrivee(in.readString());
            vol.setCompagnieID(in.readInt());
            vol.setClasseID(in.readInt());
            vol.setPrix(in.readDouble());
            this.add(vol);
        }

    }

}
