package com.miguel.marina2;

import com.miguel.marina2.exception.DbException;
import com.miguel.marina2.exception.ValidationException;
import com.miguel.marina2.utils.Alerts;
import com.miguel.marina2.utils.Constraints;
import com.miguel.marina2.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Function;

public class VesselFormController implements Initializable {

    private static final String FIELD_EMPTY_ERROR = "Field can't be empty";
    private Vessel entity;
    private DatabaseManager dbManager;
    private VesselService service;
    private ClientService clientService;
    private CountryService countryService;
    private AnchoragesService anchoragesService;

    @FXML
    private TextField txtRegistration;
    @FXML
    private TextField txtCapitanName;
    @FXML
    private TextField txtNumPassenger;
    @FXML
    private ComboBox<Client> comboBoxClient;
    @FXML
    private ComboBox<Country> comboBoxCountry;
    @FXML
    private DatePicker entryDate;
    @FXML
    private DatePicker exitDate;
    @FXML
    private ComboBox<Anchorages> comboBoxAnchorages;
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

    private ObservableList<Client> obsListClient;
    private ObservableList<Country> obsListCountry;
    private ObservableList<Anchorages> obsListAnchorages;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    public void setVessel(Vessel entity) {
        this.entity = entity;
    }

    public void setService(VesselService service, ClientService clientService, CountryService countryService, AnchoragesService anchoragesService) {
        this.service = service;
        this.clientService = clientService;
        this.countryService = countryService;
        this.anchoragesService = anchoragesService;
    }

    public void subscribeDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    public void setDbManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    @FXML
    public void onBtSaveAction(ActionEvent event) {
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }
        try {
            entity = getFormData();
            dbManager.insertVessel(entity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();
        } catch (ValidationException e) {
            setErrorMessages(e.getErrors());
        } catch (DbException e) {
            Alerts.showAlert("Error saving object", null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }

    private Vessel getFormData() {
        Vessel obj = new Vessel();
        ValidationException exception = new ValidationException("Validation error");

        if (isFieldEmpty(txtRegistration)) {
            exception.addError("registration", FIELD_EMPTY_ERROR);
        } else {
            obj.setRegistration(txtRegistration.getText());
        }

        if (isFieldEmpty(txtCapitanName)) {
            exception.addError("capitanName", FIELD_EMPTY_ERROR);
        } else {
            obj.setCapitanName(txtCapitanName.getText());
        }

        if (isFieldEmpty(txtNumPassenger)) {
            exception.addError("numPassenger", FIELD_EMPTY_ERROR);
        } else {
            obj.setNumPassenger(Utils.tryParseToInt(txtNumPassenger.getText()));
        }

        if (comboBoxClient.getValue() == null) {
            exception.addError("client", "Please select a client");
        } else {
            obj.setClientId(comboBoxClient.getValue());
        }

        if (comboBoxCountry.getValue() == null) {
            exception.addError("country", "Please select a country");
        } else {
            obj.setCountryId(comboBoxCountry.getValue());
        }

        if (entryDate.getValue() == null) {
            exception.addError("entryDate", "Please select entry date");
        } else {
            Instant instant = Instant.from(entryDate.getValue().atStartOfDay(ZoneId.systemDefault()));
            obj.setEntryDate(Date.from(instant));
        }

        if (exitDate.getValue() == null) {
            exception.addError("exitDate", "Please select exit date");
        } else {
            Instant instant = Instant.from(exitDate.getValue().atStartOfDay(ZoneId.systemDefault()));
            obj.setExitDate(Date.from(instant));
        }

        if (comboBoxAnchorages.getValue() == null) {
            exception.addError("anchorage", "Please select an anchorage type");
        } else {
            obj.setPierType(comboBoxAnchorages.getValue());
        }

        if (!exception.getErrors().isEmpty()) {
            throw exception;
        }

        return obj;
    }

    private boolean isFieldEmpty(TextField textField) {
        return textField.getText() == null || textField.getText().trim().isEmpty();
    }

    @FXML
    public void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtRegistration);
        Constraints.setTextFieldMaxLength(txtCapitanName, 70);
        Constraints.setTextFieldInteger(txtNumPassenger);
        Utils.formatDatePicker(entryDate, "dd/MM/yyyy");
        Utils.formatDatePicker(exitDate, "dd/MM/yyyy");

        initializeComboBox(comboBoxClient, Client::getName);
        initializeComboBox(comboBoxCountry, Country::getName);
        initializeComboBox(comboBoxAnchorages, anchorage -> String.valueOf(anchorage.getPierType()));
    }

    public void updateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }
        txtRegistration.setText(entity.getRegistration());
        txtCapitanName.setText(entity.getCapitanName());
        txtNumPassenger.setText(String.valueOf(entity.getNumPassenger()));

        comboBoxClient.setValue(entity.getClientId() == null ? null : entity.getClientId());
        comboBoxCountry.setValue(entity.getCountryId() == null ? null : entity.getCountryId());
        comboBoxAnchorages.setValue(entity.getPierType() == null ? null : entity.getPierType());

        if (entity.getEntryDate() != null) {
            entryDate.setValue(LocalDate.ofInstant(entity.getEntryDate().toInstant(), ZoneId.systemDefault()));
        }

        if (entity.getExitDate() != null) {
            exitDate.setValue(LocalDate.ofInstant(entity.getExitDate().toInstant(), ZoneId.systemDefault()));
        }
    }

    public void loadAssociateObjects() {
        if (clientService == null || countryService == null || anchoragesService == null) {
            throw new IllegalStateException("Services cannot be null");
        }
        obsListClient = FXCollections.observableArrayList(clientService.findAll());
        obsListCountry = FXCollections.observableArrayList(countryService.findAll());
        obsListAnchorages = FXCollections.observableArrayList(anchoragesService.findAll());

        comboBoxClient.setItems(obsListClient);
        comboBoxCountry.setItems(obsListCountry);
        comboBoxAnchorages.setItems(obsListAnchorages);
    }

    public void setErrorMessages(Map<String, String> errors) {
        labelErrorRegistration.setText(errors.getOrDefault("registration", ""));
        labelErrorCapitanName.setText(errors.getOrDefault("capitanName", ""));
        labelErrorNumPassenger.setText(errors.getOrDefault("numPassenger", ""));
        labelErrorClient.setText(errors.getOrDefault("client", ""));
        labelErrorCountry.setText(errors.getOrDefault("country", ""));
        labelErrorAnchorages.setText(errors.getOrDefault("anchorage", ""));
        labelErrorEntryDate.setText(errors.getOrDefault("entryDate", ""));
        labelErrorExitDate.setText(errors.getOrDefault("exitDate", ""));
    }

    private <T> void initializeComboBox(ComboBox<T> comboBox, Function<T, String> toStringFunction) {
        comboBox.setCellFactory(param -> new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : toStringFunction.apply(item));
            }
        });
        comboBox.setButtonCell(new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : toStringFunction.apply(item));
            }
        });
    }
}

