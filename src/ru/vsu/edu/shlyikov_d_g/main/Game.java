package ru.vsu.edu.shlyikov_d_g.main;

import ru.vsu.edu.shlyikov_d_g.utils.DatePrinter;
import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.events.Supply;
import ru.vsu.edu.shlyikov_d_g.events.TransferGoods;
import ru.vsu.edu.shlyikov_d_g.products.Products;
import ru.vsu.edu.shlyikov_d_g.rooms.Storage;
import ru.vsu.edu.shlyikov_d_g.rooms.Store;
import ru.vsu.edu.shlyikov_d_g.visualisation.Console;
import ru.vsu.edu.shlyikov_d_g.visualisation.GameVisualise;
import ru.vsu.edu.shlyikov_d_g.visualisation.Panel;

import java.util.Locale;
import java.util.Scanner;

public class Game {

    private GameVisualise gameVisualise;

    private MoneyScore money;
    private static final Products products = new Products();
    private DatePrinter datePrinter;
    private int dayPassed;
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
        datePrinter = new DatePrinter();
        money = new MoneyScore(500000);
        storage = new Storage(1000, 200, "Склад");
        store = new Store(500, 100, "Торговый зал");
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

        dayPassed = 0;
        gameVisualise.helpStart();

        supply = new Supply(gameVisualise, money, storage, 3);

        while (true) {
            supply.supply();
            storage.addElements(supply.getElements());
            transferGoods.askStorage();



            dayPassed++;
        }
    }
}
