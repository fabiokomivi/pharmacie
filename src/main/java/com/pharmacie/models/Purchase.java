package com.pharmacie.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchases")
public class Purchase extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "payment_mode_id", nullable = false)
    private PaymentMode paymentMode;

    @OneToMany(mappedBy = "purchase", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicinePurchase> medicinesLink = new ArrayList<>();

    @Column(name = "total", nullable = false)
    private double total;

    @Column(name = "purchase_date", nullable = false, updatable = false)
    private LocalDateTime purchaseDate;

    @Column(name = "status", nullable = false)
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;  // L'admin ou l'employé qui a enregistré l'achat

    // Constructeurs
    public Purchase() {}

    public Purchase(Client client, double totalPrice, User user, PaymentMode paymentMode) {
        this.client = client;
        this.total = totalPrice;
        this.purchaseDate = LocalDateTime.now(); // Enregistre la date de l'achat
        this.user = user;
        this.status = false; // Statut par défaut
        this.paymentMode = paymentMode;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public PaymentMode getPaymentMode() {
        return this.paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<MedicinePurchase> getMedicinesLink() {
        return medicinesLink;
    }

    public void addMedicinePurchase(MedicinePurchase medicinePurchase) {
        this.medicinesLink.add(medicinePurchase);
        medicinePurchase.setPurchase(this);
    }

    public void removeMedicinePurchase(MedicinePurchase medicinePurchase) {
        this.medicinesLink.remove(medicinePurchase);
        medicinePurchase.setPurchase(null);
    }

    // Méthode pour calculer le total du prix d'achat
    public void calculateTotal() {
        double totalAmount = 0;
        for (MedicinePurchase mp : medicinesLink) {
            totalAmount += mp.getTotalPrice(); // Calcule le total en fonction des achats spécifiques de chaque médicament
        }
        this.total = totalAmount;
    }

    // toString() pour afficher les informations essentielles de l'achat
    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", client=" + client.getName() +
                ", total=" + total +
                ", purchaseDate=" + purchaseDate +
                ", status=" + status +
                ", user=" + user.getName() +
                '}';
    }
}