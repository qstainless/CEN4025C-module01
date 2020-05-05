package gce.module01;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("module01.fxml"));
            primaryStage.setTitle("Detailed Folder View");
            primaryStage.setScene(new Scene(root));
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("It seems that JavaFX is not properly installed in your system.\n\n" +
                    "Program cannot continue. Exiting.");
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
