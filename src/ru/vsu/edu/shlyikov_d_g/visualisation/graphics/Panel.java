package ru.vsu.edu.shlyikov_d_g.visualisation.graphics;

import ru.vsu.edu.shlyikov_d_g.humans.buyers.Customer;
import ru.vsu.edu.shlyikov_d_g.main.Shop;
import ru.vsu.edu.shlyikov_d_g.main.application.*;
import ru.vsu.edu.shlyikov_d_g.main.application.game.GamePanel;
import ru.vsu.edu.shlyikov_d_g.main.application.helper.ChequePanel;
import ru.vsu.edu.shlyikov_d_g.main.application.helper.ContinuePanel;
import ru.vsu.edu.shlyikov_d_g.main.application.non_game.MainMenuPanel;
import ru.vsu.edu.shlyikov_d_g.main.application.non_game.SupplyMenuPanel;
import ru.vsu.edu.shlyikov_d_g.main.application.room.RoomPanel;
import ru.vsu.edu.shlyikov_d_g.products.Cheque;
import ru.vsu.edu.shlyikov_d_g.products.units.SupplyUnit;
import ru.vsu.edu.shlyikov_d_g.rooms.Store;
import ru.vsu.edu.shlyikov_d_g.utils.Amounts;
import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.humans.buyers.Supplier;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.products.units.Unit;
import ru.vsu.edu.shlyikov_d_g.rooms.Room;
import ru.vsu.edu.shlyikov_d_g.rooms.Storage;
import ru.vsu.edu.shlyikov_d_g.utils.Utils;
import ru.vsu.edu.shlyikov_d_g.visualisation.GameVisualise;
import ru.vsu.edu.shlyikov_d_g.visualisation.graphics.adapters.*;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Panel extends JPanel implements GameVisualise {
    private final Shop shop;
    private final MainMenuPanel mainMenuPanel = new MainMenuPanel();
    private final SupplyMenuPanel supplyMenuPanel;
    private RoomPanel roomPanel = new RoomPanel();
    GamePanel gamePanel = new GamePanel();
    private final ShopFrame frame;
    private List<String> list = new ArrayList<>();
    private final ReadyEvent readyEvent = new ReadyEvent();

    public Shop getShop() {
        return shop;
    }

    public Panel(Shop shop){
        this.shop = shop;
        supplyMenuPanel = new SupplyMenuPanel(shop.getDayPassed());

        frame = new ShopFrame(mainMenuPanel, supplyMenuPanel, gamePanel);

        gamePanel.addMouseListener(new GamePanelMouseAdapter(this, gamePanel));
        gamePanel.addMouseMotionListener(new GamePanelMouseMotionAdapter(this, gamePanel));
        supplyMenuPanel.addMouseListener(new GameDrawPanelMouseAdapter(supplyMenuPanel, this));

        supplyMenuPanel.addListener(() -> {
            shop.getSupply().add(list);
            frame.setCurrentNotGamePanel(ShopFrame.CurrentNotGamePanel.ROOM_PANEL);
            showConsignments(shop.getSupply().getElements());
        });

        frame.addListener(() -> {
            if (frame.getCurrentNotGamePanel().equals(ShopFrame.CurrentNotGamePanel.MAIN_MENU_PANEL)) {
                getFrom("закупить", new SupplyUnit(""));
                shop.startDay();
                shop.getSupply().reset();
                showSuppliers(shop.getSupply().getSupplierList());
            }
        });


        roomPanel.addListener(readyEvent);
        mainMenuPanel.addMouseListener(new DrawPanelMouseAdapter(mainMenuPanel, this));

        winMain();
    }

    public List<String> getList() {
        return list;
    }

    private void winMain(){
        Locale.setDefault(Locale.ROOT);

        java.awt.EventQueue.invokeLater(() -> {
            frame.setSize(1600, 900);
            frame.setVisible(true);
        });
    }

    @Override
    public void start(){
        String help = ("Добро пожаловать в игру “Супермаркет”!\n" +
                "В начале игры вы имеете 500 тысяч рублей. На них вы должны будете открыть магазин, который будет приносить вам доход. " +
                "Закупайте товары, следите за их сроком годности, устанавливайте наценку, при которой прибыль будет наибольшей.\n" +
                "Игра представляет из себя бесконечность дней, следующих друг за другом.\n" +

                "В начале дня вы закупаете товар на склад, далее переносите его в торговый зал, " +
                "устанавливаете скидки и, наконец, когда всё готово, открываете магазин.\n" +

                "Во время рабочего дня вам нужно будет следить за количеством товара в торговом зале и пробивать товары на кассе.\n" +

                "После рабочего дня вы будете подсчитывать расходы и доходы, а также составлять дальнейший план развития.\n" +

                "Удачи в росте вашей торговой сети!\n");
        PopUpDisplay.showHelp(help);
    }

    @Override
    public void showProducts(List<Consignment> basket) {

    }

    @Override
    public void showSuppliers(List<Supplier> supplierList) {
        supplyMenuPanel.setSupplierList(supplierList);
    }

    @Override
    public void noConsignment() {

    }

    @Override
    public void inputSupplyConsignments(){

    }

    @Override
    public void showInfoGeneral(MoneyScore ms, BigDecimal cost, Amounts amounts, Storage storage) {

    }

    @Override
    public void showInfoCost(MoneyScore ms, BigDecimal cost) {

    }

    @Override
    public void showInfoAmount(Amounts amounts, Room room, String roomName, Boolean amountedNonFreeze, Boolean amountedFreeze) {
    }

    @Override
    public void showConsignments(List<Consignment> consignmentList) {
        roomPanel = new RoomPanel(this, consignmentList, "Удалить");
        roomPanel.addListener(readyEvent);

        frame.setRoomPanel(roomPanel);
        frame.setCurrentNotGamePanel(ShopFrame.CurrentNotGamePanel.ROOM_PANEL);
    }

    @Override
    public boolean continueEvent(String name) {
        ContinuePanel continuePanel = new ContinuePanel(name);
        continuePanel.addListener(readyEvent);

        while (!readyEvent.isClicked())
            System.out.println(readyEvent.isClicked());

        readyEvent.setClicked(false);
        return continuePanel.isAnswer();
    }

    @Override
    public void supplyStart() {

    }

    @Override
    public void consignmentIsOver(Consignment c) {

    }

    @Override
    public void remove() {
    }

    @Override
    public void removeStore() {

    }

    @Override
    public void showRoom(Room room){
        roomPanel = new RoomPanel(this, room, "Продолжить");
        roomPanel.addListener(readyEvent);

        frame.setRoomPanel(roomPanel);
        frame.setCurrentNotGamePanel(ShopFrame.CurrentNotGamePanel.ROOM_PANEL);

        while (!readyEvent.isClicked())
            System.out.println(readyEvent.isClicked());

        readyEvent.setClicked(false);
    }

    @Override
    public void askRoom(Room room, String roomName) {

    }

    @Override
    public void helpPurchase() {

    }

    @Override
    public void showCheque(Cheque cheque, BigDecimal price) {
        PopUpDisplay.showCheque(cheque.toString() + "\n" + "Итого: " + Utils.round(price,2));
    }

    @Override
    public BigDecimal purchase(Customer customer, Consignment consignment, Store store)  {
        gamePanel = new GamePanel(customer, consignment, store);
        gamePanel.addMouseListener(new GamePanelMouseAdapter(this, gamePanel));
        gamePanel.addMouseMotionListener(new GamePanelMouseMotionAdapter(this, gamePanel));

        frame.setGamePanel(gamePanel);
        frame.setCurrentNotGamePanel(ShopFrame.CurrentNotGamePanel.GAME_PANEL);

        while (!gamePanel.isCustomerChosen())
            System.out.println(gamePanel.isCustomerChosen());

        if (consignment.checkKG()) {
            gamePanel.setCurrentGamePanel(GamePanel.CurrentGamePanel.SCALES_PANEL);
            while (!gamePanel.isScalesRight())
                System.out.println(gamePanel.isScalesRight());

            gamePanel.setCurrentGamePanel(GamePanel.CurrentGamePanel.BARCODE_AUTO_PANEL);
            gamePanel.setScalesRight(false);
            while (!gamePanel.isSubmitted())
                System.out.println(gamePanel.isSubmitted());
        }
        else{
            gamePanel.setCurrentGamePanel(GamePanel.CurrentGamePanel.BARCODE_MANUAL_PANEL);
            while (!gamePanel.isCompleted()) {
                if (gamePanel.getCurrentGamePanel() == GamePanel.CurrentGamePanel.BARCODE_AUTO_PANEL) break;
                System.out.println(gamePanel.isCompleted());
            }
            if (gamePanel.getCurrentGamePanel() == GamePanel.CurrentGamePanel.BARCODE_AUTO_PANEL) {
                gamePanel.setScalesRight(false);
                while (!gamePanel.isSubmitted())
                    System.out.println(gamePanel.isSubmitted());
            }
        }
        return consignment.getUnitPrice().multiply(consignment.getAmount());
    }

    private void circleBase(String operationName){
        PopUpDisplay.showHelp(String.format("""
                Нажмите на товары, которые хотите %s,
                после этого выберите их количество и нажмите купить.
                После закупки нажмите на купить в меню, где все товары.
                """, operationName));
    }

    private List<String> circle(String operationName){

        circleBase(operationName);

        if (operationName.toLowerCase(Locale.ROOT).equals("закупить"))
            frame.setCurrentNotGamePanel(ShopFrame.CurrentNotGamePanel.SUPPLY_MENU_PANEL);

        return list;
    }

    private List<String> circle(String operationName, Room room) {
        circleBase(operationName);

            roomPanel = new RoomPanel(this, room, operationName);
        roomPanel.addListener(readyEvent);

            frame.setRoomPanel(roomPanel);
            frame.setCurrentNotGamePanel(ShopFrame.CurrentNotGamePanel.ROOM_PANEL);

        while (!readyEvent.isClicked())
            System.out.println(readyEvent.isClicked());

        readyEvent.setClicked(false);

        return list;
    }

    private void getFromBase(){
        list.clear();
    }

    @Override
    public List<String> getFromRoom(String operationName, Unit u, Room room) {
        getFromBase();
        return circle(operationName, room);
    }

    @Override
    public List<String> getFrom(String operationName, Unit u) {
        getFromBase();
        return circle(operationName);
    }

    @Override
    public void startOfTheDay(int dayPassed){
    }

    @Override
    public void endOfTheDay(int dayPassed, MoneyScore receive, MoneyScore spending, MoneyScore allMoney) {
        String dayReport = String.format("Закончился %d день!\nЗа сегодняшний день:\n" +
                "Вы заработали: %.2f рублей\n" +
                "Вы потратили: %.2f рублей\n" +
                "Ваше состояние оценивается в %.2f рублей", dayPassed, receive.getMoney(), spending.getMoney(), allMoney.getMoney());
        PopUpDisplay.showHelp(dayReport);
        supplyMenuPanel.plusDay();
    }
}
