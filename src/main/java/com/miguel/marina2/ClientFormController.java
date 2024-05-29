package com.miguel.marina2;

import com.miguel.marina2.exception.DbException;
import com.miguel.marina2.exception.ValidationException;
import com.miguel.marina2.utils.Alerts;
import com.miguel.marina2.utils.Constraints;
import com.miguel.marina2.utils.Utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.*;

public class ClientFormController implements Initializable {

    private Client entity;
    private DatabaseManager dbManager;
    private ClientService service;


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
    private Label labelErrorEmail;
    @FXML
    private Label labelErrorPhone;
    @FXML
    private Button btSave;
    @FXML
    private Button btCancel;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    public void setClient(Client entity) {
        this.entity = entity;
    }


    public void setService(ClientService service) {
        this.service = service;
    }

    public void setDbManger(DatabaseManager dbManger) {
        this.dbManager = dbManger;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.dbManager = new DatabaseManager();

        initializeNodes();

    }

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        if(entity == null){
            throw new IllegalStateException("Entity was null");
        }
       try{
           entity = getFormData();
           dbManager.insertClient(entity);
           notifyDataChangeListeners();
           Utils.currentStage(event).close();

       }catch (ValidationException e){

            setErrorMessages(e.getErrors());

        }catch (DbException e) {

            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    private void notifyDataChangeListeners() {
        for(DataChangeListener listener : dataChangeListeners){
            listener.onDataChanged();
        }
    }
    private Client getFormData() {
        Client obj = new Client();

        ValidationException exception = new ValidationException("Validation error");
        obj.setId(Utils.tryParseToInt(txtId.getId()));

        if(txtName.getText() == null || txtName.getText().trim().equals("")){
            exception.addError("name", "Field can't be empty");
        }
        obj.setName(txtName.getText());

        if(txtEmail.getText() == null || txtEmail.getText().trim().equals("")){
            exception.addError("email", "Field can´t be empty");
        }

        obj.setEmail(txtEmail.getText());

        if(txtPhone.getText() == null || txtPhone.getText().trim().equals("")){
            exception.addError("phone", "Field can't be empty");
        }
        obj.setPhone(Utils.tryParseToInt(txtPhone.getText()));

        if(!exception.getErrors().isEmpty()){
            throw exception;
        }
        return obj;
    }

    @FXML
    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }


    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 70);
        Constraints.setTextFieldMaxLength(txtEmail,70);
        Constraints.setTextFieldInteger(txtPhone);
    }

    public void updateFromData() {
        if(entity == null){
            throw new IllegalStateException("Entity was null");
        }
        txtId.setText(String.valueOf(entity.getId()));
        txtName.setText(entity.getName());
        txtEmail.setText(entity.getEmail());
        txtPhone.setText(String.valueOf(entity.getPhone()));
    }

    public void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();

        labelErrorName.setText(fields.contains("name") ? errors.get("name") : "" );
        labelErrorEmail.setText(fields.contains("email") ? errors.get("email") : "" );
        labelErrorPhone.setText(fields.contains("phone") ? errors.get("phone") : "");
    }

    public void updateFormData() {
    }

    public void subscribeDataChangeListener(ClientController clientController) {
    }
}