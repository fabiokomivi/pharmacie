package com.pharmacie.FxControllers.pops;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.pharmacie.FxControllers.cards.ClientCard;
import com.pharmacie.controllers.ClientController;
import com.pharmacie.models.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ClientChoose implements Initializable{

    ClientController clientController = new ClientController();

    Client client = null;

    @FXML
    private VBox clientBox;

    @FXML
    private TextField searcher;

    @FXML
    void onClose(ActionEvent event) {
        closeWindow(event);
    }

    @FXML
    void onSearch(ActionEvent event) {

    }

    @FXML
    void onValidate(ActionEvent event) {
        closeWindow(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadClients();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadClients() throws IOException {
        for(Client client : clientController.getAllClients()) {
            addClientCard(client);
        }
    }

    private void addClientCard(Client client) throws IOException {
        try{
            // Charger une carte category
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pharmacie/fxml/cards/clientCard.fxml"));
            Parent clientCard = loader.load(); // Charger le fichier FXML

            ClientCard clientCardController = loader.getController(); // Récupérer le contrôleur associé
            
            clientCard.setUserData(clientCardController);

            clientCardController.setData(client);

            clientCard.setOnMouseClicked(event -> styleBinder(clientCard));

            clientBox.getChildren().add(clientCard);
        }
        catch(Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private void styleBinder(Parent card) {
        // Récupérer le contrôleur associé à la carte actuelle
        ClientCard clientCard = (ClientCard) card.getUserData();
        
        // Si multipleSelector n'est pas sélectionné, itérer sur les enfants du supplierGrid
        for (Node node : clientBox.getChildren()) {
            // Vérifier si l'élément a un contrôleur (on suppose que chaque node a un contrôleur associé)
            if (node.getUserData() != null && node != card) {
                // Récupérer le contrôleur de chaque carte
                ClientCard otherCategoryCardController = (ClientCard) node.getUserData();
                // Appeler la méthode removeSelection() pour retirer le style "selected"
                otherCategoryCardController.removeSelection();
            }
        }
        clientCard.toggle();

        if(clientCard.isCardSelected())
            client = clientCard.getClient();
        else
            client = null;
    }

    public Client getClient() {
        return client;
    }

    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
        stage.close();
    }
}
