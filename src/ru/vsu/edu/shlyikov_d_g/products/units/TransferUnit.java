package ru.vsu.edu.shlyikov_d_g.products.units;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransferUnit extends Unit {
    // ask: "\\w+-\\w+-\\w+[\\.\\w+]*"
    private Integer numConsignment;
    private Integer numBatch;

    public TransferUnit(String pattern) {
        super(pattern);
    }

    @Override
    public String toString() {
        return "TransferUnit{" +
                "numConsignment=" + numConsignment +
                ", numBatch=" + numBatch +
                ", amout=" + getAmount() +'}';
    }

    @Override
    public void setNums(List<Integer> allIntegers){
            numConsignment = allIntegers.get(0);
            numBatch = allIntegers.get(1);
    }


    public static List<TransferUnit> toTransferUnitList(List<String> uList){
        List<TransferUnit> tuList = new ArrayList<>();
        for (String u : uList) {
            TransferUnit tu = new TransferUnit("\\w+[\\.\\w+]*");
            tuList.add((TransferUnit) tu.fromString(u));
        }
        return tuList;
    }

    public int getNumConsignment() {
        return numConsignment;
    }

    public int getNumBatch() {
        return numBatch;
    }
}