package com.pharmacie.FxControllers.sections;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.pharmacie.FxControllers.cards.PaymentCard;
import com.pharmacie.FxControllers.pops.CreatePayment;
import com.pharmacie.controllers.PaymentModeController;
import com.pharmacie.models.PaymentMode;
import com.pharmacie.utilities.BehaviorSetter;
import com.pharmacie.utilities.Dialogs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class PaymentFx implements Initializable{

    PaymentModeController paymentModeController = new PaymentModeController();

    @FXML
    private FlowPane paymentsGrid;

    @FXML
    private TextField searcher;

    @FXML
    void addPayment(ActionEvent event) throws IOException {
        openWindow();
    }

    @FXML
    void removePayment(ActionEvent event) {
        deleteStock();
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

    public void openWindow() throws IOException {

        FXMLLoader windowLoader = new FXMLLoader(getClass().getResource("/com/pharmacie/fxml/pops/createPaiement.fxml"));
        Parent root = windowLoader.load();

        Stage stage = BehaviorSetter.ModalDraggableTransparent(root);
    
        CreatePayment windowController = windowLoader.getController();
                
        stage.setOnHidden(e -> {
            PaymentMode createdPaymentMode = windowController.getPaymentMode();
            if(createdPaymentMode != null) {
                addPaymentCard(createdPaymentMode);
            }
        });

        stage.showAndWait();
    }
            
    private void styleBinder(Parent son) {
        // Récupérer le contrôleur associé à la carte actuelle
        PaymentCard paymentCard = (PaymentCard) son.getUserData();
        
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
    }

    public void deleteStock() {
        PaymentCard selectedPaymentCard = null;
    
        // Parcours des enfants de la grille pour trouver le mode de paiement sélectionné
        for (Node node : paymentsGrid.getChildren()) {
            PaymentCard currentPaymentCard = (PaymentCard) node.getUserData();
            if (currentPaymentCard.isCardSelected()) {
                selectedPaymentCard = currentPaymentCard;
                break;
            }
        }
    
        // Si aucun mode de paiement n'est sélectionné, afficher un message
        if (selectedPaymentCard == null) {
            Dialogs.showSimpleMessage("Aucun mode de paiement sélectionné");
        } else {
            // Supprimer le mode de paiement dans la base de données
            paymentModeController.deletePaymentMode(selectedPaymentCard.getPaymentMode().getId());
    
            // Supprimer la carte correspondante de la grille
            Node toRemove = null;
            for (Node node : paymentsGrid.getChildren()) {
                if (node.getUserData() == selectedPaymentCard) {
                    toRemove = node;
                    break;
                }
            }
            if (toRemove != null) {
                paymentsGrid.getChildren().remove(toRemove);
            }
        }
    }
    
}
