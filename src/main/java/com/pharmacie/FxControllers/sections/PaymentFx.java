package com.pharmacie.FxControllers.sections;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.pharmacie.FxControllers.cards.PaymentCard;
import com.pharmacie.controllers.PaymentModeController;
import com.pharmacie.models.PaymentMode;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
        for(PaymentMode paymentMode : paymentModeController.getAllPaymentModes()) {
            addPaymentCard(paymentMode);
        }
    }
            
    private void addPaymentCard(PaymentMode paymentMode) {
        try{
            // Charger une carte category
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pharmacie/fxml/cards/paiementCard.fxml"));
            Parent paymentCard = loader.load(); // Charger le fichier FXML

            PaymentCard paymentCardController = loader.getController(); // Récupérer le contrôleur associé
            
            paymentCard.setUserData(paymentCardController);

            paymentCardController.setData(paymentMode);

            paymentCard.setOnMouseClicked(event -> styleBinder(paymentCard));
            
            paymentsGrid.getChildren().add(paymentCard);
        }
        catch(Exception e) {
            e.printStackTrace();
            return;
        }
    }
            
    private void styleBinder(Parent son) {
        // Récupérer le contrôleur associé à la carte actuelle
        PaymentCard paymentCard = (PaymentCard) son.getUserData();
        
        // Si multipleSelector est sélectionné, appliquez le toggle sur la carte
        //if (multipleSelector.isSelected()) {
        if (true) {
            paymentCard.toggle();
        } /*else {
            // Si multipleSelector n'est pas sélectionné, itérer sur les enfants du supplierGrid
            for (Node node : paymentsGrid.getChildren()) {
                // Vérifier si l'élément a un contrôleur (on suppose que chaque node a un contrôleur associé)
                if (node.getUserData() != null && node != son) {
                    // Récupérer le contrôleur de chaque carte
                    PaymentCard otherCardController = (PaymentCard) node.getUserData();
                    // Appeler la méthode removeSelection() pour retirer le style "selected"
                    otherCardController.removeSelection();
                }
            }
            paymentCard.toggle();
        }*/
    }

}
