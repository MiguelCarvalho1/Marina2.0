package com.miguel.marina2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InvoiceController {
    @FXML
    private TextField clientNameTextField;
    @FXML
    private TextField entryDateTimeTextField;
    @FXML
    private TextField exitDateTimeTextField;
    @FXML
    private TextField anchorageIdTextField;
    @FXML
    private Label invoiceLabel;

    @FXML
    public void generateInvoice() {
        String clientName = clientNameTextField.getText();
        Date entryDateTime = parseDate(entryDateTimeTextField.getText());
        Date exitDateTime = parseDate(exitDateTimeTextField.getText());
        int anchorageId = Integer.parseInt(anchorageIdTextField.getText());

        Anchorages anchorage = getAnchorageById(anchorageId);
        double anchorageCostPerHour = anchorage.getPrice();

        Invoice invoice = new Invoice(clientName, entryDateTime, exitDateTime, anchorageCostPerHour);

        invoiceLabel.setText(invoice.generateInvoice());
    }

    private Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing date: " + date, e);
        }
    }

    private Anchorages getAnchorageById(int id) {

        AnchoragesService anchoragesService = new AnchoragesService();
        return anchoragesService.findById(id);
    }
}
