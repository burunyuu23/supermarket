package ru.vsu.edu.shlyikov_d_g.products.units;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransferUnit extends Unit {

    private Integer numConsignment;
    private Integer numBatch;

    public TransferUnit(String pattern) {
        super(pattern);
    }

    public static List<TransferUnit> toTransferUnitList(List<Unit> uList){
//        uList.stream().map(); TODO
        List<TransferUnit> tuList = new ArrayList<>();
        for (Unit u : uList) {
            // TransferUnit from Unit
            tuList.add((TransferUnit) u);
        }
        return tuList;
    }

    public int getNumConsignment() {
        return numConsignment;
    }

    public int getNumBatch() {
        return numBatch;
    }

    @Override
    protected void setNums(List<Integer> allIntegers) {

    }
}