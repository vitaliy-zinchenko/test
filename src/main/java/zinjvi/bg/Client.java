package zinjvi.bg;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Vitaliy_Zinchenko on 17.07.2015.
 */
public class Client {

    private Socket socket;
    private PrintWriter printWriter;
    private BlockingQueue<String> messages;

    public Client() {
        messages = new LinkedBlockingQueue<>();
    }

    public void connect() throws IOException {
        socket = new Socket("localhost", 1111);
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        Thread messageSender = new Thread(){
            @Override
            public void run() {
                System.out.println("Sender initialized");
                while(true) {
                    try {
                        String message = messages.take();
                        System.out.println("will send: " + message);
                        send(message);
                    } catch (Exception e) {
                        System.out.println("Unexpected error!");
                    }
                }
            }
        };
        messageSender.setDaemon(true);
        messageSender.start();

        initInput();
    }

    private void initInput() throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String line = scanner.nextLine();
            if(line.equals("stop")) {
                System.out.println("Stop client");
                return;
            }
            try {
                messages.put(line);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String message) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(message);
        objectOutputStream.flush();
        objectOutputStream.close();
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client();
        client.connect();
    }

}
