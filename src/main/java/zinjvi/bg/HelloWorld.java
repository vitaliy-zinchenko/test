package zinjvi.bg;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class HelloWorld extends Application {

    @Override
    public void start(final Stage primaryStage) throws IOException {
        Socket socket = new Socket("localhost", 1111);
        final PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
//        printWriter.append("qwe");
//        printWriter.append("qwe2");
//        printWriter.flush();
//        printWriter.close();







        Button btn = new Button();
        btn.setText("...");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("...");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(KeyCode.UP.equals(event.getCode())) {
                    printWriter.append("UP");
                    printWriter.flush();
                }


                System.out.println("e " + event.getCode());
            }
        });

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
