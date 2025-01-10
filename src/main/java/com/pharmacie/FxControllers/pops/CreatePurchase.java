package com.pharmacie.FxControllers.pops;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class CreatePurchase {

    @FXML
    private VBox chosenMedicinesBox;

    @FXML
    private TextField clientContactTF;

    @FXML
    private TextField clientEmailTF;

    @FXML
    private TextField clientNameTF;

    @FXML
    private ImageView medicineIV;

    @FXML
    private Label medicineNameLabel;

    @FXML
    private TextField medicineQuantityTF;

    @FXML
    private VBox medicinesBox;

    @FXML
    private Button onValidate;

    @FXML
    private TextField searcher;

    @FXML
    void onCancel(ActionEvent event) {

    }

    @FXML
    void onChooseClient(ActionEvent event) {

    }

    @FXML
    void onChooseQuantity(ActionEvent event) {

    }

    @FXML
    void onValidate(ActionEvent event) {

    }

}
