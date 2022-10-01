package ru.vsu.edu.shlyikov_d_g.products;

public class PurchaseUnit {
    private int numOfSupplier;
    private int numOfConsignment;
    private double amount;

    public PurchaseUnit(int numOfSupplier, int numOfConsignment, double amount) {
        this.numOfSupplier = numOfSupplier;
        this.numOfConsignment = numOfConsignment;
        this.amount = amount;
    }

    public int getNumOfSupplier() {
        return numOfSupplier;
    }

    public int getNumOfConsignment() {
        return numOfConsignment;
    }

    public double getAmount() {
        return amount;
    }
}
