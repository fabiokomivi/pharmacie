package com.pharmacie.FxControllers.boards;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class SideBoard implements Initializable{

    private Map<String, Parent> pages = new HashMap<>();
    
    @FXML
    private VBox adminControl;

    @FXML
    private AnchorPane sideboard;

    @FXML
    private StackPane content;

    @FXML
    void onNavButtonSelected(ActionEvent event) {

        Button clickedButton = (Button) event.getSource();

        System.out.println(clickedButton.getText().trim());
        
        onNavChange(clickedButton);
        showPage(clickedButton.getText().trim());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Précharger les pages FXML dans le dictionnaire
        try {
            pages.put("dashboard", FXMLLoader.load(getClass().getResource("/com/pharmacie/fxml/topSections/dashboard.fxml")));
            pages.put("ventes", FXMLLoader.load(getClass().getResource("/com/pharmacie/fxml/topSections/ventes.fxml")));
            pages.put("gestion", FXMLLoader.load(getClass().getResource("/com/pharmacie/fxml/topSections/gestion.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du préchargement des pages FXML");
        }

        // Charger une page par défaut
        showPage("dashboard");

    }



    public void onNavChange(Button clickedButton) {

        // Parcourir toute la hiérarchie de VBox dans 'sideboard' pour trouver les boutons
        sideboard.getChildren().stream()
            .filter(node -> node instanceof VBox) // Trouver les VBox de niveau 1
            .flatMap(vbox -> ((VBox) vbox).getChildren().stream()) // Parcourir leurs enfants
            .filter(node -> node instanceof Button) // Trouver les boutons
            .forEach(node -> {
                Button button = (Button) node;
                // Supprimer les styles 'up' et 'down'
                button.getStyleClass().removeAll("selected");
                /// Ajouter le style 'up' par défaut
                ///button.getStyleClass().add("up");
            });

        // Ajouter le style 'down' au bouton cliqué
        ///clickedButton.getStyleClass().remove("up");
        clickedButton.getStyleClass().add("selected");
    }

    private void showPage(String pageKey) {
        // Vérifier si la page existe dans le dictionnaire
        Parent root = pages.get(pageKey);
        if (root!=null) {
            // Effacer le contenu existant et afficher la nouvelle page
            content.getChildren().clear();
            content.getChildren().setAll(root);
        } else {
            System.out.println("Page introuvable : " + pageKey);
        }
    }

}



