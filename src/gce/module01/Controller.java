package gce.module01;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Comparator;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    /**
     * The application stage
     */
    @FXML
    private AnchorPane anchorPane;

    /**
     * Placeholder to display application messages
     */
    @FXML
    private Label messageLabel;

    /**
     * The TreeView where the directory hierarchy will be displayed
     */
    @FXML
    private TreeView<String> treeView;

    /**
     * Called by the {@code FXMLLoader} to initialize the controller after
     * its root element has been completely processed.
     *
     * @param location  The location used to resolve relative paths for the
     *                  root object, or <tt>null</tt> if the location is
     *                  not known.
     * @param resources The resources used to localize the root object, or
     *                  <tt>null</tt> if the root object was not localized.
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * When the Browse button is clicked, a dialog opens for the user to
     * select the directory/folder to parse. Once the directory/folder is
     * selected then the program recursively iterates through the selected
     * directory/folder to display its contents.
     */
    @FXML
    public void handleBrowseButtonAction() {
        final DirectoryChooser directoryChooser = new DirectoryChooser();

        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        // Ask the user to select the root directory
        File selectedRoot = directoryChooser.showDialog(anchorPane.getScene().getWindow());

        if (selectedRoot == null) {
            // Cancelled directory selection
            messageLabel.setText("No directory selected.");
            treeView.setRoot(null);
        } else if (!selectedRoot.isDirectory()) {
            // Selection is not a valid directory
            messageLabel.setText("Could not open directory.");
            treeView.setRoot(null);
        } else {
            // Display the selected directory's hierarchical structure
            messageLabel.setText(null);
            treeView.setRoot(getNodesForDirectory(selectedRoot));
        }
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

        for (int i = 0; i < Objects.requireNonNull(listFiles).length; i++) {
            File f = listFiles[i];

            if (f.isDirectory()) {
                root.getChildren().add(getNodesForDirectory(f));
            } else {
                // Add the node with the corresponding file size
                String fileInfo = f.getName() + " (" + prettyFileSize(f.length()) + ")";
                root.getChildren().add(new TreeItem<>(fileInfo));
            }
        }

        // Sort the nodes in descending alphabetical order.
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
            return "0 B";
        }

        final String[] units = new String[]{"B", "KiB", "MiB", "GiB", "TiB"};

        int sizegroup = (int) (Math.log10(size) / Math.log10(1024));

        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, sizegroup)) + " " + units[sizegroup];
    }
}
