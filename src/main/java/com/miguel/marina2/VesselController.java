package com.miguel.marina2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VesselController implements Initializable {
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

    @FXML
    private TextField txtRegistration;

    @FXML
    private Button btNew;

    private ObservableList<Vessel> obsList;

    @FXML
    public void onBtNewAction(ActionEvent event) {


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
    }


    private void initEditButtons() {
    }

   /* @FXML
    public void onSearchByRegistration(ActionEvent event) {
        String registration = txtRegistration.getText();

        try {
            Vessel result = vesselService.findByRegistration(registration);

            ObservableList<Vessel> singleItemList = FXCollections.observableArrayList(result);
            tableViewVessel.setItems(singleItemList);

            txtRegistration.clear();
        } catch (ObjectNotFoundException e) {

            if (e.getMessage().equals("Registo não encontrado")) {
                System.err.println("Registo não encontrado: " + registration);

            } else {

                e.printStackTrace();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }*/
}
