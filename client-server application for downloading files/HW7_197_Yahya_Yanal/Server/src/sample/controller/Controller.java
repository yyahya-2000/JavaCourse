package sample.controller;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import sample.module.ConnectionHandler;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * Controller class represents the dashboard scene
 */
public class Controller {
    private static ServerSocket serverSocket = null;
    private static ConnectionHandler connectionHandler = null;
    /*
     * these variables represent the frontend elements
     * */
    @FXML
    private TextField directoryPath;

    @FXML
    private Label connectionAddress;

    @FXML
    private Label connectionPort;

    @FXML
    private TextArea textArea;

    @FXML
    private Button browseBtn;

    @FXML
    private Button publishBtn;

    /**
     * this method will work when user want to close the program,
     * so the connection will be closed
     */
    public static void shutDown() {
        try {
            if (serverSocket != null && connectionHandler != null) {
                serverSocket.close();
                connectionHandler.join();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        connectionAddress.setText("localhost");
        connectionPort.setText("5000");
    }

    /**
     * an action to choose a folder to share
     */
    @FXML
    void choiceDirectoryBtnAction() {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) directoryPath.getScene().getWindow();
        File file = directoryChooser.showDialog(stage);
        if (file != null) {
            directoryPath.setText(file.getAbsolutePath());
        }
    }

    /**
     * an action to publish the selected folder to clients
     */
    @FXML
    void publishBtnAction() {
        if (!directoryPath.getText().trim().isEmpty() && serverSocket == null) {
            publishBtn.setStyle("-fx-background-color: #088A08; -fx-background-radius: 15px;");
            publishBtn.setText("Connected");
            browseBtn.setVisible(false);
            try {
                serverSocket = new ServerSocket(5000, 10, InetAddress.getByName("127.0.0.1"));
                connectionHandler = new ConnectionHandler(serverSocket, directoryPath.getText(), textArea);
                connectionHandler.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (serverSocket != null)
            alert("Already connected", "The server already connected and ready to communicate with clients.");
        else
            alert("No Selected Folder", "Please select a local folder before!");
    }

    /**
     * alert server user
     */
    private void alert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
