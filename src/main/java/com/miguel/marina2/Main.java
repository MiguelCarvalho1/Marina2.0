package com.miguel.marina2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

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
        Admin admin1 = new Admin(1, "Miguel" , "admin", "1234");
        dbManager.insertAdmin(admin1);
        launch();

    }
}
