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

public class VesselFormController implements Initializable {

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
        this.dbManager = new DatabaseManager();
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

        if (txtRegistration.getText() == null || txtRegistration.getText().trim().isEmpty()) {
            exception.addError("registration", "Field can't be empty");
        }
        obj.setRegistration(txtRegistration.getText());

        if (txtCapitanName.getText() == null || txtCapitanName.getText().trim().isEmpty()) {
            exception.addError("capitanName", "Field can't be empty");
        }
        obj.setCapitanName(txtCapitanName.getText());

        if (txtNumPassenger.getText() == null || txtNumPassenger.getText().trim().isEmpty()) {
            exception.addError("numPassenger", "Field can't be empty");
        }
        obj.setNumPassenger(Utils.tryParseToInt(txtNumPassenger.getText()));

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

        initializeComboBoxClient();
        initializeComboBoxCountry();
        initializeComboBoxAnchorages();
    }

    public void updateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        }
        txtRegistration.setText(entity.getRegistration());
        txtCapitanName.setText(entity.getCapitanName());
        txtNumPassenger.setText(String.valueOf(entity.getNumPassenger()));

        if (entity.getClientId() == null) {
            comboBoxClient.getSelectionModel().selectFirst();
        } else {
            comboBoxClient.setValue(entity.getClientId());
        }

        if (entity.getCountryId() == null) {
            comboBoxCountry.getSelectionModel().selectFirst();
        } else {
            comboBoxCountry.setValue(entity.getCountryId());
        }

        if (entity.getPierType() == null) {
            comboBoxAnchorages.getSelectionModel().selectFirst();
        } else {
            comboBoxAnchorages.setValue(entity.getPierType());
        }

        if (entity.getEntryDate() != null) {
            entryDate.setValue(LocalDate.ofInstant(entity.getEntryDate().toInstant(), ZoneId.systemDefault()));
        }

        if (entity.getExitDate() != null) {
            exitDate.setValue(LocalDate.ofInstant(entity.getExitDate().toInstant(), ZoneId.systemDefault()));
        }
    }

    public void loadAssociateObjects() {
        if (clientService == null) {
            throw new IllegalStateException("ClientService was null");
        }
        List<Client> list = clientService.findAll();
        obsListClient = FXCollections.observableArrayList(list);
        comboBoxClient.setItems(obsListClient);

        if (countryService == null) {
            throw new IllegalStateException("CountryService was null");
        }
        List<Country> listC = countryService.findAll();
        obsListCountry = FXCollections.observableArrayList(listC);
        comboBoxCountry.setItems(obsListCountry);

        if (anchoragesService == null) {
            throw new IllegalStateException("AnchoragesService was null");
        }
        List<Anchorages> listA = anchoragesService.findAll();
        obsListAnchorages = FXCollections.observableArrayList(listA);
        comboBoxAnchorages.setItems(obsListAnchorages);
    }

    public void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();

        labelErrorRegistration.setText(fields.contains("registration") ? errors.get("registration") : "");
        labelErrorCapitanName.setText(fields.contains("capitanName") ? errors.get("capitanName") : "");
        labelErrorNumPassenger.setText(fields.contains("numPassenger") ? errors.get("numPassenger") : "");
        labelErrorClient.setText(fields.contains("client") ? errors.get("client") : "");
        labelErrorCountry.setText(fields.contains("country") ? errors.get("country") : "");
        labelErrorAnchorages.setText(fields.contains("anchorage") ? errors.get("anchorage") : "");
        labelErrorEntryDate.setText(fields.contains("entryDate") ? errors.get("entryDate") : "");
        labelErrorExitDate.setText(fields.contains("exitDate") ? errors.get("exitDate") : "");
    }

    private void initializeComboBoxClient() {
        comboBoxClient.setCellFactory(param -> new ListCell<Client>() {
            @Override
            protected void updateItem(Client item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
        comboBoxClient.setButtonCell(new ListCell<Client>() {
            @Override
            protected void updateItem(Client item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
    }

    private void initializeComboBoxCountry() {
        comboBoxCountry.setCellFactory(param -> new ListCell<Country>() {
            @Override
            protected void updateItem(Country item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
        comboBoxCountry.setButtonCell(new ListCell<Country>() {
            @Override
            protected void updateItem(Country item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });
    }

    private void initializeComboBoxAnchorages() {
        comboBoxAnchorages.setCellFactory(param -> new ListCell<Anchorages>() {
            @Override
            protected void updateItem(Anchorages item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.valueOf(item.getPierType()));
                }
            }
        });
        comboBoxAnchorages.setButtonCell(new ListCell<Anchorages>() {
            @Override
            protected void updateItem(Anchorages item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.valueOf(item.getPierType()));
                }
            }
        });
    }
}
