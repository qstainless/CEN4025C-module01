package gce.module01;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This program reads the file system beneath a specific folder/directory and
 * stores it in a tree data structure. It then outputs the directory/folder
 * hierarchy in a GUI showing the number of files in each directory/folder and
 * the size of each file contained therein.
 * <p>
 * Course: CEN 4025C-33718 Software Development II
 * Instructor: Dr. Dhrgam AL Kafaf
 *
 * @author Guillermo Castaneda Echegaray
 * @version 1.0
 * @since 2020-05-05
 */
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
