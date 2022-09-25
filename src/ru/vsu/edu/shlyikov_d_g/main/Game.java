package ru.vsu.edu.shlyikov_d_g.main;

import ru.vsu.edu.shlyikov_d_g.DatePrinter;
import ru.vsu.edu.shlyikov_d_g.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.products.Products;

public class Game {
    private MoneyScore money;
    private Products products;
    private DatePrinter date;
    private int day_passed;
//    private Queue<Event> q;

    // 20-30% extra charge
    private int extra_charge;

    public Game(){
        date = new DatePrinter();
        products = new Products();
        money = new MoneyScore(500000);
    }

    public void start(){
        day_passed = 0;
        Help.start();
        while (true) {



            day_passed++;
        }
    }
}
