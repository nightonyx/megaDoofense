package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static Main.Main.doofenseStage;


public class Menu implements Initializable {
    //Кнопки:
    @FXML
    private Button buttonTapToPlay;
    @FXML
    private Button buttonExit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Кнопка"Начать игру":
        buttonTapToPlay.setOnMouseClicked(event -> play());
        buttonTapToPlay.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                play();
            }
            if (event.getCode() == KeyCode.ESCAPE){
                System.exit(1);
            }
        });

        //Кнопка "Выход":
        buttonExit.setOnMouseClicked(event -> System.exit(1));
    }

    //Метод "играть":
    private void play(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/fxmlMatchWindow.fxml"));
            doofenseStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
