package ru.vsu.edu.shlyikov_d_g.products.units;

import java.math.BigDecimal;

public class TransferUnit extends Unit {

    public TransferUnit(int numConsignment, int numBatch, BigDecimal amount) {
        super(numConsignment, numBatch, amount);
    }

    public TransferUnit(int numSupplier, int numConsignment, double amount) {
        super(numSupplier, numConsignment, amount);
    }

    public int getNumConsignment() {
        return getNum1();
    }

    public int getNumBatch() {
        return super.getNum2();
    }
}