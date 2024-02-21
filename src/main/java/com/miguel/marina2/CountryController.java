package com.miguel.marina2;

import com.miguel.marina2.exception.DbException;
import com.miguel.marina2.exception.ValidationException;
import com.miguel.marina2.utils.Alerts;
import com.miguel.marina2.utils.Constraints;
import com.miguel.marina2.utils.Utils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;

public class CountryController implements Initializable {
    private Country entity;
    private CountryService service;
    private DatabaseManager dbManager;

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtCode;
    @FXML
    private Label labelErrorName;
    @FXML
    private Label labelErrorCode;
    @FXML
    private Button btSave;
    @FXML
    private Button btCancel;

    private ObservableList<Country> obsList;
    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    public void setCountry(Country entity) {
        this.entity = entity;
    }

    public void setService(CountryService service) {
        this.service = service;
    }

    public void setDbManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @FXML
    public  void onBtSaveAction(ActionEvent event){
        try {

            Country newCountry = getFormData();
            dbManager.insertCountry(newCountry);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();

        } catch (ValidationException e) {
            setErrorMessages(e.getErrors());

        } catch (DbException e) {
            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void notifyDataChangeListeners() {
        for(DataChangeListener listener : dataChangeListeners){
            listener.onDataChanged();
        }
    }

    private Country getFormData() {
        Country obj = new Country();

        ValidationException exception = new ValidationException("Validation error");


        obj.setId(Utils.tryParseToInt(txtId.getText()));

        if(txtName.getText() == null || txtName.getText().trim().equals("")){
            exception.addError("name", "Field can't be empty");
        }
        obj.setName(txtName.getText());

        if(txtCode.getText() == null || txtCode.getText().trim().equals("")){
            exception.addError("code", "Field can't be empty");
        }
        obj.setCode(Utils.tryParseToInt(txtCode.getText()));

        if(!exception.getErrors().isEmpty()){
            throw exception;
        }
        return obj;
    }

    @FXML
    public void onBtCanvelAction(ActionEvent event){
        Utils.currentStage(event).close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dbManager = new DatabaseManager();

        initializeNodes();
    }
    private void initializeNodes(){
        Constraints.setTextFieldInteger(txtId);
        Constraints.setTextFieldMaxLength(txtName, 70);
        Constraints.setTextFieldInteger(txtCode);
    }

    public void setErrorMessages(Map<String, String> errors){

        Set<String> fields = errors.keySet();

        labelErrorName.setText(fields.contains("name") ? errors.get("name") : "" );
        labelErrorCode.setText(fields.contains("code") ? errors.get("code") : "");
    }




}
