package school.coda.ilay_luisa.bataillejavale.model;

public class Player {
    private final String name;
    private final Board board;

    public Player(String name) {
        this.name = name;
        this.board = new Board();
    }

    public Board getBoard() {
        return board;
    }
    public String getName() {
        return name;
    }
}
