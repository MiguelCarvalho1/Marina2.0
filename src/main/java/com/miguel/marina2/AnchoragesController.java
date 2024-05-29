package com.miguel.marina2;

import com.miguel.marina2.utils.Constraints;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class AnchoragesController implements Initializable {

    @Autowired
    private AnchoragesService service;

    @FXML
    private TableView<Anchorages> tableViewAnchorages;

    @FXML
    private TableColumn<Anchorages, String> tableColumnTypeOfPier;

    @FXML
    private TableColumn<Anchorages, String> tableColumnCapacity;

    private ObservableList<Anchorages> obsList;

    public AnchoragesController() {
    }

    public void setService(AnchoragesService service) {
        this.service = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableColumnTypeOfPier.setCellValueFactory(new PropertyValueFactory<>("pierType"));
        tableColumnCapacity.setCellValueFactory(new PropertyValueFactory<>("places"));
        updateListAnchorages();
    }

    private void updateListAnchorages() {
        if (service == null) {
            throw new IllegalStateException("Service was null");
        }
        List<Anchorages> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewAnchorages.setItems(obsList);
    }
}
