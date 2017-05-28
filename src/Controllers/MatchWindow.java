package Controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import static Main.Main.doofenseStage;

public class MatchWindow implements Initializable {
    @FXML
    private Button btnKey;
    @FXML
    private Text timer;
    // Счетчик очков
    @FXML
    private Text countScore;
    //Контейнер, содержащий оружие
    @FXML
    private Pane paneControlWeapons;
    //Контейнер, содержащий здоровье
    @FXML
    private Pane paneControlHitPoints;
    //Контейнер, содержащий положение противника (контейнер назвали условно "Бункер")
    @FXML
    private Pane paneContorBunker;
    //Контейнеры предполагают положение противников
    private Pane paneBunkerLeft = new Pane();
    private Pane paneBunkerMiddle = new Pane();
    private Pane paneBunkerRight = new Pane();
    //Лист "бункеров"
    private List<Pane> listBunker = Arrays.asList(paneBunkerLeft, paneBunkerMiddle, paneBunkerRight);
    //Лист типов оружия
    private List<ImageView> listWeapons = Arrays.asList(
            new ImageView(new Image("file:src\\Doofense\\ButtonLaser.png")),
            new ImageView(new Image("file:src\\Doofense\\ButtonLaser.png")),
            new ImageView(new Image("file:src\\Doofense\\ButtonLaser.png"))
    );
    //Лист типов противника
    private List<ImageView> listEnemy = Arrays.asList(
            new ImageView(new Image("file:src\\Doofense\\Attacker-1.png")),
            new ImageView(new Image("file:src\\Doofense\\Attacker-2.png")),
            new ImageView(new Image("file:src\\Doofense\\Attacker-3.png"))
    );
    //Контейнеры для здоровья
    private List<ImageView> listHitPoints = Arrays.asList(
            new ImageView(new Image("file:src\\Doofense\\Defender.png")),
            new ImageView(new Image("file:src\\Doofense\\Defender.png")),
            new ImageView(new Image("file:src\\Doofense\\Defender.png")),
            new ImageView(new Image("file:src\\Doofense\\Defender.png")),
            new ImageView(new Image("file:src\\Doofense\\Defender.png"))
    );
    //Счетчик очков
    static int score = 0;
    //Счетчик здоровья
    private int hitPoints = 5;
    //Таймер
    private int countdown = 30;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Инициализация игры...
        initializeEnemy();
        initializePaneEnemy();
        showEnemy();
        initializeWeapon();
        showWeapons();
        initializeHitPoints();
        showHitPoints();

        //Таймер
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(1), ae -> {
                            countdown--;
                            timer.setText(String.valueOf(countdown));

                        }
                )
        );
        int time = 30;
        timeline.setCycleCount(time);
        timeline.setOnFinished(event -> ending());
        timeline.play();

        //Нажатие на оружие мышкой:
        paneControlWeapons.setOnMouseClicked(event -> shotMouse());

        //Нажатие на оружие клавишей:
        btnKey.setOnKeyPressed(event -> {
            try {
                int i = -1;
                if (event.getCode() == KeyCode.NUMPAD1 || event.getCode() == KeyCode.DIGIT1){
                    i = 0;
                }
                if (event.getCode() == KeyCode.NUMPAD2 || event.getCode() == KeyCode.DIGIT2){
                    i = 1;
                }
                if (event.getCode() == KeyCode.NUMPAD3 || event.getCode() == KeyCode.DIGIT3){
                    i = 2;
                }
                if (listEnemy.get(i).isDisable()){
                    score = score + 1;
                    countScore.setText(String.valueOf(score));
                } else {
                    loseHitPoint(hitPoints);
                    hitPoints--;
                    if (hitPoints == 0){
                        ending();
                    }
                }
                deleteEnemy();
                showEnemy();
            } catch (ArrayIndexOutOfBoundsException ignored){}


        });
    }

    //Убить противника
    private void deleteEnemy(){
        paneContorBunker.getChildren().remove(0);
        for (Pane pane: listBunker){
            if (!pane.getChildren().isEmpty()){
                pane.getChildren().remove(0);
            }
        }
        for (ImageView imageView: listEnemy){
            if (imageView.isDisable()){
                imageView.setDisable(false);
            }
        }
    }

    //Показать нового противника
    private void showEnemy(){
        try {
            int k = new Random().nextInt(3);
            int l = new Random().nextInt(3);
            listBunker.get(k).getChildren().add(listEnemy.get(l));
            paneContorBunker.getChildren().add(listBunker.get(k));
            listEnemy.get(l).setDisable(true);
        } catch (IllegalArgumentException ignored){

        }

    }

    //Инициализация "бункеров"
    private void initializePaneEnemy(){
        int i = 20;
        for (Pane pane: listBunker){
            pane.setLayoutX(i);
            pane.setLayoutY(70);
            pane.getChildren();
            i = i + 140;
        }
    }

    //Инициалиация противников
    private void initializeEnemy(){
        for (ImageView imageView: listEnemy){
            imageView.setFitHeight(70);
            imageView.setFitWidth(50);
        }
    }

    //Инициализация оружия
    private void initializeWeapon(){
        int i = 20;
        for (ImageView imageView: listWeapons){
            imageView.setFitHeight(70);
            imageView.setFitWidth(70);
            imageView.setLayoutX(i);
            imageView.setLayoutY(0);
            i = i + 140;
        }
    }

    //Показать оружия
    private void showWeapons(){
        paneControlWeapons.getChildren().addAll(listWeapons);
    }

    //Инициалиция здоровья
    private void initializeHitPoints(){
        int i = 20;
        for (ImageView imageView: listHitPoints){
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            imageView.setLayoutX(i);
            imageView.setLayoutY(0);
            i = i + 25;
        }
    }

    //Показать здоровье
    private void showHitPoints(){
        paneControlHitPoints.getChildren().addAll(listHitPoints);
    }

    //Потеря здоровья
    private void loseHitPoint(Integer number){
        paneControlHitPoints.getChildren().remove(number - 1);
    }

    //Огонь мышью
    private void shotMouse(){
        for (int i = 0; i < 3; i++){
            if (listWeapons.get(i).isHover()){
                System.out.println(i);
                if (listEnemy.get(i).isDisable()){
                    score = score + 1;
                    countScore.setText(String.valueOf(score));
                } else {
                    loseHitPoint(hitPoints);
                    hitPoints--;
                    if (hitPoints == 0){
                        ending();
                    }
                }
            }
        }
        deleteEnemy();
        showEnemy();
    }

    //Завершение:
    private void ending(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/fxmlTotal.fxml"));
            doofenseStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
