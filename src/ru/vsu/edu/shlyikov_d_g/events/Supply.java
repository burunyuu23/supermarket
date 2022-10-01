package ru.vsu.edu.shlyikov_d_g.events;

import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.humans.buyers.Supplier;
import ru.vsu.edu.shlyikov_d_g.shop_baskets.PlayerShopBasket;
import ru.vsu.edu.shlyikov_d_g.shop_baskets.ShopBasket;

import java.util.ArrayList;
import java.util.List;

public class Supply {
    private List<Supplier> lst;
    private PlayerShopBasket sb = new PlayerShopBasket();

    private void reset(){
        lst = new ArrayList<>();
        lst.add(new Supplier());
        lst.add(new Supplier());
        lst.add(new Supplier());
    }

    public void supply(MoneyScore ms){
        reset();
        System.out.println("Время принимать поставки товаров:");
        for (int i = 0; i < lst.size(); i++) {
            System.out.printf("Товары %d-го поставщика:\n", i+1);
            lst.get(i).showProducts();
        }

        sb.process(lst, ms);

        for (int i = 0; i < lst.size(); i++) {
            System.out.printf("Товары %d-го поставщика:\n", i+1);
            lst.get(i).showProducts();
        }
    }
}
