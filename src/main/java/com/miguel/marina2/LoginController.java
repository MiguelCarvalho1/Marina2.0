package com.miguel.marina2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private static Scene mainScene;

    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;

    private DatabaseManager dbManager = new DatabaseManager();

    @FXML
    public void loginButtonOnAction() {
        if (!usernameTextField.getText().isBlank() && !passwordField.getText().isBlank()) {
            if (validateLogin(usernameTextField.getText(), passwordField.getText())) {
                loadAdminScene();
            } else {
                loginMessageLabel.setText("Invalid login credentials!");
            }
        } else {
            loginMessageLabel.setText("Please enter username and password!");
        }
    }

    @FXML
    public void cancelButtonOnAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private boolean validateLogin(String username, String password) {

        return dbManager.validateAdminCredentials(username, password);
    }

    private void loadAdminScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("admin.fxml"));
            AnchorPane root = loader.load();
            mainScene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(mainScene);
            stage.setTitle("Marina Software");
            stage.show();
            Stage currentStage = (Stage) loginButton.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            loginMessageLabel.setText("Falha ao carregar a cena do administrador!");
        }
    }
    }

