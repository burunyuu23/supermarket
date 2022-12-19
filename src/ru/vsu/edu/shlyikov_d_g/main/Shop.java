package ru.vsu.edu.shlyikov_d_g.main;

import ru.vsu.edu.shlyikov_d_g.events.Purchase;
import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.events.Supply;
import ru.vsu.edu.shlyikov_d_g.events.TransferGoods;
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

    private Supply supply;
    private MoneyScore dayMoney;
    private MoneyScore supplyMoney;

    public Supply getSupply() {
        return supply;
    }

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

    public void play(){
        chooseGameSettings();
        gameVisualise.start();
    }

    public void startDay(){
        supply = new Supply(gameVisualise, money, storage, 3);
        dayMoney = new MoneyScore(new BigDecimal(0));
    }

    public void supply(){
        supplyMoney = new MoneyScore(supply.supply());
        storage.addElements(supply.getElements());
    }

    public void transfer(){
        new TransferGoods(storage, store, gameVisualise).askStorage();
    }

    public void purchase(){
        dayMoney.receive(new Purchase(store, gameVisualise).purchase());
    }

    public void nextDay(){
        storage.nextDay();
        store.nextDay();
        money.spend(supplyMoney.getMoney());
        gameVisualise.endOfTheDay(dayPassed, dayMoney, supplyMoney, money);

        dayPassed++;

        startDay();
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
