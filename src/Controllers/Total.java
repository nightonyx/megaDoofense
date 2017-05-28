package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Controllers.MatchWindow.score;
import static Main.Main.doofenseStage;


public class Total implements Initializable{
    @FXML
    private Text textPoints;
    @FXML
    private Button btnPlayAgain;
    @FXML
    private Button btnToMenu;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Вывод очков:
        textPoints.setText(String.valueOf(score));
        //Кнопка "Меню"
        btnToMenu.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE){
                toMenu();
            }
        });
        btnToMenu.setOnMouseClicked(event -> toMenu());

        //Кнопка: "Играть снова"
        btnPlayAgain.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER){
                playAgain();
            }
        });

        btnPlayAgain.setOnMouseClicked(event -> playAgain());

    }

    //В меню:
    private void toMenu(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/fxmlMenu.fxml"));
            doofenseStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Играть снова:
    private void playAgain(){
        score = 0;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/fxmlMatchWindow.fxml"));
            doofenseStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
