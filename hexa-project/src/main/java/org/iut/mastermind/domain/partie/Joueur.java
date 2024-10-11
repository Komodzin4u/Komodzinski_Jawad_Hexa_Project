package org.iut.mastermind.domain.partie;

public class Joueur {

    private final String nom;

    // constructeur
    public Joueur(String nom) {
        this.nom = nom;
    }

    // getter nom joueur
    public String getNom() {
        return nom;
    }

    // equals
    @Override
    public boolean equals(Object o) {
       Joueur j=(Joueur) o;
       return j.getNom().equals(this.nom);
    }

    @Override
    public int hashCode() {
        return nom.hashCode();
    }
}
