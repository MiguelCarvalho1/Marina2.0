package com.miguel.marina2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    public Button loginButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label LoginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;

    public void loginButtonOnAction(){
        if(!usernameTextField.getText().isBlank() && !passwordField.getText().isBlank()){
            validateLogin(usernameTextField.getText(), passwordField.getText());
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("admin.fxml"));
                Parent root = loader.<Parent>load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

            }catch (IOException e){
                e.printStackTrace();
            }

        }else {
            LoginMessageLabel.setText("Por favor, insira o username e a password!");
        }
    }

    public void cancelButtonOnAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void validateLogin(String text, String text1) {
    }
}
