package com.miguel.marina2;

import com.miguel.marina2.utils.Alerts;
import com.miguel.marina2.utils.Utils;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import javafx.application.Platform;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.bson.Document;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;


import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClientController implements Initializable, DataChangeListener {

    @FXML
    private TableView<Client> tableViewClient;
    @FXML
    private TableColumn<Client, String> tableColumnName;
    @FXML
    private TableColumn<Client, String> tableColumnEmail;
    @FXML
    private TableColumn<Client, Integer> tableColumnPhone;
    @FXML
    private TableColumn<Client, Client> tableColumnEDIT;

    @FXML
    private TableColumn<Client, Client> tableColumnREMOVE;

    @FXML
    private Button btNew;

    private ObservableList<Client> obsList;
    private DatabaseManager dbManager = new DatabaseManager();

    public void setTableViewClient(TableView<Client> tableViewClient) {
        this.tableViewClient = tableViewClient;
    }

    @FXML
    public void onBtNewAction(ActionEvent event) {
        Stage parentStage = Utils.currentStage(event);
        Client obj = new Client();
        createDialogForm(obj, "clientForm.fxml", parentStage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTableView();
        initializeNodes();



    }

    public void updateTableView() {
        ObservableList<Client> clientList = FXCollections.observableArrayList();

        MongoCollection<Document> clientCollection = dbManager.getDatabase().getCollection("client");
        FindIterable<Document> documents = clientCollection.find();
        MongoCursor<Document> cursor = documents.iterator();

        while (cursor.hasNext()) {
            Document document = cursor.next();
            String name = document.getString("name");
            String email = document.getString("email");
            Integer phone = document.getInteger("phone");
            clientList.add(new Client(name, email, phone));
        }

        tableViewClient.setItems(clientList);
        tableViewClient.refresh();
    }






    private void createDialogForm(Client obj, String absoluteName, Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
            AnchorPane pane = loader.load();

            ClientFormController controller = loader.getController(); // Obtenha o controlador
            controller.setClient(obj);
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
            Alerts.showAlert("IOException", "Error loading view", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private void initializeNodes() {
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tableColumnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        Stage stage = (Stage) Main.getMainScene().getWindow();
        tableViewClient.prefHeightProperty().bind(stage.heightProperty());

        initEditButtons();
        initRemoveButtons();
    }
    @Override
    public void onDataChanged() {

        updateTableView();
    }

    private void initEditButtons() {
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Client, Client>() {
            private final Button button = new Button("Edit");

            {
                button.setOnAction(event -> {
                    Client client = getTableView().getItems().get(getIndex());
                    dbManager.updateClient(client, (Stage) button.getScene().getWindow());
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
                    dbManager.deleteClient(client.getId());
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
