package school.coda.ilay_luisa.bataillejavale.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class MenuController
{
//   @FXML
//    private HBox gameController;


        /// affichage de me chats
      ///  drawInitialStade();/// method oluştur!

        ///if (gameContenier)
///    }

        @FXML
        public void startGame(ActionEvent event) throws Exception {

            System.out.println("Nouvelle partie lancée !");

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/school/coda/ilay_luisa/bataillejavale/views/placement.fxml")
            );
            Parent placementFxml = loader.load();

            Node button = (Node) event.getSource();
            Stage stage = (Stage) button.getScene().getWindow();

            Scene scene = new Scene(placementFxml, 1500, 850);

            // 4. On applique la nouvelle scène et on l'affiche
            stage.setScene(scene);
            stage.show();
        }

    }
