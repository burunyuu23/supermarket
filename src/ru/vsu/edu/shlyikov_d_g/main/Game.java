package ru.vsu.edu.shlyikov_d_g.main;

import ru.vsu.edu.shlyikov_d_g.DatePrinter;
import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.events.Supply;
import ru.vsu.edu.shlyikov_d_g.products.Products;
import ru.vsu.edu.shlyikov_d_g.rooms.Storage;
import ru.vsu.edu.shlyikov_d_g.visualisation.Console;
import ru.vsu.edu.shlyikov_d_g.visualisation.Panel;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Game {
    private Console console = new Console();
    private Panel panel = new Panel();

    private MoneyScore money;
    private static final Products products = new Products();
    private DatePrinter date;
    private int day_passed;
    private Storage storage;

    private Supply supply;
//    private Queue<Event> q;

    // 20-30% extra charge
    private int extra_charge;

    public static Products getProducts(){
        return products;
    }

    public Game(){
        date = new DatePrinter();
        money = new MoneyScore(500000);
        storage = new Storage(1000);
    }

    private void chooseGameSettings(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("""
                Выберите режим игры
                "Консоль" или "Окно":""");

        while (supply == null) {
            switch (scanner.nextLine().toLowerCase(Locale.ROOT)) {
                case "консоль" -> supply = new Supply(console, new ArrayList<>(), new ArrayList<>());
                case "окно" -> supply = new Supply(panel, new ArrayList<>(), new ArrayList<>());
                default -> System.out.println("Не могу разобрать, что вы сказали. Повторите еще раз.");
            }
        }
    }

    public void start() {
        chooseGameSettings();

        day_passed = 0;
        Help.start();

        storage.addElements(supply.supply(money, storage));
//        while (true) {
//
//
//
//            day_passed++;
//        }
    }
}
