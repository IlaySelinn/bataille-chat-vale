package school.coda.ilay_luisa.bataillejavale.rules;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import school.coda.ilay_luisa.bataillejavale.model.Player;

import static org.assertj.core.api.Assertions.assertThat;

class TurnManagerTest {

    @Test
    @DisplayName("Au début de la partie, le compteur de tour doit être à 1")
    void shouldStartAtTurnOne() {
        // 1. Arrange : on prépare 2 joueurs et le TurnManager
        Player p1 = new Player("Ilay");
        Player p2 = new Player("Luisa");
        TurnManager manager = new TurnManager(p1, p2);

        // 2. Act : on récupère le numéro de tour
        int actualTurn = manager.getTurnNumber();

        // 3. Assert : on vérifie qu'il commence bien à 1
        assertThat(actualTurn).isEqualTo(1);
    }
}