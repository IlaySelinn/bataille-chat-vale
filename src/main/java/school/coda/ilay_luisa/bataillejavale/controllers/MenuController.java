package school.coda.ilay_luisa.bataillejavale.controllers;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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


public class MenuController
{
   @FXML
    private HBox gameController;


        /// affichage de me chats
      ///  drawInitialStade();/// method oluştur!

        ///if (gameContenier)
///    }

    @FXML
    public void startGame(ActionEvent event) throws Exception {

        System.out.println("Nouvelle partie lancée !");

        Node button = (Node) event.getSource();

        Scene scene = button.getScene();

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/school/coda/ilay_luisa/bataillejavale/views/placement.fxml")
        );

        Parent placementFxml = loader.load();

        scene.setRoot(placementFxml);


    }
}