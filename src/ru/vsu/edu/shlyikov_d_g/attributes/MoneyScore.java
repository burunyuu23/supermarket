package ru.vsu.edu.shlyikov_d_g.attributes;

import java.math.BigDecimal;

public class MoneyScore {
    private BigDecimal money;

    public MoneyScore(BigDecimal money){
        this.money = money;
    }

    public MoneyScore(int money){
        this.money = BigDecimal.valueOf(money);
    }


    public MoneyScore(){
        this.money = new BigDecimal((int) (Math.random() * 4900 + 100));
    }

    public BigDecimal getMoney(){
        return money;
    }

    public void spend(BigDecimal spending){
        money = money.add(spending.multiply(BigDecimal.valueOf(-1)));
    }

    public void receive(BigDecimal spending){
        money = money.add(spending);
    }
}
