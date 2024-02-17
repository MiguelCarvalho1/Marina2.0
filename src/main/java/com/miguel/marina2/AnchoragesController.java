package com.miguel.marina2;

import com.miguel.marina2.utils.Constraints;
import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class AnchoragesController implements Initializable {

    //@Autowired
   // private AnchoragesService service;

    @FXML
    private TableView<Anchorages> tableViewAnchorages ;

    @FXML
    private TableColumn<Anchorages, String> tableColumnTypeOfPier;

    @FXML
    private TableColumn<Anchorages, String> tableColumnCapacity;

    @FXML
    private TableColumn<Anchorages, String> tableColumnOccupation;


    private static final Logger logger = LogManager.getLogger(AnchoragesController.class);
    private ObservableList<Anchorages> obsList;

    public AnchoragesController() {

    }
  /*  @Autowired
    public AnchoragesController(AnchoragesService service) {
        this.service = service;
        logger.debug("AnchoragesService instance injected successfully.");
    }

    public void setService(AnchoragesService service) {
        this.service = service;
    }*/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tableColumnTypeOfPier.setCellValueFactory(new PropertyValueFactory<>("typeOfPier"));
        tableColumnCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));

       // updateListAnchorages();

    }



   /* private void updateListAnchorages() {
        if(service == null){
            throw new IllegalStateException("Service was null");
        }
        List<Anchorages> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewAnchorages.setItems(obsList);

    }*/






}
