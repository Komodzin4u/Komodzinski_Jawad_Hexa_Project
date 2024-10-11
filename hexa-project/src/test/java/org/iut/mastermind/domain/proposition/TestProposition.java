package org.iut.mastermind.domain.proposition;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.iut.mastermind.domain.proposition.Lettre.*;

@DisplayName("Test sur la proposition:")
public class TestProposition {

    @Test
    @DisplayName("une lettre est incorrecte")
    public void casLettreIncorrecte() {
        var rep = new Reponse("S");
        rep.compare("Z");
        assertResultat(rep, INCORRECTE);
    }

    @Test
    @DisplayName("une lettre est placée")
    public void casLettrePlacee() {
        var rep = new Reponse("S");
        rep.compare("S");
        assertResultat(rep, PLACEE);
    }

    @Test
    @DisplayName("une lettre est incorrecte, une non placée")
    public void casDeuxiemeLettreMalPlacee() {
        var rep = new Reponse("SO");
        rep.compare("ZS");
        assertResultat(rep,  INCORRECTE, NON_PLACEE);
    }

    @Test
    @DisplayName("une lettre est incorrecte, non placée, placée")
    public void casCombinaisons() {
        var rep = new Reponse("SOL");
        rep.compare("ZSL");
        assertResultat(rep,  INCORRECTE, NON_PLACEE, PLACEE);
    }

    @Test
    @DisplayName("toutes les lettres sont placées")
    void casToutesLettresPlacees() {
        var rep = new Reponse("SOLID");
        rep.compare("SOLID");
        assertThat(rep.lettresToutesPlacees()).isTrue();
    }

    @Test
    @DisplayName("la proposition n'est pas correcte")
    void casLettresIncorrectes() {
        var rep = new Reponse("SOLID");
        rep.compare("SOL*D");
        assertThat(rep.lettresToutesPlacees()).isFalse();
    }

    @Test
    @DisplayName("vérifie la taille du résultat")
    void casAccesLettres() {
        var rep = new Reponse("SOLID");
        rep.compare("SOL*D");
        assertThat(rep.lettresResultat()).hasSize(5);
    }


    private void assertResultat(Reponse reponse, Lettre... resultatAttendu) {
        for (int position = 0; position < resultatAttendu.length; position++) {
            Lettre attendue = resultatAttendu[position];
            assertThat(reponse.lettre(position)).isEqualTo(attendue);
        }
    }
}
