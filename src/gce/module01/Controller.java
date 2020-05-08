package gce.module01;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Comparator;
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

    /**
     * When the Load Directory button is clicked, a dialog opens for the
     * user to select the directory/folder to parse. Once the directory/
     * folder is selected then the program recursively iterates through
     * the selected directory/folder to display its contents.
     */
    @FXML
    public void handleSelectDirectoryButtonAction() {
        DirectoryChooser dc = new DirectoryChooser();

        dc.setInitialDirectory(new File(System.getProperty("user.home")));

        // Ask the user to select the root directory
        File selectedRoot = dc.showDialog(messageLabel.getScene().getWindow());

        // Display the TreeView in the GUI
        treeView.setRoot(getNodesForDirectory(selectedRoot));
    }

    /**
     * Recursive parsing of directory nodes to add subdirectories and files
     * to the TreeView.
     *
     * @param directory The current directory being parsed
     * @return The TreeItem
     */
    public TreeItem<String> getNodesForDirectory(File directory) {
        // For every iteration, this will extract the number of items
        // (files and directories) in the current node
        long numberOfFiles = Objects.requireNonNull(directory.listFiles()).length;

        // The directory/folder name will include the number of files it contains
        String folderInfo = directory.getName() + " (" + numberOfFiles + " items)";
        TreeItem<String> root = new TreeItem<>(folderInfo);

        File[] listFiles = directory.listFiles();

        for (File f : listFiles) {
            if (f.isDirectory()) {
                root.getChildren().add(getNodesForDirectory(f));
            } else {
                // Add the node with the corresponding file size
                String fileInfo = f.getName() + " (" + prettyFileSize(f.length()) + ")";
                root.getChildren().add(new TreeItem<>(fileInfo));
            }
        }

        root.getChildren().sort(Comparator.comparing(TreeItem::getValue));

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
