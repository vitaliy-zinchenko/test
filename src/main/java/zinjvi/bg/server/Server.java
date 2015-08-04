package zinjvi.bg.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Vitaliy_Zinchenko on 17.07.2015.
 */
public class Server {

    public static final int PORT = 1111;
    private ServerSocket serverSocket;
    private ExecutorService executorService;

    public Server() throws IOException {
        executorService = Executors.newCachedThreadPool();
        serverSocket = new ServerSocket(PORT);
    }

    public void run() {
        System.out.println("Server started");
        while(true) {
            try {
                Socket clientSocket = serverSocket.accept();
                executorService.execute(new ClientHandler(clientSocket));
            } catch (IOException e) {
                System.out.println("Error while accepting client socket");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Server().run();
    }

}
