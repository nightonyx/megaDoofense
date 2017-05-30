package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static Controllers.MatchWindow.hitPoints;
import static Controllers.MatchWindow.score; //Импорт набранных очков - глобальная переменная
import static Main.Main.doofenseStage; //Импорт глобального окна


public class Total implements Initializable {
    @FXML
    private Text textPoints;
    @FXML
    private Button btnPlayAgain;
    @FXML
    private Button btnToMenu;
    @FXML
    private Pane paneScore;
    @FXML
    private Pane paneRecord;
    @FXML
    private Button btnRecordOk;
    @FXML
    private TextField textFieldRecord;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hitPoints = 5;
        //Пытаемся проверить, попали ли мы мы в топ 10?
        try {
            List<String> listRecordes = getFile(new File("src\\Profiles\\Record"));
            if (checkRecord(score, listRecordes)){
                paneScore.setVisible(false);
                paneRecord.setVisible(true);

                btnRecordOk.setOnMouseClicked(event -> {
                    try {
                        setFile(setRecord(textFieldRecord.getText(), score, listRecordes));
                        paneRecord.setVisible(false);
                        paneScore.setVisible(true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                //Не знаю, почему не работает Enter
            }
        } catch (Exception ignored) {}

        //Вывод очков:
        textPoints.setText(String.valueOf(score));

        //Кнопка "Меню"
        btnToMenu.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                toMenu();
            }
        });
        btnToMenu.setOnMouseClicked(event -> toMenu());

        //Кнопка: "Играть снова"
        btnPlayAgain.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                playAgain();
            }
            if (event.getCode() == KeyCode.ESCAPE) {
                toMenu();
            }
        });

        btnPlayAgain.setOnMouseClicked(event -> playAgain());
    }

    //В меню:
    private void toMenu() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/fxmlMenu.fxml"));
            doofenseStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Играть снова:
    private void playAgain() {
        score = 0;
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/fxmlMatchWindow.fxml"));
            doofenseStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Получить файл с рекордами, можно было бы и реализовать через HashMap, но думаю, что парсинг 10 строк не проигрывает по времени случайной итерации по HashMap.
    public List<String> getFile(File file) throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        return bufferedReader.lines().collect(Collectors.toList());
    }

    //Положить рекорд, нужна доработка, есть дублирование кода:
    public List<String> setRecord(String name, int score, List<String> inputList) throws IOException {
        int i;
        List<String> outList = new ArrayList<>();
        Pattern regex = Pattern.compile("(\\S+)(\\s)(\\d+)");
        for (i = 0; i < 10; i++) {
            Matcher matcher = regex.matcher(inputList.get(i));
            if (matcher.matches()) {
                if (i == 0) {
                    if (score < Integer.parseInt(matcher.group(3))) {
                        break;
                    }
                }
                if (i == 9) {
                    for (int j = 1; j < i + 1; j++) {
                        outList.add(inputList.get(j));
                    }
                    outList.add(9, name + " " + score);
                }
                if (score < Integer.parseInt(matcher.group(3)) && i > 0) {
                    for (int j = 1; j < i; j++) {
                        outList.add(inputList.get(j));
                    }
                    outList.add(i - 1, name + " " + score);
                    break;
                }
            }
        }
        for (int j = i; j < 10; j++) {
            outList.add(inputList.get(j));
        }
        return outList;
    }

    //Записать таблицу рекордов в файл:
    public void setFile(List<String> inputList) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("src\\Profiles\\Record")));
        for (String line : inputList) {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        }
        bufferedWriter.close();
    }

    //Проверить результат:
    public boolean checkRecord(int score, List<String> inputList) {
        Pattern regex = Pattern.compile("(\\w+)(\\s)(\\d+)");
        for (String line : inputList) {
            Matcher matcher = regex.matcher(line);
            if (matcher.matches()) {
                if (score > Integer.parseInt(matcher.group(3))) {
                    return true;
                }
            }
        }
        return false;
    }
}

