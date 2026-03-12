package school.coda.ilay_luisa.bataillejavale.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import school.coda.ilay_luisa.bataillejavale.view.BoardViev;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    public BoardViev boardView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // ıcı on peut ınıtalıser des objest
        // this.truc = ...
    }

    public void startGame(ActionEvent actionEvent) {
        System.out.println("Nouvelle partie lancée !");
        // changement d'ecran ici
    }

    public void handleBoardViewClick(MouseEvent mouseEvent) {
        System.out.println("clıque sur plateau");
    }


}
