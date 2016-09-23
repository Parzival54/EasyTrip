package com.example.merguez.easytrip.bdd.table_enregistrements;

/**
 * Created by grap on 23/09/2016.
 */
public class Enregistrement {
    private int id;
    private int userID;
    private int volAllerID;
    private int volRetourID;
    private int nbAdultes;
    private int nbEnfants;
    private double prixTotal;
    private String dateCreation;

    public Enregistrement() {
    }

    public Enregistrement(int id, int userID, int volAllerID, int volRetourID, int nbAdultes, int nbEnfants, double prixTotal,String dateCreation) {
        this.id = id;
        this.userID = userID;
        this.volAllerID = volAllerID;
        this.volRetourID = volRetourID;
        this.nbAdultes = nbAdultes;
        this.nbEnfants = nbEnfants;
        this.prixTotal = prixTotal;
        this.dateCreation = dateCreation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getVolAllerID() {
        return volAllerID;
    }

    public void setVolAllerID(int volAllerID) {
        this.volAllerID = volAllerID;
    }

    public int getVolRetourID() {
        return volRetourID;
    }

    public void setVolRetourID(int volRetourID) {
        this.volRetourID = volRetourID;
    }

    public int getNbAdultes() {
        return nbAdultes;
    }

    public void setNbAdultes(int nbAdultes) {
        this.nbAdultes = nbAdultes;
    }

    public int getNbEnfants() {
        return nbEnfants;
    }

    public void setNbEnfants(int nbEnfants) {
        this.nbEnfants = nbEnfants;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    @Override
    public String toString() {
        return "Enregistrement{" +
                "id=" + id +
                ", userID=" + userID +
                ", volAllerID=" + volAllerID +
                ", volRetourID=" + volRetourID +
                ", nbAdultes=" + nbAdultes +
                ", nbEnfants=" + nbEnfants +
                ", prixTotal=" + prixTotal +
                ", dateCreation='" + dateCreation + '\'' +
                '}';
    }
}
