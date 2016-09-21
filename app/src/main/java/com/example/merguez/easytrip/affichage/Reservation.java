package com.example.merguez.easytrip.affichage;

import java.io.Serializable;

/**
 * Created by merguez on 19/09/2016.
 */
public class Reservation implements Serializable{

    private String aitaDepart;
    private String nomDepart;
    private String aitaArrivee;
    private String nomArrivee;
    private String classe;
    private int volAller;
    private int volRetour;
    private boolean allerRetour;
    private int nbAdultes;
    private int nbEnfants;
    private String dateAller;
    private String dateRetour;

    public Reservation(){}

    public Reservation(String aitaDepart, String nomDepart, String aitaArrivee, String nomArrivee, String classe, int volAller, int volRetour, boolean allerRetour, int nbAdultes, int nbEnfants, String dateAller, String dateRetour) {
        this.aitaDepart = aitaDepart;
        this.nomDepart = nomDepart;
        this.aitaArrivee = aitaArrivee;
        this.nomArrivee = nomArrivee;
        this.classe = classe;
        this.volAller = volAller;
        this.volRetour = volRetour;
        this.allerRetour = allerRetour;
        this.nbAdultes = nbAdultes;
        this.nbEnfants = nbEnfants;
        this.dateAller = dateAller;
        this.dateRetour = dateRetour;
    }

    public String getAitaDepart() {
        return aitaDepart;
    }

    public void setAitaDepart(String aitaDepart) {
        this.aitaDepart = aitaDepart;
    }

    public String getAitaArrivee() {
        return aitaArrivee;
    }

    public void setAitaArrivee(String aitaArrivee) {
        this.aitaArrivee = aitaArrivee;
    }

    public int getVolAller() {
        return volAller;
    }

    public void setVolAller(int volAller) {
        this.volAller = volAller;
    }

    public int getVolRetour() {
        return volRetour;
    }

    public void setVolRetour(int volRetour) {
        this.volRetour = volRetour;
    }

    public boolean isAllerRetour() {
        return allerRetour;
    }

    public void setAllerRetour(boolean allerRetour) {
        this.allerRetour = allerRetour;
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

    public String getDateAller() {
        return dateAller;
    }

    public void setDateAller(String dateAller) {
        this.dateAller = dateAller;
    }

    public String getDateRetour() {
        return dateRetour;
    }

    public void setDateRetour(String dateRetour) {
        this.dateRetour = dateRetour;
    }

    public String getNomDepart() {
        return nomDepart;
    }

    public void setNomDepart(String nomDepart) {
        this.nomDepart = nomDepart;
    }

    public String getNomArrivee() {
        return nomArrivee;
    }

    public void setNomArrivee(String nomArrivee) {
        this.nomArrivee = nomArrivee;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "aitaDepart='" + aitaDepart + '\'' +
                ", nomDepart='" + nomDepart + '\'' +
                ", aitaArrivee='" + aitaArrivee + '\'' +
                ", nomArrivee='" + nomArrivee + '\'' +
                ", classe='" + classe + '\'' +
                ", volAller=" + volAller +
                ", volRetour=" + volRetour +
                ", allerRetour=" + allerRetour +
                ", nbAdultes=" + nbAdultes +
                ", nbEnfants=" + nbEnfants +
                ", dateAller='" + dateAller + '\'' +
                ", dateRetour='" + dateRetour + '\'' +
                '}';
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public boolean estComplete() {
        if (allerRetour){
            return  ((aitaDepart == null) || (aitaArrivee == null) || (nbAdultes == 0)
                    || (dateAller == null) || (dateRetour == null));
        } else {
            return  ((aitaDepart == null) || (aitaArrivee == null)
                    || (nbAdultes == 0) || (dateAller == null));
        }
    }

}

