package ru.vsu.edu.shlyikov_d_g.main;

import ru.vsu.edu.shlyikov_d_g.DatePrinter;
import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.events.Supply;
import ru.vsu.edu.shlyikov_d_g.products.Products;

public class Game {
    private MoneyScore money;
    private static final Products products = new Products();
    private DatePrinter date;
    private int day_passed;
    private Supply supply = new Supply();
//    private Queue<Event> q;

    // 20-30% extra charge
    private int extra_charge;

    public static Products getProducts(){
        return products;
    }

    public Game(){
        date = new DatePrinter();
        money = new MoneyScore(500000);
    }

    public void start(){
        day_passed = 0;
        Help.start();
        supply.supply(money);
//        while (true) {
//
//
//
//            day_passed++;
//        }
    }
}
