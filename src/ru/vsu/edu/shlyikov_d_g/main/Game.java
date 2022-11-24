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
import ru.vsu.edu.shlyikov_d_g.visualisation.Panel;

import java.math.BigDecimal;
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
    private Purchase purchase;
    private TransferGoods transferGoods;
    private Supply supply;
//    private Queue<Event> q;

    // 20-30% extra charge
    private int extra_charge;

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

    private void start() {
        chooseGameSettings();

        dayPassed = 0;
        gameVisualise.helpStart();
        purchase = new Purchase(store, gameVisualise);

        supply = new Supply(gameVisualise, money, storage, 3);
    }

    public void play(){
        start();

        while (true) {
            System.out.printf("День %s начинается!\n\n", dayPassed+1);
            MoneyScore dayMoney = new MoneyScore(new BigDecimal(0));
            MoneyScore supplyMoney = new MoneyScore(supply.supply());

            storage.addElements(supply.getElements());
            transferGoods.askStorage();

            for (int i = 0; i < 4; i++) {
                dayMoney.receive(purchase.purchase());
            }

            storage.nextDay();
            store.nextDay();
            money.spend(supplyMoney.getMoney());
            System.out.printf("День %s закончен!\n\nОтчет:\n", dayPassed+1);
            System.out.println("За сегодня заработано: " + dayMoney);
            System.out.println("За сегодня потрачено: " + supplyMoney);
            System.out.println("currMoney= " + money);
            if (!gameVisualise.continueEvent("Продолжить играть")){
                break;
            }
            dayPassed++;
        }
    }
}
