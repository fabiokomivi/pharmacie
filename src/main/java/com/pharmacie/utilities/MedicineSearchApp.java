package com.pharmacie.utilities;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class MedicineSearchApp extends Application {

    private ObservableList<Medicine> medicineList;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Initial list of medicines
        medicineList = FXCollections.observableArrayList(
                new Medicine("Aspirin"),
                new Medicine("Paracetamol"),
                new Medicine("Ibuprofen"),
                new Medicine("Amoxicillin"),
                new Medicine("Cough Syrup")
        );

        // ListView to display medicines
        ListView<Medicine> listView = new ListView<>(medicineList);

        // Search field to filter the list
        TextField searchField = new TextField();
        searchField.setPromptText("Search for medicine...");

        // Add listener to the search field to filter the list as text changes
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                filterMedicines(newValue);
            }
        });

        // Layout setup
        VBox vbox = new VBox(10, searchField, listView);
        Scene scene = new Scene(vbox, 300, 250);

        primaryStage.setTitle("Medicine Search");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Filter medicines based on the search text
    private void filterMedicines(String searchText) {
        // If search text is empty, show all medicines
        if (searchText.isEmpty()) {
            listView.setItems(medicineList);
        } else {
            // Filter the list to only include medicines whose names contain the search text
            List<Medicine> filteredList = medicineList.stream()
                    .filter(medicine -> medicine.getName().toLowerCase().contains(searchText.toLowerCase()))
                    .collect(Collectors.toList());

            // Update the ListView with the filtered results
            listView.setItems(FXCollections.observableArrayList(filteredList));
        }
    }
}
