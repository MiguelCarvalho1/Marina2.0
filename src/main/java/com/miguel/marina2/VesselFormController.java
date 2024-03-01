package com.miguel.marina2;

import com.miguel.marina2.exception.DbException;
import com.miguel.marina2.exception.ValidationException;
import com.miguel.marina2.utils.Alerts;
import com.miguel.marina2.utils.Constraints;
import com.miguel.marina2.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.*;

public class VesselFormController implements Initializable {

    private Vessel entity;
    private DatabaseManager dbManager;
    private VesselService service;


    @FXML
    private TextField txtRegistration;
    @FXML
    private TextField txtCapitanName;
    @FXML
    private TextField txtNumPassenger;
    @FXML
    private ComboBox comboBoxClient;
    @FXML
    private ComboBox comboBoxCountry;
    @FXML
    private ComboBox comboBoxAnchorages;
    @FXML
    private Label labelErrorRegistration;
    @FXML
    private Label labelErrorCapitanName;
    @FXML
    private Label labelErrorNumPassenger;
    @FXML
    private Button btSave;
    @FXML
    private Button btCancel;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    public void setVessel(Vessel entity) {
        this.entity = entity;
    }


    public void setService(VesselService service) {
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
            dbManager.insertVessel(entity);
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
    private Vessel getFormData() {
        Vessel obj = new Vessel();

        ValidationException exception = new ValidationException("Validation error");
        if(txtRegistration.getText() == null || txtRegistration.getText().trim().isEmpty()){
            exception.addError("registration", "Field can't be empty");
        }
        obj.setRegistration(String.valueOf(Utils.tryParseToInt(txtRegistration.getText())));

        if(txtCapitanName.getText() == null || txtCapitanName.getText().trim().isEmpty()){
            exception.addError("capitanName", "Field can't be empty");
        }
        obj.setCapitanName(txtCapitanName.getText());

        if(txtNumPassenger.getText() == null || txtNumPassenger.getText().trim().isEmpty()){
            exception.addError("email", "Field can´t be empty");
        }

        obj.setNumPassenger(Objects.requireNonNull(Utils.tryParseToInt(txtNumPassenger.getText())));


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
        Constraints.setTextFieldInteger(txtRegistration);
        Constraints.setTextFieldMaxLength(txtCapitanName, 70);
        Constraints.setTextFieldInteger(txtNumPassenger);
    }

    public void updateFromData() {
        if(entity == null){
            throw new IllegalStateException("Entity was null");
        }
        txtRegistration.setText(String.valueOf(entity.getRegistration()));
        txtCapitanName.setText(entity.getCapitanName());
        txtNumPassenger.setText(String.valueOf(entity.getNumPassenger()));
    }

    public void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();

        labelErrorRegistration.setText(fields.contains("registration") ? errors.get("registration") : "" );
        labelErrorCapitanName.setText(fields.contains("capitanName") ? errors.get("capitanName") : "" );
        labelErrorNumPassenger.setText(fields.contains("numPassenger") ? errors.get("numPassenger") : "");
    }
}
