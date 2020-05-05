package gce.module01;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Label messageLabel;

    @FXML
    private TreeView<String> treeView;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void handleLoadFolderButtonAction() {
        DirectoryChooser dc = new DirectoryChooser();

        dc.setInitialDirectory(new File(System.getProperty("user.home")));

        File selected = dc.showDialog(messageLabel.getScene().getWindow());

        if (selected == null || !selected.isDirectory()) {
            messageLabel.setText("Could not open directory.");
        } else {
            treeView.setRoot(getNodesForDirectory(selected));
        }
    }

    public TreeItem<String> getNodesForDirectory(File directory) {
        TreeItem<String> root = new TreeItem<>(directory.getName());

        File[] listFiles = directory.listFiles();

        for (int i = 0; i < listFiles.length; i++) {
            File f = listFiles[i];

            if (f.isDirectory()) {
                root.getChildren().add(getNodesForDirectory(f));
            } else {
                root.getChildren().add(new TreeItem<>(f.getName()));
            }
        }

        return root;
    }
}
