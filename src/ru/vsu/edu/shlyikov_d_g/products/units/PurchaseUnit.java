package ru.vsu.edu.shlyikov_d_g.products.units;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PurchaseUnit extends Unit{
    // "\\w+[\\.\\w+]*"
    private Integer numSupplier = 0;
    private Integer numConsignment = 0;

    public PurchaseUnit(String pattern) {
        super(pattern);
    }

    @Override
    public void setNums(List<Integer> allIntegers){

    }

    // TODO как объединить их?????????????????????????????????
    public static List<PurchaseUnit> toPurchaseUnitList(List<Unit> uList){
        List<PurchaseUnit> puList = new ArrayList<>();
        for (Unit u : uList) {
            puList.add((PurchaseUnit) u);
        }
        return puList;
    }

    public int getNumSupplier() {
        return numSupplier;
    }

    public int getNumConsignment() {
        return numConsignment;
    }
}
