package com.pharmacie.FxControllers.pops;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.pharmacie.FxControllers.cards.MedicineCategoryCard;
import com.pharmacie.controllers.CategoryController;
import com.pharmacie.controllers.MedicineController;
import com.pharmacie.models.Category;
import com.pharmacie.models.Medicine;
import com.pharmacie.utilities.Dialogs;
import com.pharmacie.utilities.OneSelectable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateMedecine implements Initializable, OneSelectable{

    MedicineController medicineController = new MedicineController();
    CategoryController categoryController = new CategoryController();

    Medicine medicine = null;

    @FXML
    private VBox categoriesBox;

    @FXML
    private TextField medicineName;

    @FXML
    private TextField medicinePrice;

    @FXML
    private TextField medicineMinValue;

    @FXML
    private VBox root;

    @FXML
    void onClose(ActionEvent event) {
        closeWindow(event);
    }

    @FXML
    void onValidate(ActionEvent event) {
        if (medicineName.getText().trim().isEmpty()) {
            Dialogs.showSimpleMessage("veuillez specifier le nom du produit");
            return;
        } else if (medicinePrice.getText().trim().isEmpty()){
            Dialogs.showSimpleMessage("veuillez specifier le prix du produit");
            return;
        } else{
            try {
                Double.valueOf(medicinePrice.getText().trim());
            }
            catch (Exception e) {
                Dialogs.showSimpleMessage("prix invalide");
                return;
            }
        }
        
        String name = medicineName.getText().trim();
        double price = Double.valueOf(medicinePrice.getText().trim());

        MedicineCategoryCard selectedMedicineCategoryCard = null;

        for (Node node : categoriesBox.getChildren()) {
            MedicineCategoryCard controller = (MedicineCategoryCard) node.getUserData();
            if(controller.isChecked()) {
                selectedMedicineCategoryCard = controller;
                break;
            }
        }

        if( selectedMedicineCategoryCard == null) {
            Dialogs.showSimpleMessage("veuillez selectionner une categorie pour le produit");
            return;
        }

        if (medicine == null) {
            medicine = new Medicine(name, price);
            medicine.setCategory(selectedMedicineCategoryCard.getCategory());
            medicineController.saveMedicine(medicine);
        } else {
            medicine.setName(name);
            medicine.setPrice(price);
            medicine.getCategory().removeMedicine(medicine);
            medicine.setCategory(selectedMedicineCategoryCard.getCategory());
            medicineController.updateMedicine(medicine);
        }

        closeWindow(event);
    }

    private void loadCategories() throws IOException {

        List<Category> categories = categoryController.getAllCategories();

        if (!categories.isEmpty())
            for (Category category : categories){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pharmacie/fxml/cards/medicineCategorie.fxml"));
                Parent root = loader.load();

                MedicineCategoryCard controller = loader.getController();

                controller.setData(category);
                controller.setOnCategorySelectedListener(this);

                root.setUserData(controller);

                categoriesBox.getChildren().add(root);
            }
    }

    public void setData(Medicine medicine) {
        this.medicine = medicine;
        medicineName.setText(medicine.getName());
        medicinePrice.setText(String.valueOf(medicine.getPrice()));

        for (Node node : categoriesBox.getChildren()) {
            MedicineCategoryCard medicineCategoryCard = (MedicineCategoryCard) node.getUserData();
            if (medicineCategoryCard.getCategory().hasMedicine(medicine))
            medicineCategoryCard.check();
        }
    }

    public Medicine getMedicine() {
        return medicine;
    }

    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadCategories();
        } catch (IOException e) {
            e.printStackTrace();
        };
    }

    @Override
    public void onSelection(Object selectedCard) {
        // Parcourir toutes les cartes et décocher celles qui ne sont pas sélectionnées
        for (Node node : categoriesBox.getChildren()) {
            MedicineCategoryCard medicineCategoryCard = (MedicineCategoryCard) node.getUserData();
            if (medicineCategoryCard != selectedCard) {
                medicineCategoryCard.uncheck();
            }
        }
    }

}
