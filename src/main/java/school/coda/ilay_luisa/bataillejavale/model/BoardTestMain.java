package school.coda.ilay_luisa.bataillejavale.model;

import school.coda.ilay_luisa.bataillejavale.rules.AttackResult;

public class BoardTestMain {

    static void main() {
        Board board = new Board();
        System.out.println(board.attack(0, 0));

        System.out.println(board.attack(0, 9));

        System.out.println(board.attack(5, 7));

        System.out.println(board.attack(4, 9));

        System.out.println(board.attack(3, 4));

        System.out.println(board.attack(2, 6));

        System.out.println(board.attack(1, 5));

        System.out.println(board.attack(8, 3));

        System.out.println(board.attack(4, 4));

        System.out.println(board.attack(2, 0));

        System.out.println(board.attack(0,1));
        System.out.println(board.attack(0,2));
        System.out.println(board.attack(0,3));
        System.out.println(board.attack(0,4));


    }
}
