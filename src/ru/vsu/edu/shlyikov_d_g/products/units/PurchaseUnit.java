package ru.vsu.edu.shlyikov_d_g.products.units;

import java.math.BigDecimal;

public class PurchaseUnit extends Unit{
    public PurchaseUnit(int numSupplier, int numConsignment, BigDecimal amount) {
        super(numSupplier, numConsignment, amount);
    }

    public PurchaseUnit(int numSupplier, int numConsignment, double amount) {
        super(numSupplier, numConsignment, amount);
    }

    public int getNumSupplier() {
        return getNum1();
    }

    public int getNumConsignment() {
        return super.getNum2();
    }
}
