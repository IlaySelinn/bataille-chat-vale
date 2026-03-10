package school.coda.ilay_luisa.bataillejavale.model;

import school.coda.ilay_luisa.bataillejavale.fight.IAfight;
import school.coda.ilay_luisa.bataillejavale.rules.AttackResult;

public class Game {
    private final Player player;
    private final Player ia;

    private final IAfight iaAI = new IAfight();

    private int turn = 1;

    public Game(String playerName) {
        player = new Player(playerName);
        ia = new Player("IA");
    }
    public Player getPlayer() {
        return player;
    }
    public Player getIA() {
        return ia;
    }
    public int getTurn() {
        return turn;
    }
    public AttackResult playerAttack(int row, int col) {
        AttackResult result = ia.getBoard().attack(row, col);
        turn++;
        return result;
    }
    public AttackResult iaAttack(int row, int col) {
        AttackResult result = player.getBoard().attack(row, col);
        turn++;
        return result;
    }
    public AttackResult iaTurn() {
        CoordinatePoints shot = iaAI.chooseAttack();
        return player.getBoard().attack(
                shot.getRow(),
                shot.getCol()
        );
    }
}
