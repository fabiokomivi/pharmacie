package com.pharmacie.FxControllers.topSections;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

public class Dashboard implements Initializable {

    List<String> days = new ArrayList<>(Arrays.asList("lun", "mar", "mer", "jeu", "ven", "sam", "dim"));


    @FXML
    private Label emailTop;

    @FXML
    private Circle imageCircle;

    @FXML
    private BarChart<String, Number> mybar;

    @FXML
    private AreaChart<String, Number> areaChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private Label nameTop;

    @FXML
    void onNavButtonSelected(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         // Créer une série de données pour février
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("");

        // Générer des valeurs aléatoires pour chaque jour de février (28 jours)
        Random random = new Random();
        for (int i = 0; i < days.size(); i++) { // L'index commence à 0
            int ventes = random.nextInt(1000); // Génère un nombre aléatoire entre 0 et 999
            series.getData().add(new XYChart.Data<>(days.get(i), ventes));
        }


        // Ajouter la série de données au BarChart
        mybar.getData().add(series);
        mybar.getXAxis().setVisible(false);
        mybar.setLegendVisible(false);



        // Définition des catégories pour l'axe X
        //xAxis.setCategories(javafx.collections.FXCollections.observableArrayList("Jour 1", "Jour 2", "Jour 3", "Jour 4", "Jour 5"));

        // Création d'une série de données
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Données simulées");

        // Ajout de données
        series1.getData().add(new XYChart.Data<>("Jour 1", 10));
        series1.getData().add(new XYChart.Data<>("Jour 2", 20));
        series1.getData().add(new XYChart.Data<>("Jour 3", 30));
        series1.getData().add(new XYChart.Data<>("Jour 4", 25));
        series1.getData().add(new XYChart.Data<>("Jour 5", 35));

        // Ajout de la série au graphique
        areaChart.getData().add(series1);
        areaChart.getXAxis().setVisible(false);
        areaChart.setLegendVisible(false);
        
  }
}


