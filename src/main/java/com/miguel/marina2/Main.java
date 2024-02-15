package com.miguel.marina2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();


        LoginController loginController = loader.getController();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Marina Software");
        stage.show();



    }

    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            dbManager.close();
        }));

       /* Admin admin1 = new Admin(1, "Miguel" , "admin", "1234");
        dbManager.insertAdmin(admin1);

        List<Anchorages> anchoragesList = new ArrayList<>();

        anchoragesList.add(new Anchorages(1, 'A', 7.99, 10.40, 40));
        anchoragesList.add( new Anchorages(2, 'B', 9.99, 15.50, 32));
        anchoragesList.add(new Anchorages(3, 'C', 11.99, 19.50, 35));
        anchoragesList.add( new Anchorages(4, 'D', 14.99, 25.60, 30));
        anchoragesList.add( new Anchorages(5, 'E', 17.99, 50.50, 25));
        anchoragesList.add( new Anchorages(6, 'F', 19.99, 62.80, 17));
        anchoragesList.add( new Anchorages(7, 'G', Double.POSITIVE_INFINITY, 80.00, 7));

        dbManager.insertAnchorages(anchoragesList);*/


        launch();

    }
}
