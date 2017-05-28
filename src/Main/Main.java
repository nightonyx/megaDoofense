package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {
    //Статическое окно, которое можно переключать
    public static Stage doofenseStage = new Stage();

    @Override
    public void start(Stage unused) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/fxmlMenu.fxml"));
        doofenseStage.getIcons().add(new Image("file:src\\Doofense\\Icon.png"));
        doofenseStage.setResizable(false);
        doofenseStage.setTitle("Doofense");
        doofenseStage.setScene(new Scene(root));
        doofenseStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
