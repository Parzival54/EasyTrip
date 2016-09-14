package com.example.merguez.easytrip.bdd.table_classes;

/**
 * Created by merguez on 13/09/2016.
 */
public class Classe {
    private int id;
    private String classe;

    public Classe(){}

    public Classe(int id, String classe) {
        this.id = id;
        this.classe = classe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    @Override
    public String toString() {
        return "Classe{" +
                "id=" + id + ", classe='" + classe + '\'' +
                '}';
    }
}
