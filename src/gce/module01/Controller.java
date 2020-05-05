package gce.module01;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.text.CharacterIterator;
import java.text.DecimalFormat;
import java.text.StringCharacterIterator;
import java.util.Objects;
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
        // For ever iteration, this will extract the number of items
        // (files or directories) in the current node
        long numberOfFiles = Objects.requireNonNull(directory.listFiles()).length;

        String folderInfo = directory.getName() + " (" + numberOfFiles + " items)";

        TreeItem<String> root = new TreeItem<>(folderInfo);

        File[] listFiles = directory.listFiles();

        for (int i = 0; i < listFiles.length; i++) {
            File f = listFiles[i];

            if (f.isDirectory()) {
                root.getChildren().add(getNodesForDirectory(f));
            } else {
                String fileInfo = f.getName() + " (" + prettyFileSize(f.length()) + ")";
                root.getChildren().add(new TreeItem<>(fileInfo));
            }
        }

        return root;
    }

    /**
     * Prettifies the display of file sizes
     *
     * @param size The file size
     * @return The formatted file size
     */
    public static String prettyFileSize(long size) {
        if (size <= 0) {
            return "0";
        }

        final String[] units = new String[]{"B", "KiB", "MiB", "GiB", "TiB"};

        int sizegroups = (int) (Math.log10(size) / Math.log10(1024));

        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, sizegroups)) + " " + units[sizegroups];
    }
}
