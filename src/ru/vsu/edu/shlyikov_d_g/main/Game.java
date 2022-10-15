package ru.vsu.edu.shlyikov_d_g.main;

import ru.vsu.edu.shlyikov_d_g.DatePrinter;
import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.events.Supply;
import ru.vsu.edu.shlyikov_d_g.events.TransferGoods;
import ru.vsu.edu.shlyikov_d_g.products.Products;
import ru.vsu.edu.shlyikov_d_g.rooms.Room;
import ru.vsu.edu.shlyikov_d_g.rooms.Storage;
import ru.vsu.edu.shlyikov_d_g.rooms.Store;
import ru.vsu.edu.shlyikov_d_g.visualisation.Console;
import ru.vsu.edu.shlyikov_d_g.visualisation.GameVisualise;
import ru.vsu.edu.shlyikov_d_g.visualisation.Panel;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class Game {

    private GameVisualise gameVisualise;

    private MoneyScore money;
    private static final Products products = new Products();
    private DatePrinter date;
    private int day_passed;
    private Storage storage;
    private Store store;

    private TransferGoods transferGoods;
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
        storage = new Storage(1000, "Склад");
        store = new Store(500, "Торговый зал");
    }

    private void chooseGameSettings(){
        Scanner scanner = new Scanner(System.in);

        // vis
        System.out.println("""
                Выберите режим игры
                "Консоль" или "Окно":""");

        while (gameVisualise == null) {
            switch (scanner.nextLine().toLowerCase(Locale.ROOT)) {
                case "консоль" -> gameVisualise = new Console();
                case "окно" -> gameVisualise = new Panel();
                default -> System.out.println("Не могу разобрать, что вы сказали. Повторите еще раз.");
            }
        }

        transferGoods = new TransferGoods(storage, store, gameVisualise);
    }

    public void start() {
        chooseGameSettings();

        day_passed = 0;
        gameVisualise.helpStart();

        supply = new Supply(gameVisualise, new ArrayList<>(), new ArrayList<>());
        storage.addElements(supply.supply(money, storage));
        transferGoods.show();
        transferGoods.askStorage();

//        while (true) {
//
//
//
//            day_passed++;
//        }
    }
}
