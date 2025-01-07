package com.pharmacie.FxControllers.sections;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.pharmacie.controllers.PaymentModeController;
import com.pharmacie.models.Medicine;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

public class PaymentFx implements Initializable{

    PaymentModeController paymentModeController = new PaymentModeController();

    @FXML
    private FlowPane paymentsGrid;

    @FXML
    private TextField searcher;

    @FXML
    void addPayment(ActionEvent event) {

    }

    @FXML
    void removePayment(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadPaymentsModes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPaymentsModes() throws IOException {
        //for(PaymentMode paymentMode : paymentModeController.getAllPaymentModes()) {
          //  addPaymentCard(paymentMode);
     //   }
    }

}
