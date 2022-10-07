package ru.vsu.edu.shlyikov_d_g.products;

import java.math.BigDecimal;
import java.util.*;
import java.util.ArrayList;

public class PurchaseUnit {
    private List<Integer> nums = new ArrayList<>();
    private BigDecimal amount;

    public PurchaseUnit(int numOfSupplier, int numOfConsignment, BigDecimal amount) {
        this.nums.add(numOfSupplier);
        this.nums.add(numOfConsignment);
        this.amount = amount;
    }

    public PurchaseUnit(int numOfConsignment, BigDecimal amount) {
        this.nums.add(numOfConsignment);
        this.amount = amount;
    }

    public PurchaseUnit(List<Integer> nums, BigDecimal amount) {
        this.nums = nums;
        this.amount = amount;
    }

    public List<Integer> getNums() {
        return nums;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
