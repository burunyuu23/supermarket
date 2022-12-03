package ru.vsu.edu.shlyikov_d_g.products.units;

import java.util.ArrayList;
import java.util.List;

public class SupplyUnit extends Unit{
    // "\\w+[\\.\\w+]*"
    // add: "\w+-\w+-\w+[\.\w+]*"
    // remove: "\\w+-\\w+[\\.\\w+]*"

    private Integer numSupplier = 0;
    private Integer numConsignment = 0;

    public SupplyUnit(String pattern) {
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

    public static List<SupplyUnit> toSupplyUnitList(List<String> uList){
        List<SupplyUnit> puList = new ArrayList<>();
        for (String u : uList) {
            SupplyUnit pu = new SupplyUnit("\\w+[\\.\\w+]*");
            puList.add((SupplyUnit) pu.fromString(u));
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
