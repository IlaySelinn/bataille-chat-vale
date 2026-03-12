package school.coda.ilay_luisa.bataillejavale.controllers;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import school.coda.ilay_luisa.bataillejavale.model.Board;
import school.coda.ilay_luisa.bataillejavale.fight.RandomFight;
import school.coda.ilay_luisa.bataillejavale.rules.AttackResult;
import school.coda.ilay_luisa.bataillejavale.view.BoardViev;

import java.net.URL;
import java.util.ResourceBundle;


public class MenuController implements Initializable
{
   @FXML
    private HBox gameController;

    private Board playerLogic;
    private Board enemyLogic;
    private BoardViev playerViev;
    private BoardViev enemyViev;

    private RandomFight randomFight;
    private boolean isPlayerTurn = true;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        playerLogic = new Board();
        enemyLogic = new Board();
        playerViev = new BoardViev();
        enemyViev = new BoardViev();
        randomFight = new RandomFight();

        /// Mon grid
        ///drawInitialStade();/// method oluştur
    }

    @FXML
    public void startGame(ActionEvent event) throws Exception {

        System.out.println("Nouvelle partie lancée !");

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/school/coda/ilay_luisa/bataillejavale/views/placement.fxml")
        );

        Scene scene = new Scene(loader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    public void handleBoardViewClick(MouseEvent mouseEvent) {
        System.out.println("clique sur plateau");
    }
}