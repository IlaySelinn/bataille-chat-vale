package school.coda.ilay_luisa.bataillejavale.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import school.coda.ilay_luisa.bataillejavale.model.Board;
import school.coda.ilay_luisa.bataillejavale.model.CatType;
import school.coda.ilay_luisa.bataillejavale.model.Game;
import school.coda.ilay_luisa.bataillejavale.view.BoardViev;

public class PlacementController {

    @FXML
    private GridPane grid;

    @FXML
    private BoardViev boardView;

    @FXML
    private ToggleButton toggleOrientationButton;

    private Game game;
    private Board playerBoard;

    // Phase placement
    private boolean isHorizontal = true;
    private int catIndex = 0; // index du chat à placer
    private final CatType[] catOrder = {CatType.TOM, CatType.PUFI, CatType.MISTACHE, CatType.UKULELE, CatType.GÜMÜŞ};

    @FXML
    public void initialize() {
        game = new Game("Player");
        playerBoard = game.getPlayer().getBoard();

        int gridSize = 10;

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {

                Button cell = new Button();
                cell.setPrefSize(40, 40);

                int r = row;
                int c = col;

                cell.setOnAction(event -> placeCat(r, c, cell));

                grid.add(cell, col, row);
            }
        }

        toggleOrientationButton.setOnAction(event -> {
            isHorizontal = !isHorizontal;
            toggleOrientationButton.setText(isHorizontal ? "Orientation: H" : "Orientation: V");
        });
    }

    private void placeCat(int row, int col, Button cell) {
        if (catIndex >= catOrder.length) {
            System.out.println("Tous les chats ont été placés !");
            return;
        }

        CatType currentCat = catOrder[catIndex];

        // Vérifier si le placement est possible
        if (!canPlaceCat(currentCat, row, col, isHorizontal)) {
            System.out.println("Placement impossible ici !");
            return;
        }

        // Placer le chat
        playerBoard.placeCat(currentCat, row, col, isHorizontal);
        markCatOnGrid(currentCat, row, col, isHorizontal);

        catIndex++;

        if (catIndex >= catOrder.length) {
            System.out.println("Tous les chats placés ! Cliquez sur 'Commencer la bataille'");
        } else {
            System.out.println("Placez : " + catOrder[catIndex].name());
        }
    }

    private boolean canPlaceCat(CatType cat, int row, int col, boolean horizontal) {
        int size = cat.getSize();

        if (horizontal) {
            if (col + size > 10) return false;
            for (int i = 0; i < size; i++) {
                if (playerBoard.getOceanGrid()[row][col + i] != null) return false;
            }
        } else {
            if (row + size > 10) return false;
            for (int i = 0; i < size; i++) {
                if (playerBoard.getOceanGrid()[row + i][col] != null) return false;
            }
        }
        return true;
    }

    private void markCatOnGrid(CatType cat, int row, int col, boolean horizontal) {
        Color color = Color.LIGHTGRAY;
        int size = cat.getSize();

        for (int i = 0; i < size; i++) {
            int r = horizontal ? row : row + i;
            int c = horizontal ? col + i : col;
            boardView.markHit(r, c, color);
        }
    }

    @FXML
    private void startBattle() {
        if (catIndex < catOrder.length) {
            System.out.println("Placez tous les chats avant de commencer la bataille !");
            return;
        }

        System.out.println("La bataille commence !");
        // TODO : basculer vers la scène de bataille
    }
}