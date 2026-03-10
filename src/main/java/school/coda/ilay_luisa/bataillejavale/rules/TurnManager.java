package school.coda.ilay_luisa.bataillejavale.rules;

import school.coda.ilay_luisa.bataillejavale.model.Player;

public class TurnManager {
    private Player currentPlayer;
    private Player opponent;

    private int turnNumber = 1;

    public TurnManager(Player player1, Player player2) {
        this.currentPlayer = player1;
        this.opponent = player2;
    }
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public Player getOpponent() {
        return opponent;
    }
    public int getTurnNumber() {
        return turnNumber;
    }
    public void nextTurn()  {
        Player temp = currentPlayer;
        currentPlayer = opponent;
        opponent = temp;

        turnNumber++;
    }
}
