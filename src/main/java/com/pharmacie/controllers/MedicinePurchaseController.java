package com.pharmacie.controllers;


import com.pharmacie.models.MedicinePurchase;
import com.pharmacie.models.Purchase;
import com.pharmacie.models.Medicine;
import com.pharmacie.services.MedicinePurchaseService;

import java.util.List;

public class MedicinePurchaseController {

    private MedicinePurchaseService medicinePurchaseService;

    public MedicinePurchaseController() {
        this.medicinePurchaseService = new MedicinePurchaseService();  // Instancier le service
    }

    // Ajouter un achat de médicament
    public void addMedicinePurchase(Purchase purchase, Medicine medicine, int quantity) {
        MedicinePurchase medicinePurchase = new MedicinePurchase(purchase, medicine, quantity);
        medicinePurchaseService.saveMedicinePurchase(medicinePurchase);
    }

    // Mettre à jour un achat de médicament
    public void updateMedicinePurchase(int id, Purchase purchase, Medicine medicine, int quantity) {
        MedicinePurchase medicinePurchase = medicinePurchaseService.getMedicinePurchaseById(id);
        if (medicinePurchase != null) {
            medicinePurchase.setPurchase(purchase);
            medicinePurchase.setMedicine(medicine);
            medicinePurchase.setQuantity(quantity);
            medicinePurchaseService.updateMedicinePurchase(medicinePurchase);
        }
    }

    // Supprimer un achat de médicament
    public void deleteMedicinePurchase(int id) {
        medicinePurchaseService.deleteMedicinePurchase(id);
    }

    // Afficher tous les achats de médicaments
    public List<MedicinePurchase> getAllMedicinePurchases() {
        return medicinePurchaseService.getAllMedicinePurchases();
    }

    // Afficher un achat de médicament par ID
    public MedicinePurchase getMedicinePurchaseById(int id) {
        return medicinePurchaseService.getMedicinePurchaseById(id);
    }
}
