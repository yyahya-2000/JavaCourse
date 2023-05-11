package sample.module;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ClientServerConnection extends Thread {
    private final Socket connectedClient;
    private final Map<String, File> filesMap = new HashMap<>();
    private final List<MyFile> filesList = new ArrayList<>();

    ClientServerConnection(Socket socket, String dirPath) {
        connectedClient = socket;
        File[] filesArray = new File(dirPath).listFiles();
        for (File file : Objects.requireNonNull(filesArray)) {
            if (!file.isDirectory() && file.length() <= 137400000000L) {
                filesMap.put(file.getName(), file);
                filesList.add(new MyFile(file.getName(), file.length() / 1024.0));
            }
        }
    }

    public void run() {
        try {
            DataInputStream ois = new DataInputStream(connectedClient.getInputStream());
            int connType = ois.readInt();
            if (connType == 0)
                sendFileList();
            else
                sendFile(ois);
            connectedClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendFile(DataInputStream ois) throws IOException {
        String fileName = ois.readUTF();
        BufferedOutputStream bos = new BufferedOutputStream(connectedClient.getOutputStream());
        DataOutputStream outToClient = new DataOutputStream(bos);
        byte[] buffer;
        buffer = new byte[8192];
        File file = filesMap.get(fileName);
        DataInputStream dis = getDataInputStream(file);
        int read;
        while ((read = dis.read(buffer)) != -1) {
            outToClient.write(buffer, 0, read);
        }
        outToClient.flush();
        outToClient.close();
    }

    private void sendFileList() throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(
                connectedClient.getOutputStream());
        outputStream.writeObject(filesList);
        outputStream.flush();
        outputStream.close();
    }

    private DataInputStream getDataInputStream(File file) throws FileNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        return new DataInputStream(bis);
    }
}