package com.miguel.marina2;

import com.miguel.marina2.utils.Utils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

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
    private Button btNew;

    private ObservableList<Client> obsList;

    @FXML
    public void onBtNewAction(ActionEvent event){
        Stage parentStage = Utils.currentStage(event);
        Client obj = new Client();
        createDialogForm(obj, "clientForm.xml", parentStage);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();

    }
    public void updateTableView() {

    }
    private void createDialogForm(Client obj, String absoluteName, Stage parentStage) {
    }



    private void initializeNodes() {
    }

    private void initEditButtons() {

    }
    private void initRemoveButtons() {}
}
