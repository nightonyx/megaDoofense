package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static Main.Main.doofenseStage;


public class Menu implements Initializable {
    //Кнопки
    @FXML
    private Button buttonTapToPlay;
    @FXML
    private Button buttonExit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Кнопка"Начать игру"
        buttonTapToPlay.setOnMouseClicked((MouseEvent event) -> {
            System.out.println("Anton and Doofense cool!");
            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("../fxmlFiles/fxmlMatchWindow.fxml"));
                doofenseStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Кнопка "Выход"
        buttonExit.setOnMouseClicked(event -> System.exit(1));
    }
}
