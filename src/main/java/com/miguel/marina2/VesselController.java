package com.miguel.marina2;

import com.miguel.marina2.utils.Alerts;
import com.miguel.marina2.utils.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class VesselController implements Initializable, DataChangeListener {
    private VesselService vesselService;
    @FXML
    private TableView<Vessel> tableViewVessel;
    @FXML
    private TableColumn<Vessel, String> tableColumnRegistration;
    @FXML
    private TableColumn<Vessel, String> tableColumnNameCapitan;
    @FXML
    private TableColumn<Client, String> tableColumnClient;
    @FXML
    private TableColumn<Country, String> tableColumnCountry;
    @FXML
    private TableColumn<Vessel, Integer> tableColumnNumPassenger;

    @FXML
    private TableColumn<Vessel, Vessel> tableColumnEDIT;

    @FXML
    private TableColumn<Vessel, Vessel> tableColumnREMOVE;

    private DatabaseManager dbManager = new DatabaseManager();


    @FXML
    private Button btNew;

    private ObservableList<Vessel> obsList;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    public void onBtNewAction(ActionEvent event) {
        Stage parentStage = Utils.currentStage(event);
        Vessel obj = new Vessel();
        createDialogForm(obj, "vesselForm.fxml", parentStage);


    }

    private void createDialogForm(Vessel obj, String absName, Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absName));
            Pane pane = loader.load();

            VesselFormController controller = loader.getController();
            controller.setVessel(obj);
            controller.setService(new VesselService(), new ClientService(), new CountryService(), new AnchoragesService());
            controller.loadAssociateObjects();
            controller.subscribeDataChangeListener(this);
            controller.updateFormData();

            Stage dialogStage1 = new Stage();
            dialogStage1.setTitle("Marina Software");
            dialogStage1.setScene(new Scene(pane));
            dialogStage1.setResizable(false);
            dialogStage1.initOwner(parentStage);
            dialogStage1.initModality(Modality.WINDOW_MODAL);
            dialogStage1.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            Alerts.showAlert("IOException", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    public void setVesselService(VesselService vesselService) {
        this.vesselService = vesselService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
        updateTableView();

    }

    private void initializeNodes() {
        tableColumnRegistration.setCellValueFactory(new PropertyValueFactory<Vessel, String>("registration"));
        tableColumnNameCapitan.setCellValueFactory(new PropertyValueFactory<Vessel, String>("capitanName"));
        tableColumnClient.setCellValueFactory(new PropertyValueFactory<Client, String>("name"));
        tableColumnCountry.setCellValueFactory(new PropertyValueFactory<Country, String>("name"));
        tableColumnNumPassenger.setCellValueFactory(new PropertyValueFactory<Vessel, Integer>("numPassenger"));

        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewVessel.prefHeightProperty().bind(stage.heightProperty());

    }

    public void updateTableView() {
        if (vesselService == null) {
            throw new IllegalStateException("Vessel was null");
        }
        List<Vessel> list = vesselService.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewVessel.setItems(obsList);
        initEditButtons();
        initRemoveButtons();
    }

    private void initRemoveButtons() {
        tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Vessel, Vessel>() {
            private final Button button = new Button("Remove");

            {
                button.setOnAction(event -> {
                    Vessel vessel = getTableView().getItems().get(getIndex());
                    dbManager.deleteVessel(vessel.getRegistration());
                });
            }

            @Override
            protected void updateItem(Vessel vessel, boolean empty) {
                super.updateItem(vessel, empty);
                if (empty || vessel == null) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });
    }


    private void initEditButtons() {
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Vessel, Vessel>() {
            private final Button button = new Button("Edit");

            {
                button.setOnAction(event -> {
                    Vessel vessel = getTableView().getItems().get(getIndex());
                    createDialogForm(vessel, "vesselForm.fxml", Utils.currentStage(event));
                });
            }

            @Override
            protected void updateItem(Vessel vessel, boolean empty) {
                super.updateItem(vessel, empty);
                if (empty || vessel == null) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });
    }

    @Override
    public void onDataChanged() {
        updateTableView();
    }

    public void subscribeDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    private void notifyDataChangeListeners() {
        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }

    private void saveOrUpdateVessel() {

        notifyDataChangeListeners();
    }
}
