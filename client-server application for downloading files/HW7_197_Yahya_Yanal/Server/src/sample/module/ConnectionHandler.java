package sample.module;

import javafx.scene.control.TextArea;

import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler extends Thread {
    private final ServerSocket serverSocket;
    private final String dirPath;
    private final TextArea textArea;

    public ConnectionHandler(ServerSocket serverSocket, String dirPath, TextArea textArea) {
        this.serverSocket = serverSocket;
        this.dirPath = dirPath;
        this.textArea = textArea;
    }

    public void run() {
        int i = 1;
        try {
            while (true) {
                Socket connected = serverSocket.accept();
                textArea.setText(textArea.getText() + "\n" + "ClientServerConnection " + i++ + ": " + connected);
                new ClientServerConnection(connected, dirPath).start();
            }
        } catch (Exception ex) {
            System.out.println("got exception: " + ex);
        }
        System.out.println("ServerSocket closed.");
    }
}
