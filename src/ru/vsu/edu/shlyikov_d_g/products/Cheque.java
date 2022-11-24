package ru.vsu.edu.shlyikov_d_g.products;

import jdk.jshell.execution.Util;
import ru.vsu.edu.shlyikov_d_g.utils.Utils;

import java.math.BigDecimal;
import java.util.List;

public class Cheque {
    private final List<Consignment> consignmentList;
    private BigDecimal generalAmount = new BigDecimal(0);

    public Cheque(List<Consignment> consignmentList) {
        this.consignmentList = consignmentList;
        for (Consignment c:consignmentList) {
            generalAmount = generalAmount.add(Utils.round(c.getUnitPrice().multiply(c.getAmount()),2));
        }
    }

    public BigDecimal getGeneralAmount() {
        return generalAmount;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("///////////////////////////////////////\n");
        for (Consignment c:consignmentList) {
            String string = c.getProductName() + "    " + c.getUnitPrice() + " руб.  *  " + c.getAmount() + " " + c.getMeasure() + "   =   " + Utils.round(c.getUnitPrice().multiply(c.getAmount()),2) + " руб.\n";
            stringBuilder.append(string);
        }
        String string = "\n\n";
        stringBuilder.append(string);

        stringBuilder.append("///////////////////////////////////////");
        return stringBuilder.toString();
    }
}
