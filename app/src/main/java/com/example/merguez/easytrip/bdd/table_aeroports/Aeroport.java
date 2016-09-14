package com.example.merguez.easytrip.bdd.table_aeroports;

/**
 * Created by merguez on 13/09/2016.
 */
public class Aeroport {

    private int id;
    private String aita;
    private String nom;
    private String ville;
    private String pays;
    private double latitude;
    private double longitude;
    private int timezone;

    public Aeroport(){}

    public Aeroport(int id, String aita, String nom, String ville, String pays, double latitude, double longitude, int timezone) {
        this.id = id;
        this.aita = aita;
        this.nom = nom;
        this.ville = ville;
        this.pays = pays;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timezone = timezone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAita() {
        return aita;
    }

    public void setAita(String aita) {
        this.aita = aita;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    @Override
    public String toString() {
        return "Aeroport{" +
                "id=" + id + ", aita='" + aita + '\'' + ", nom='" + nom + '\'' + ", ville='" + ville + '\'' +
                ", pays='" + pays + '\'' + ", latitude=" + latitude + ", longitude=" + longitude + ", timezone=" + timezone +
                '}';
    }
}
