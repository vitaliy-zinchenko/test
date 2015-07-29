package zinjvi.bg;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
//            try (
//
//            ) {
                 handle(clientSocket.getInputStream(), clientSocket.getOutputStream());
//            }
        } catch (IOException e) {
            System.err.println("ERROR. Couldn't handle client.");
            e.printStackTrace();
        }
    }

    private void handle(InputStream in, OutputStream out) throws IOException {
        while (true) {
            ObjectInputStream objectInputStream = new ObjectInputStream(in);
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
