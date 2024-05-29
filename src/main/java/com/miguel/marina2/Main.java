package com.miguel.marina2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class Main extends Application {
    private static Scene mainScene;

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            mainScene = new Scene(root);
            stage.setScene(mainScene);
            stage.setTitle("Marina Software");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Scene getMainScene() {
        return mainScene;
    }

    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();

        Runtime.getRuntime().addShutdownHook(new Thread(dbManager::close));

        // Uncomment below lines for initial data setup
        // setupInitialData(dbManager);

        launch(args);
    }

    // Uncomment this method for initial data setup
    /*
    private static void setupInitialData(DatabaseManager dbManager) {
        Admin admin1 = new Admin(1, "Miguel", "admin", "1234");
        dbManager.insertAdmin(admin1);

        List<Anchorages> anchoragesList = List.of(
                new Anchorages(1, 'A', 7.99, 10.40, 40),
                new Anchorages(2, 'B', 9.99, 15.50, 32),
                new Anchorages(3, 'C', 11.99, 19.50, 35),
                new Anchorages(4, 'D', 14.99, 25.60, 30),
                new Anchorages(5, 'E', 17.99, 50.50, 25),
                new Anchorages(6, 'F', 19.99, 62.80, 17),
                new Anchorages(7, 'G', Double.POSITIVE_INFINITY, 80.00, 7)
        );

        dbManager.insertAnchorages(anchoragesList);
    }
    */
}
