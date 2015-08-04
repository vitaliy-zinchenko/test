package zinjvi.bg.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private ObjectInputStream objectInputStream;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
    }

    @Override
    public void run() {
        try {
             handle();
        } catch (IOException e) {
            System.err.println("ERROR. Couldn't handle client.");
            e.printStackTrace();
        }
    }

    private void handle() throws IOException {
        while (true) {
            String line = null;
            try {
                line = (String) objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("line: " + line);
            if(line == null) {
                return;
            }
            System.out.println("arrived: " + line);
        }
    }

}
