package com.miguel.marina2;

import com.miguel.marina2.utils.Alerts;
import com.miguel.marina2.utils.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerTemplateAvailabilityProvider;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    @FXML
    private TableView<Client> tableViewClient;
    @FXML
    private TableColumn<Client, String> tableColumnName;
    @FXML
    private TableColumn<Client, String> tableColumnEmail;
    @FXML
    TableColumn<Client, Integer> tableColumnPhone;
    @FXML
    private TableColumn<Client, Client> tableColumnEDIT;

    @FXML
    private TableColumn<Client, Client> tableColumnREMOVE;

    @FXML
    private Button btNew;

    private ObservableList<Client> obsList;
    private DatabaseManager dbManger;

    @FXML
    public void onBtNewAction(ActionEvent event) {
        Stage parentStage = Utils.currentStage(event);
        Client obj = new Client();
        createDialogForm(obj, "clientForm.fxml", parentStage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();

    }

    public void updateTableView() {

        initEditButtons();
        initRemoveButtons();

    }

    private void createDialogForm(Client obj, String absoluteName, Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            AnchorPane pane = loader.load();

            ClientFormController controller = loader.getController(); // Obtenha o controlador
            controller.seClient(obj);
            controller.setService(new ClientService());
            controller.updateFromData();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Marina Software");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
            Alerts.showAlert("IOExcepion", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private void initializeNodes() {
    }

    private void initEditButtons() {
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Client, Client>() {
            private final Button button = new Button("Edit");

            {
                button.setOnAction(event -> {
                    Client client = getTableView().getItems().get(getIndex());
                    dbManger.updateClient(client, (Stage) button.getScene().getWindow());
                });
            }

            @Override
            protected void updateItem(Client client, boolean empty) {
                super.updateItem(client, empty);
                if (empty || client == null) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });
    }

    private void initRemoveButtons() {
        tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Client, Client>() {
            private final Button button = new Button("Remove");

            {
                button.setOnAction(event -> {
                    Client client = getTableView().getItems().get(getIndex());
                    // Chamar o método de exclusão de cliente com o ID do cliente correspondente
                    dbManger.deleteClient(client.getId());
                });
            }

            @Override
            protected void updateItem(Client client, boolean empty) {
                super.updateItem(client, empty);
                if (empty || client == null) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        });
    }
}
