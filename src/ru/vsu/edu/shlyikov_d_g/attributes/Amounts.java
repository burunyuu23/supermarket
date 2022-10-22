package ru.vsu.edu.shlyikov_d_g.attributes;

import java.math.BigDecimal;

public class Amounts {
    private BigDecimal freeze;
    private BigDecimal nonFreeze;

    public Amounts(BigDecimal freeze, BigDecimal nonFreeze) {
        this.freeze = freeze;
        this.nonFreeze = nonFreeze;
    }

    public BigDecimal plus(BigDecimal a, Boolean b){
        if (b){
            freeze = freeze.add(a);
            return freeze;
        }
        else{
            nonFreeze = nonFreeze.add(a);
            return nonFreeze;
        }
    }

    public BigDecimal plusFreeze(BigDecimal a){
        freeze = freeze.add(a);
        return freeze;
    }

    public BigDecimal plusNonFreeze(BigDecimal a){
        nonFreeze = nonFreeze.add(a);
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
