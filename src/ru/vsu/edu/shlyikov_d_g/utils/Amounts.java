package ru.vsu.edu.shlyikov_d_g.utils;

import java.math.BigDecimal;

public class Amounts {
    private BigDecimal freeze;
    private BigDecimal nonFreeze;

    public Amounts(BigDecimal freeze, BigDecimal nonFreeze) {
        this.freeze = freeze;
        this.nonFreeze = nonFreeze;
    }

    public Amounts(int freeze, int nonFreeze) {
        this.freeze = BigDecimal.valueOf(freeze);
        this.nonFreeze = BigDecimal.valueOf(nonFreeze);
    }

    public BigDecimal plus(BigDecimal plusAmount, Boolean isFreeze){
        if (isFreeze) return plusFreeze(plusAmount);
        return plusNonFreeze(plusAmount);
    }

    public BigDecimal minus(BigDecimal minusAmount, Boolean isFreeze){
        if (isFreeze) return plusFreeze(minusAmount.multiply(BigDecimal.valueOf(-1)));
        return plusNonFreeze(minusAmount.multiply(BigDecimal.valueOf(-1)));
    }

    public BigDecimal plusFreeze(BigDecimal plusAmount){
        freeze = freeze.add(plusAmount);
        return freeze;
    }

    public BigDecimal plusNonFreeze(BigDecimal plusAmount){
        nonFreeze = nonFreeze.add(plusAmount);
        return nonFreeze;
    }

    public BigDecimal getFreeze() {
        return freeze;
    }

    public void setFreeze(BigDecimal freeze) {
        this.freeze = freeze;
    }

    public BigDecimal getNonFreeze() {
        return nonFreeze;
    }

    public void setNonFreeze(BigDecimal nonFreeze) {
        this.nonFreeze = nonFreeze;
    }
}
