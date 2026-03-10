package school.coda.ilay_luisa.bataillejavale.model;

import school.coda.ilay_luisa.bataillejavale.rules.AttackResult;

public class BoardTestMain {

    static void main() {
        Board board = new Board();
        System.out.println(board.attack(0, 0));

        System.out.println(board.attack(0, 9));

    }
}
