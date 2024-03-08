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

import javax.security.auth.callback.Callback;
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
    private DatePicker entryDate;
    @FXML
    private DatePicker exitDate;
    @FXML
    private ComboBox comboBoxAnchorages;
    @FXML
    private Label labelErrorRegistration;
    @FXML
    private Label labelErrorCapitanName;
    @FXML
    private Label labelErrorNumPassenger;
    @FXML
    private Label labelErrorClient;
    @FXML
    private Label labelErrorCountry;
    @FXML
    private Label labelErrorAnchorages;
    @FXML
    private Label labelErrorEntryDate;
    @FXML
    private Label labelErrorExitDate;
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

        // Obter e definir o registro
        if (txtRegistration.getText() == null || txtRegistration.getText().trim().isEmpty()) {
            exception.addError("registration", "Field can't be empty");
        }
        obj.setRegistration(String.valueOf(Utils.tryParseToInt(txtRegistration.getText())));

        // Obter e definir o nome do capitão
        if (txtCapitanName.getText() == null || txtCapitanName.getText().trim().isEmpty()) {
            exception.addError("capitanName", "Field can't be empty");
        }
        obj.setCapitanName(txtCapitanName.getText());

        // Obter e definir o número de passageiros
        if (txtNumPassenger.getText() == null || txtNumPassenger.getText().trim().isEmpty()) {
            exception.addError("numPassenger", "Field can't be empty");
        }
        obj.setNumPassenger(Objects.requireNonNull(Utils.tryParseToInt(txtNumPassenger.getText())));

        // Obter e definir o cliente selecionado no comboBoxClient
        if (comboBoxClient.getValue() == null) {
            exception.addError("client", "Please select a client");
        } else {
            Client selectedClient = (Client) comboBoxClient.getValue();
            obj.setClientId(selectedClient);
        }

        // Obter e definir o país selecionado no comboBoxCountry
        if (comboBoxCountry.getValue() == null) {
            exception.addError("country", "Please select a country");
        } else {
            Country selectedCountry = (Country) comboBoxCountry.getValue();
            obj.setCountryId(selectedCountry);
        }

        // Obter e definir a data de entrada
        if (entryDate.getValue() == null) {
            exception.addError("entryDate", "Please select entry date");
        } else {
            obj.setEntryDate(entryDate.getValue());
        }

        // Obter e definir a data de saída
        if (exitDate.getValue() == null) {
            exception.addError("exitDate", "Please select exit date");
        } else {
            obj.setExitDate(exitDate.getValue());
        }

        // Obter e definir o tipo de ancoradouro selecionado no comboBoxAnchorages
        if (comboBoxAnchorages.getValue() == null) {
            exception.addError("anchorage", "Please select an anchorage type");
        } else {
            Anchorages selectedAnchorages = (Anchorages) comboBoxAnchorages.getValue();
            char pierType = selectedAnchorages.getPierType();
            obj.setPierType(pierType);


        }

        // Lançar exceção se houver erros de validação
        if (!exception.getErrors().isEmpty()) {
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

        if(entity.getClientId() == null){
            comboBoxClient.getSelectionModel().selectFirst();
        }
        comboBoxClient.setValue(entity.getClientId());

        if(entity.getCountryId() == null){
            comboBoxCountry.getSelectionModel().selectFirst();
        }
        comboBoxCountry.setValue(entity.getCountryId());

        if (entity.getPierType() != 'N') {
            comboBoxAnchorages.setValue(entity.getPierType());
        } else {
            comboBoxAnchorages.getSelectionModel().selectFirst();
        }
        comboBoxAnchorages.setValue(entity.getPierType());

        if(entity.getEntryDate() != null){
            entryDate.setValue(entity.getEntryDate());
        }

        if(entity.getExitDate() != null){
            exitDate.setValue(entity.getExitDate());
        }
    }


    public void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();

        labelErrorRegistration.setText(fields.contains("registration") ? errors.get("registration") : "" );
        labelErrorCapitanName.setText(fields.contains("capitanName") ? errors.get("capitanName") : "" );
        labelErrorNumPassenger.setText(fields.contains("numPassenger") ? errors.get("numPassenger") : "");
    }
  /*  public void initializeComboBoxClient() {
        Callback<ListView<Client>, ListCell<Client>> factory = lv ->  new ListCell<Client>() {
            @Override
            protected void updateItem(Client item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };

        comboBoxClient.setCellFactory(factory);
        comboBoxClient.setButtonCell(factory.call(null)); // Selecione o item vazio, pois pode não haver um valor padrão
    }*/
}
