package com.example.merguez.easytrip.bdd.table_vols;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by merguez on 13/09/2016.
 */
public class Vol implements Parcelable{
    private int id;
    private String aeroportDepart;
    private String aeroportArrivee;
    private String heureDepart;
    private String heureArrivee;
    private int compagnieID;
    private int classeID;
    private double prix;

    public Vol(){}

    public Vol(int id, String aeroportDepart, String aeroportArrivee, String heureDepart, String heureArrivee, int compagnieID, int classeID, double prix) {
        this.id = id;
        this.aeroportDepart = aeroportDepart;
        this.aeroportArrivee = aeroportArrivee;
        this.heureDepart = heureDepart;
        this.heureArrivee = heureArrivee;
        this.compagnieID = compagnieID;
        this.classeID = classeID;
        this.prix = prix;
    }

    public int getId() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getAeroportDepart() {
        return aeroportDepart;
    }

    public void setAeroportDepart(String aeroportDepart) {
        this.aeroportDepart = aeroportDepart;
    }

    public String getAeroportArrivee() {
        return aeroportArrivee;
    }

    public void setAeroportArrivee(String aeroportArrivee) {
        this.aeroportArrivee = aeroportArrivee;
    }

    public String getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(String heureDepart) {
        this.heureDepart = heureDepart;
    }

    public String getHeureArrivee() {
        return heureArrivee;
    }

    public void setHeureArrivee(String heureArrivee) {
        this.heureArrivee = heureArrivee;
    }

    public int getCompagnieID() {
        return compagnieID;
    }

    public void setCompagnieID(int compagnieID) {
        this.compagnieID = compagnieID;
    }

    public int getClasseID() {
        return classeID;
    }

    public void setClasseID(int classeID) {
        this.classeID = classeID;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "Vol{" +
                "id=" + id + "aeroportDepart='" + aeroportDepart + '\'' + ", aeroportArrivee='" + aeroportArrivee + '\'' +
                ", heureDepart='" + heureDepart + '\'' + ", heureArrivee='" + heureArrivee + '\'' +
                ", compagnieID=" + compagnieID + ", classeID=" + classeID + ", prix=" + prix +
                '}';
    }

    public Vol(Parcel in)
    {
        this.getFromParcel(in);
    }

    @SuppressWarnings("rawtypes")
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
    {
        public Vol createFromParcel(Parcel in)
        {
            return new Vol(in);
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

    //On ecrit dans le parcel les données de notre objet
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(this.getId());
        dest.writeString(this.getAeroportDepart());
        dest.writeString(this.getAeroportArrivee());
        dest.writeString(this.getHeureDepart());
        dest.writeString(this.getHeureArrivee());
        dest.writeInt(this.getCompagnieID());
        dest.writeInt(this.getClasseID());
        dest.writeDouble(this.getPrix());
    }

    //On va ici hydrater notre objet à partir du Parcel
    public void getFromParcel(Parcel in)
    {
        this.setID(in.readInt());
        this.setAeroportDepart(in.readString());
        this.setAeroportArrivee(in.readString());
        this.setHeureDepart(in.readString());
        this.setHeureArrivee(in.readString());
        this.setCompagnieID(in.readInt());
        this.setClasseID(in.readInt());
        this.setPrix(in.readDouble());
    }
}
