package sample.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import sample.module.MyAlarm;
import sample.module.MyFile;

import java.io.*;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller class represents the dashboard scene
 */
public class Controller {
    private final List<MyFile> downloadedFiles = new ArrayList<>();
    private DataOutputStream outToServer = null;
    private Socket clientSocket = null;
    /*
     * these variables represent the frontend elements
     * */
    @FXML
    private TableView<MyFile> tableViewServer;

    @FXML
    private TableColumn<?, ?> fileNameServer;

    @FXML
    private TableColumn<?, ?> sizeFileServer;

    @FXML
    private TextField connectionAddress;
    @FXML
    private TextField connectionPort;
    @FXML
    private Button connectBtn;

    @FXML
    private TableView<MyFile> tableViewClient;

    @FXML
    private TableColumn<?, ?> fileNameClient;

    @FXML
    private TableColumn<?, ?> sizeFileClient;

    @FXML
    void initialize() {
        initializeColumns();
        tableViewServer.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableViewClient.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * initializing tables columns
     */
    private void initializeColumns() {
        fileNameClient.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        fileNameServer.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        sizeFileClient.setCellValueFactory(new PropertyValueFactory<>("fileSize"));
        sizeFileServer.setCellValueFactory(new PropertyValueFactory<>("fileSize"));
    }

    /**
     * an action to connect client to the server
     */
    @FXML
    void connectAction() {
        try {
            if (clientSocket == null && connect()) {
                connectBtn.setStyle("-fx-background-color: #088A08; -fx-background-radius: 15px;");
                connectBtn.setText("Connected");
                connectionAddress.setEditable(false);
                connectionPort.setEditable(false);
                outToServer.writeInt(0);
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                List<MyFile> files = (ArrayList<MyFile>) objectInputStream.readObject();
                disconnect();
                tableViewServer.setItems(FXCollections.observableArrayList(files));
                tableViewServer.refresh();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * an action to download a file from the server
     */
    @FXML
    void downloadAction() {
        if (tableViewServer.getSelectionModel().getSelectedIndex() > -1) {
            if (downloadEnsure(tableViewServer.getSelectionModel().getSelectedItem())) {
                File dir = chooseDirectory();
                try {
                    if (dir != null) {
                        connect();
                        MyFile fileToDownload = tableViewServer.getSelectionModel().getSelectedItem();
                        outToServer.writeInt(1);
                        outToServer.writeUTF(fileToDownload.getFileName());
                        OutputStream output = new FileOutputStream(dir.getAbsolutePath() + "//" + fileToDownload.getFileName());
                        DataInputStream clientData = new DataInputStream(clientSocket.getInputStream());
                        byte[] buffer = new byte[8192];
                        int read = 0;
                        while ((read = clientData.read(buffer)) != -1)
                            output.write(buffer, 0, read);
                        output.close();
                        clientData.close();
                        disconnect();
                        downloadedFiles.add(fileToDownload);
                        tableViewClient.setItems(FXCollections.observableArrayList(downloadedFiles));
                        tableViewClient.refresh();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else
            MyAlarm.notSelectedItemAlert();
    }

    /**
     * disconnecting from the server
     *
     * @throws IOException
     */
    private void disconnect() throws IOException {
        outToServer.close();
        clientSocket.close();
    }

    /**
     * connecting to the server
     *
     * @return True, if the connection process ends successfully, false, otherwise
     * @throws IOException
     */
    private boolean connect() throws IOException {
        try {
            InetAddress serverHost = InetAddress.getByName(connectionAddress.getText());
            clientSocket = new Socket(serverHost, Integer.parseInt(connectionPort.getText()));
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            return true;
        } catch (UnknownHostException ex) {
            clientSocket = null;
            MyAlarm.wrongAdress();
        } catch (ConnectException | NumberFormatException ex) {
            clientSocket = null;
            MyAlarm.wrongPort();
        }
        return false;
    }

    /**
     * ensures the client want to download selected file
     *
     * @param file the selected file
     */
    private boolean downloadEnsure(MyFile file) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Download");
        alert.setHeaderText("Download Confirmation");
        alert.setContentText("Are you sure you want to download this file?\nName: " + file.getFileName() + "\nSize: " + file.getFileSize() + "KB");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    /**
     * choosing directory where to save the downloaded file
     *
     * @return the directory where the downloaded file will be saved
     */
    private File chooseDirectory() {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) connectionAddress.getScene().getWindow();
        return directoryChooser.showDialog(stage);
    }

}
