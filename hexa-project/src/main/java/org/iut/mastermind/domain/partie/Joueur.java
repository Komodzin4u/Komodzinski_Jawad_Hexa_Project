package org.iut.mastermind.domain.partie;

public record Joueur(String nom) {

    // equals
    @Override
    public boolean equals(Object o) {
        Joueur j = (Joueur) o;
        return j.nom().equals(this.nom);
    }

    // getter nom joueur
    public String getNom() {
        return nom;
    }

}
