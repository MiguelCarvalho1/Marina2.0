package com.miguel.marina2;

import com.miguel.marina2.utils.Utils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ClientFormController implements Initializable {
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhone;
    @FXML
    private Label labelErrorName;
    @FXML
    private Label labelErrorCode;
    @FXML
    private Label labelErrorPhone;
    @FXML
    private Button btSave;
    @FXML
    private Button btCancel;

    private ObservableList<Client> obsList;

    @FXML
    public void onBtSaveAction(ActionEvent event) {
    }

    private Country getFormData() {
        return null;
    }

    @FXML
    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    private void initializeNodes() {
    }

    public void updateFromData() {
    }

    public void setErrorMessages(Map<String, String> errors) {
    }
}