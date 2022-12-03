package ru.vsu.edu.shlyikov_d_g.main;

import ru.vsu.edu.shlyikov_d_g.events.Purchase;
import ru.vsu.edu.shlyikov_d_g.utils.DatePrinter;
import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.events.Supply;
import ru.vsu.edu.shlyikov_d_g.events.TransferGoods;
import ru.vsu.edu.shlyikov_d_g.products.Products;
import ru.vsu.edu.shlyikov_d_g.rooms.Storage;
import ru.vsu.edu.shlyikov_d_g.rooms.Store;
import ru.vsu.edu.shlyikov_d_g.visualisation.Console;
import ru.vsu.edu.shlyikov_d_g.visualisation.GameVisualise;
import ru.vsu.edu.shlyikov_d_g.visualisation.graphics.Panel;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Scanner;

public class Shop {

    private GameVisualise gameVisualise;

    private final Storage storage;
    private final Store store;
    private final MoneyScore money;

    private int dayPassed;
//    private Queue<Event> q;

    // 20-30% extra charge
    private int extra_charge;

    public int getDayPassed() {
        return dayPassed;
    }

    public Shop(){
        money = new MoneyScore(500000);
        dayPassed = 1;
        storage = new Storage(1000, 200, "Склад");
        store = new Store(500, 100, "Торговый зал");
    }

    private void chooseGameSettings(){
        Scanner scanner = new Scanner(System.in);
//        gameVisualise = new Panel(this);
        // vis
        System.out.println("""
                Выберите режим игры
                "Консоль" или "Окно":""");

        while (gameVisualise == null) {
            switch (scanner.nextLine().toLowerCase(Locale.ROOT)) {
                case "консоль" -> gameVisualise = new Console();
                case "окно" -> gameVisualise = new Panel(this);
                default -> System.out.println("Не могу разобрать, что вы сказали. Повторите еще раз.");
            }
        }
    }

    private void start() {
        chooseGameSettings();
        gameVisualise.helpStart();
    }

    public void play(){
        start();

        while (true) {
            gameVisualise.startOfTheDay(dayPassed);

            Supply supply = new Supply(gameVisualise, money, storage, 3);

            MoneyScore dayMoney = new MoneyScore(new BigDecimal(0));
            MoneyScore supplyMoney = new MoneyScore(supply.supply());

            storage.addElements(supply.getElements());
            new TransferGoods(storage, store, gameVisualise).askStorage();

            for (int i = 0; i < 4; i++) {
                dayMoney.receive(new Purchase(store, gameVisualise).purchase());
            }

            storage.nextDay();
            store.nextDay();
            money.spend(supplyMoney.getMoney());
            gameVisualise.endOfTheDay(dayPassed, dayMoney, supplyMoney, money);
            if (!gameVisualise.continueEvent("Продолжить играть")){
                break;
            }
            dayPassed++;
        }
    }

    public Storage getStorage() {
        return storage;
    }

    public Store getStore() {
        return store;
    }

    public MoneyScore getMoney() {
        return money;
    }
}
