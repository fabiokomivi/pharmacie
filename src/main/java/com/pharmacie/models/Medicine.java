package com.pharmacie.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "medicines") // Nom de la table
public class Medicine extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private double price;

    @OneToMany(mappedBy = "medicine", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Set<Stock> stocks = new HashSet<>();

    @OneToMany(mappedBy = "medicine", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)  // Corrected typo from 'purchasesLinsk'
    private Set<MedicinePurchase> purchasesLink = new HashSet<>();  // Corrected typo

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)  // Relation avec Category
    private Category category;

    // Constructeurs
    public Medicine() {}

    public Medicine(String name, double price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public Medicine(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<Stock> getStocks() {  // Corrected typo from 'geStocksList'
        return this.stocks;
    }

    public int getStock() {
        return stocks.stream()
                     .mapToInt(Stock::getQuantity)  // Sum of all quantities in the stocks
                     .sum();
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<MedicinePurchase> getPurchasesLink() {
        return this.purchasesLink;
    }

    public void addPurchaseLink(MedicinePurchase purchaseLink) {
        this.purchasesLink.add(purchaseLink);
        purchaseLink.setMedicine(this);
    }

    public void removePurchaseLink(MedicinePurchase purchaseLink) {
        this.purchasesLink.remove(purchaseLink);
        purchaseLink.setMedicine(null);
    }

    // Méthode toString pour le débogage
    @Override
    public String toString() {
        return "Medicine{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", price=" + this.price +
                ", stock =" + this.getStock() +
                '}';
    }
}