package school.coda.ilay_luisa.bataillejavale.rules;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import school.coda.ilay_luisa.bataillejavale.model.Board;
import school.coda.ilay_luisa.bataillejavale.model.Cat;
import school.coda.ilay_luisa.bataillejavale.model.CatType;
import school.coda.ilay_luisa.bataillejavale.model.Player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    @Test
    @DisplayName("isGameOver doit retourner true quand tous les chats sont endormis")
    void shouldReturnTrueWhenAllCatsAreAsleep() {
        Player p1 = new Player("Ilay");
        Player p2 = new Player("Luisa");

        p1.getBoard().placeCat(CatType.MISTACHE, 0, 0, true);
        p1.getBoard().attack(0, 0);
        p1.getBoard().attack(0,1);
        p1.getBoard().attack(0,2);

        TurnManager manager = new TurnManager(p1, p2);
        boolean isGameOver = manager.isGameOver(p1);

        assertThat(isGameOver).isTrue();
    }

    @ParameterizedTest(name = "La partie continue tant qu'un chat est vivant")
    @CsvSource({
            "TOM",
            "MISTACHE",
            "PUFI"
    })
    void shouldNotBeGameOverWhenACatIsAlive(CatType catType) {
        Player p1 = new Player("Ilay");
        Player p2 = new Player("Luisa");
        p1.getBoard().placeCat(catType, 0, 0, true);
        TurnManager manager = new TurnManager(p1, p2);

        boolean isGameOver = manager.isGameOver(p1);

        assertThat(isGameOver).isFalse();
    }

    @Test
    @DisplayName("isGameOver doit fonctionner avec un mock de Player")
    void shouldCheckGameOverWithMockPlayer() {
        Player mockPlayer = mock(Player.class);
        Board mockBoard = mock(Board.class);

        when(mockPlayer.getBoard()).thenReturn(mockBoard);
        when(mockBoard.getOceanGrid()).thenReturn(new Cat[10][10]);
        TurnManager manager = new TurnManager(mockPlayer, mock(Player.class));

        boolean isGameOver = manager.isGameOver(mockPlayer);

        assertThat(isGameOver).isTrue();
    }

}