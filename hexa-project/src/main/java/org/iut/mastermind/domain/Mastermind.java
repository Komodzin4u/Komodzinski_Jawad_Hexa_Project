package org.iut.mastermind.domain;

import org.iut.mastermind.domain.partie.Joueur;
import org.iut.mastermind.domain.partie.Partie;
import org.iut.mastermind.domain.partie.PartieRepository;
import org.iut.mastermind.domain.partie.ResultatPartie;
import org.iut.mastermind.domain.proposition.Reponse;
import org.iut.mastermind.domain.tirage.MotsRepository;
import org.iut.mastermind.domain.tirage.ServiceNombreAleatoire;
import org.iut.mastermind.domain.tirage.ServiceTirageMot;
import java.util.Optional;

public class Mastermind {
    private final PartieRepository partieRepository;
    private final ServiceTirageMot serviceTirageMot;

    public Mastermind(PartieRepository pr, MotsRepository mr, ServiceNombreAleatoire na) {
        this.partieRepository = pr;
        this.serviceTirageMot = new ServiceTirageMot(mr, na);
    }

    // on récupère éventuellement la partie enregistrée pour le joueur
    // si il y a une partie en cours, on renvoie false (pas de nouvelle partie)
    // sinon on utilise le service de tirage aléatoire pour obtenir un mot
    // et on initialise une nouvelle partie et on la stocke
    public boolean nouvellePartie(Joueur joueur) {
        Optional<Partie> partieEnregistre=partieRepository.getPartieEnregistree(joueur);
        if(isJeuEnCours(partieEnregistre)){
            return false;
        }
        partieRepository.create(Partie.create(joueur, serviceTirageMot.tirageMotAleatoire()));
        return true;
    }

    // on récupère éventuellement la partie enregistrée pour le joueur
    // si la partie n'est pas une partie en cours, on renvoie une erreur
    // sinon on retourne le resultat du mot proposé
    public ResultatPartie evaluation(Joueur joueur, String motPropose) {
        Optional<Partie> partieEnregistre=partieRepository.getPartieEnregistree(joueur);
        if(isJeuEnCours(partieEnregistre)){
            return calculeResultat(partieEnregistre.get(), motPropose);
        }
        return ResultatPartie.ERROR;
    }

    // on évalue le résultat du mot proposé pour le tour de jeu
    // on met à jour la bd pour la partie
    // on retourne le résulat de la partie
    private ResultatPartie calculeResultat(Partie partie, String motPropose) {
        Reponse rep=partie.tourDeJeu(motPropose);
        partieRepository.update(partie);
        return ResultatPartie.create(rep, partie.isTerminee());
    }

    // si la partie en cours est vide, on renvoie false
    // sinon, on évalue si la partie est terminée
    private boolean isJeuEnCours(Optional<Partie> partieEnCours) {
        return partieEnCours.isPresent() && !partieEnCours.get().isTerminee();
    }
}
