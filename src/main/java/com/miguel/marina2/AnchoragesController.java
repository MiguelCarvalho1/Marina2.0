package com.miguel.marina2;

import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class AnchoragesController implements Initializable {

    @Autowired
    private AnchoragesService service;

    @FXML
    private TableView<Anchorages> tableViewAnchorages ;

    @FXML
    private TableColumn<Anchorages, String> tableColumnTypeOfPier;

    @FXML
    private TableColumn<Anchorages, String> tableColumnCapacity;

    @FXML
    private TableColumn<Anchorages, String> tableColumnOccupation;

    private DatabaseManager dbManager;

    public AnchoragesController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        updateListAnchorages();

    }

    private void updateListAnchorages() {
        try {
            // Recupera os dados do MongoDB usando o método getAllAnchorages()
            List<Anchorages> anchoragesList = dbManager.getAllAnchorages();

            // Converte a lista para uma ObservableList
            ObservableList<Anchorages> observableList = FXCollections.observableArrayList(anchoragesList);

            // Atualiza a TableView com os dados
            tableViewAnchorages.setItems(observableList);
        } catch (Exception e) {
            System.err.println("Error updating the anchorage list: " + e.getMessage());
            e.printStackTrace();
        }
    }





}
