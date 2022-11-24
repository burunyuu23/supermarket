package ru.vsu.edu.shlyikov_d_g.products.units;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PurchaseUnit extends Unit{
    // "\\w+[\\.\\w+]*"
    // add: "\w+-\w+-\w+[\.\w+]*"
    // remove: "\\w+-\\w+[\\.\\w+]*"

    private Integer numSupplier = 0;
    private Integer numConsignment = 0;

    public PurchaseUnit(String pattern) {
        super(pattern);
    }

    @Override
    public void setNums(List<Integer> allIntegers){
        if (allIntegers.size() == 1){
            numSupplier = -1;
            numConsignment = allIntegers.get(0);
        }
        else{
            numSupplier = allIntegers.get(0);
            numConsignment = allIntegers.get(1);
        }
    }

    // TODO как объединить их?????????????????????????????????
    public static List<PurchaseUnit> toPurchaseUnitList(List<String> uList){
        List<PurchaseUnit> puList = new ArrayList<>();
        for (String u : uList) {
            PurchaseUnit pu = new PurchaseUnit("\\w+[\\.\\w+]*");
            puList.add((PurchaseUnit) pu.fromString(u));
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
