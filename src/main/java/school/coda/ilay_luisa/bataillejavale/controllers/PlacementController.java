package school.coda.ilay_luisa.bataillejavale.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import school.coda.ilay_luisa.bataillejavale.model.Board;
import school.coda.ilay_luisa.bataillejavale.model.CatType;
import school.coda.ilay_luisa.bataillejavale.model.Game;
import school.coda.ilay_luisa.bataillejavale.view.BoardView;
import javafx.util.StringConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.image.ImageView;

import java.util.Random;

public class PlacementController {

    /// Créer l'objet de chats
    private ImageView tomView, pufiView, mistacheView, ukuleleView, gumusView;

    @FXML
    private BoardView boardView;

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

    @FXML
    private Button placeButton;

    @FXML
    private Button startButton;

    private Game game;
    private Board playerBoard;

    @FXML
    public void initialize() {
        game = new Game("Player");
        playerBoard = game.getPlayer().getBoard();

        lineCombo.getItems().addAll(1,2,3,4,5,6,7,8,9,10);
        colCombo.getItems().addAll("A","B","C","D","E","F","G","H","I","J");
        orientationCombo.getItems().addAll("HORIZONTAL","VERTICAL");
        catTypeCombo.getItems().addAll(CatType.TOM, CatType.PUFI, CatType.MISTACHE, CatType.UKULELE, CatType.GÜMÜŞ);

        catTypeCombo.setConverter(new StringConverter<CatType>() {
            @Override
            public String toString(CatType cat) {
                if (cat == null) return "";
                return cat.name() + " (" + cat.getSize() + " cases)";
            }
            @Override
            public CatType fromString(String string) {
                return null;
            }
        });

        lineCombo.getSelectionModel().selectFirst();
        colCombo.getSelectionModel().selectFirst();
        orientationCombo.getSelectionModel().selectFirst();
        catTypeCombo.getSelectionModel().selectFirst();

        startButton.setDisable(true);

        tomView = createCatImageView("/image/tom.png");
        pufiView = createCatImageView("/image/pufi.png");
        mistacheView = createCatImageView("/image/mistache.png");
        ukuleleView = createCatImageView("/image/ukulele.png");
        gumusView = createCatImageView("/image/gumus.png");

        startButton.setDisable(true);
    }

    //PLACEMENT VIA LE BOUTON
    @FXML
    private void placeCatFromMenu() {
        Integer row = lineCombo.getValue();
        String colLetter = colCombo.getValue();

        if (row == null || colLetter == null) return;

        int r = row - 1;
        int c = colLetter.charAt(0) - 'A';

        attemptToPlaceCat(r, c);
    }

    // PLACEMENT VIA LE CLIC DE SOURIS SUR LA GRILLE !
    @FXML
    public void handleBoardViewClick(MouseEvent event) {
        int r = boardView.getRow(event.getY());
        int c = boardView.getCol(event.getX());

        if (r >= 0 && r < 10 && c >= 0 && c < 10) {
            attemptToPlaceCat(r, c);
        }
    }

    //  LOGIQUE COMMUNE DE PLACEMENT
    private void attemptToPlaceCat(int row, int col) {
        CatType cat = catTypeCombo.getValue();
        String orientation = orientationCombo.getValue();

        if (cat == null) {
            System.out.println("Tous les chats sont déjà placés !");
            return;
        }

        boolean horizontal = "HORIZONTAL".equals(orientation);

        // On passe le plateau du joueur (playerBoard) à la vérification
        if (!canPlaceCat(playerBoard, cat, row, col, horizontal)) {
            System.out.println("Placement impossible ici !");
            return;
        }

        // On place et on dessine
        playerBoard.placeCat(cat, row, col, horizontal);
        markCatOnGrid(cat, row, col, horizontal);
        System.out.println("Chat " + cat + " placé avec succès !");

        // On gère le menu et les boutons
        catTypeCombo.getItems().remove(cat);

        if (!catTypeCombo.getItems().isEmpty()) {
            catTypeCombo.getSelectionModel().selectFirst();
        } else {
            System.out.println("Félicitations ! Toute votre flotte féline est déployée !");
            placeButton.setDisable(true);
            startButton.setDisable(false);
        }
    }

    // La méthode vérifie n'importe quel plateau (Joueur ou IA)
    private boolean canPlaceCat(Board board, CatType cat, int row, int col, boolean horizontal)
    {
        int size = cat.getSize();
        if (horizontal) {
            if (col + size > 10) return false;
            for (int i = 0; i < size; i++) {
                if (board.getOceanGrid()[row][col + i] != null) return false;
            }
        }
        else
        {
            if (row + size > 10) return false;
            for (int i = 0; i < size; i++) {
                if (board.getOceanGrid()[row + i][col] != null) return false;
            }
        }
        return true;
    }

    // La méthode qui affiche les photos de chats sur l'écran
    private void markCatOnGrid(CatType cat, int row, int col, boolean horizontal) {
        ImageView selectedView = switch (cat) {
            case TOM -> tomView;
            case PUFI -> pufiView;
            case MISTACHE -> mistacheView;
            case UKULELE -> ukuleleView;
            case GÜMÜŞ -> gumusView;
        };

        int size = cat.getSize();

        if (selectedView == null || selectedView.getImage() == null) {
            System.out.println("Erreur! " + cat + " Pas de photo pour le chat");

            // On dessine en gris si l'image n'est pas trouvée pour voir où on a posé !
            for (int i = 0; i < size; i++) {
                int r = horizontal ? row : row + i;
                int c = horizontal ? col + i : col;
                boardView.markHit(r, c, javafx.scene.paint.Color.LIGHTGRAY);
            }
            return;
        }

        Image catImage = selectedView.getImage();
        for (int i = 0; i < size; i++) {
            int r = horizontal ? row : row + i;
            int c = horizontal ? col + i : col;
            boardView.drawCatImage(r, c, catImage);
        }
    }

    // Méthode pour placer les chats de l'IA au hasard
    private void placeIACatsRandomly() {
        Board iaBoard = game.getIA().getBoard();
        Random random = new Random();

        for (CatType cat : CatType.values()) {
            boolean isPlaced = false;

            while (!isPlaced) {
                int randomRow = random.nextInt(10);
                int randomCol = random.nextInt(10);
                boolean randomHorizontal = random.nextBoolean();

                // On vérifie si ça rentre sur la grille de l'IA
                if (canPlaceCat(iaBoard, cat, randomRow, randomCol, randomHorizontal)) {
                    iaBoard.placeCat(cat, randomRow, randomCol, randomHorizontal);
                    isPlaced = true;
                }
            }
        }
        System.out.println("L'IA a placé tous ses chats secrètement !");
    }

    @FXML
    private void startBattle(ActionEvent event) {
        System.out.println("La bataille commence !");

        // ON PLACE LES CHATS DE L'ORDINATEUR ICI
        placeIACatsRandomly();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/school/coda/ilay_luisa/bataillejavale/views/battle.fxml"));
            Parent root = loader.load();

            BattleController battleController = loader.getController();
            battleController.initData(game);

            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(" Erreur lors du chargement de la vue de bataille.");
        }
    }

    private ImageView createCatImageView(String path) {
        java.net.URL url = getClass().getResource(path);
        if (url == null) {
            System.out.println("Pas de photo de chat: " + path);
            return null;
        }
        Image img = new Image(url.toString());
        return new ImageView(img);
    }
}