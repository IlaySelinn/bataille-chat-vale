package school.coda.ilay_luisa.bataillejavale.rules;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import school.coda.ilay_luisa.bataillejavale.model.CatType;
import school.coda.ilay_luisa.bataillejavale.model.Player;

import static org.assertj.core.api.Assertions.assertThat;

class TurnManagerTest {

    @Test
    @DisplayName("Au début de la partie, le compteur de tour doit être à 1")
    void shouldStartAtTurnOne() {
        Player p1 = new Player("Ilay");
        Player p2 = new Player("Luisa");
        TurnManager manager = new TurnManager(p1, p2);

        int actualTurn = manager.getTurnNumber();

        assertThat(actualTurn).isEqualTo(1);
    }

    @Test
    @DisplayName("nextTurn() doit incrémenter le tour et alterner l'adversaire")
    void shouldSwitchPlayerAndIncrementTurnOnNextTurn() {
        Player p1 = new Player("Ilay");
        Player p2 = new Player("Luisa");
        TurnManager manager = new TurnManager(p1, p2);

        manager.nextTurn();

        assertThat(manager.getTurnNumber()).isEqualTo(2);
        assertThat(manager.getOpponent()).isEqualTo(p1);
    }
    @Test
    @DisplayName("isGameOver doit returner false si le joueur à au moins un chat vivant")
    void shouldReturnFalseIfPlayerHasAliveCats() {
        Player p1 = new Player("Ilay");
        Player p2 = new Player("Luisa");

        p1.getBoard().placeCat(CatType.MISTACHE, 0, 0, true);
        TurnManager manager = new TurnManager(p1, p2);
        boolean isGameOver = manager.isGameOver(p1);

        assertThat(isGameOver).isFalse();


    }

}