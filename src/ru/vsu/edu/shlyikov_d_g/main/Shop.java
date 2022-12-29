package ru.vsu.edu.shlyikov_d_g.main;

import ru.vsu.edu.shlyikov_d_g.events.Purchase;
import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.events.Supply;
import ru.vsu.edu.shlyikov_d_g.events.Transfer;
import ru.vsu.edu.shlyikov_d_g.humans.buyers.Customer;
import ru.vsu.edu.shlyikov_d_g.rooms.Storage;
import ru.vsu.edu.shlyikov_d_g.rooms.Store;
import ru.vsu.edu.shlyikov_d_g.main.visualisation.Console;
import ru.vsu.edu.shlyikov_d_g.main.visualisation.GameVisualise;
import ru.vsu.edu.shlyikov_d_g.main.visualisation.graphics.Panel;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Scanner;

public class Shop {
    private String name = "unnamed";

    private GameVisualise gameVisualise;

    private final Storage storage;
    private final Store store;
    private final MoneyScore money;

    private Supply supply;
    private Transfer transfer;
    private Purchase purchase;

    private MoneyScore dayMoney;
    private MoneyScore supplyMoney;
    private int purchaseCount;

    public int getPurchaseCount() {
        return purchaseCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Supply getSupply() {
        return supply;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    private int dayPassed;
//    private Queue<Event> q;

    // 20-30% extra charge
    private int extra_charge;

    public int getDayPassed() {
        return dayPassed;
    }

    public void setVisualise(GameVisualise gameVisualise){
        this.gameVisualise = gameVisualise;
    };

    public Shop(){
        money = new MoneyScore(500000);
        dayPassed = 1;
        storage = new Storage(1000, 200, "Склад");
        store = new Store(500, 100, "Торговый зал");
    }

    public boolean isOverPurchaseCount() {
        return purchaseCount < 5;
    }

    public boolean isOverPurchaseCount(int k) {
        return purchaseCount < 5+k;
    }

    public void setSupplyMoney(MoneyScore supplyMoney) {
        this.supplyMoney = supplyMoney;
    }

    public void addPurchaseCount(int purchaseCount) {
        this.purchaseCount += purchaseCount;
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
                case "консоль" -> gameVisualise = new Console(this);
                case "окно" -> gameVisualise = new Panel(this);
                default -> System.out.println("Не могу разобрать, что вы сказали. Повторите еще раз.");
            }
        }
    }

    public void play(){
        chooseGameSettings();
        gameVisualise.start();
    }

    public void circle(){
        startDay();

        do {
            gameVisualise.startOfTheDay(dayPassed);

            supply();
            transfer();

            for (int i = 0; i < 4; i++) {
                purchase = new Purchase(store, gameVisualise);
                dayMoney.receive(purchase.purchase());
            }

            nextDay();
        } while (gameVisualise.continueEvent("Продолжить играть"));
    }

    public void startDay(){
        supply = new Supply(gameVisualise, money, storage, 3);
        transfer = new Transfer(storage, store, gameVisualise);
        dayMoney = new MoneyScore(new BigDecimal(0));
        supplyMoney = new MoneyScore(new BigDecimal(0));
        purchaseCount = 0;
    }

    public void supply(){
        supply = new Supply(gameVisualise, money, storage, 3);
        supplyMoney = new MoneyScore(supply.supply());
        storage.addElements(supply.getElements());
    }

    public void supply(int productCount){
        supply = new Supply(store, productCount);
    }

    public void transfer(){
        transfer.askStorage();
    }

    public Purchase purchase(){
        purchase = new Purchase(store, gameVisualise);
        purchaseCount++;
        getStore().removeElements(purchase.getCustomer().getBasket());
        return purchase;
    }

    public Purchase purchase(Customer customer){
        purchase = new Purchase(store, customer);
        purchaseCount++;
        getStore().removeElements(customer.getBasket());
        return purchase;
    }

    public void dayMoneyPurchase(){
        dayMoneyReceive(purchase.purchase());
    }

    public void dayMoneyReceive(BigDecimal money){
        dayMoney.receive(money);
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
