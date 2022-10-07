package ru.vsu.edu.shlyikov_d_g.humans.buyers;

import ru.vsu.edu.shlyikov_d_g.main.Game;
import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;

import java.math.BigDecimal;

public class Customer extends Buyer {
    private MoneyScore money;

    public MoneyScore getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = new MoneyScore(money);
    }

    public Customer(){
        setGender();
        setName();
        setYears((int) (Math.random() * 100 + 5));
        money = new MoneyScore();
        this.basket = Game.getProducts().getRandomProducts((this.money.getMoney().divide(BigDecimal.valueOf(75))));
    }

}
