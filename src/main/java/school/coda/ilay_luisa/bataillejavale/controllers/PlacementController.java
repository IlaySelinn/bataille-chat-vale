package school.coda.ilay_luisa.bataillejavale.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import school.coda.ilay_luisa.bataillejavale.model.Board;
import school.coda.ilay_luisa.bataillejavale.model.CatType;
import school.coda.ilay_luisa.bataillejavale.model.Game;
import school.coda.ilay_luisa.bataillejavale.view.BoardViev;

public class PlacementController {

    @FXML
    private BoardViev boardView;

    @FXML
    private ToggleButton toggleOrientationButton;

    @FXML
    private ComboBox<Integer> lineCombo;

    @FXML
    private ComboBox<String> colCombo;

    @FXML
    private ComboBox<String> orientationCombo;

    @FXML
    private ComboBox<CatType> catTypeCombo;

    private Game game;
    private Board playerBoard;

    @FXML
    public void initialize() {
        // Initialiser le plateau et le joueur
        game = new Game("Player");
        playerBoard = game.getPlayer().getBoard();

        // Initialisation des menus déroulants
        lineCombo.getItems().addAll(1,2,3,4,5,6,7,8,9,10);
        colCombo.getItems().addAll("A","B","C","D","E","F","G","H","I","J");
        orientationCombo.getItems().addAll("H","V");
        catTypeCombo.getItems().addAll(CatType.TOM, CatType.PUFI, CatType.MISTACHE, CatType.UKULELE, CatType.GÜMÜŞ);

        // Valeurs par défaut
        lineCombo.getSelectionModel().selectFirst();
        colCombo.getSelectionModel().selectFirst();
        orientationCombo.getSelectionModel().selectFirst();
        catTypeCombo.getSelectionModel().selectFirst();

    }

    @FXML
    private void placeCatFromMenu() {
        Integer row = lineCombo.getValue();
        String colLetter = colCombo.getValue();
        String orientation = orientationCombo.getValue();
        CatType cat = catTypeCombo.getValue();

        if (row == null || colLetter == null || orientation == null || cat == null) {
            System.out.println("Veuillez sélectionner toutes les options !");
            return;
        }

        int r = row - 1;
        int c = colLetter.charAt(0) - 'A';
        boolean horizontal = orientation.equals("H");

        // Vérification si le chat peut être placé
        if (!canPlaceCat(cat, r, c, horizontal)) {
            System.out.println("Placement impossible ici !");
            return;
        }

        // Placer le chat
        playerBoard.placeCat(cat, r, c, horizontal);
        markCatOnGrid(cat, r, c, horizontal);
        System.out.println("Chat " + cat + " placé en " + colLetter + row + " (" + orientation + ")");
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
        System.out.println("La bataille commence !");
        // TODO : basculer vers la scène de bataille
    }

    public void handleBoardViewClick(MouseEvent mouseEvent) {
        System.out.println("Clique sur le plateau détecté !");
    }
}