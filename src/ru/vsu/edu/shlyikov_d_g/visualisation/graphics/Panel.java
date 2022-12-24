package ru.vsu.edu.shlyikov_d_g.visualisation.graphics;

import ru.vsu.edu.shlyikov_d_g.events.Supply;
import ru.vsu.edu.shlyikov_d_g.humans.buyers.Customer;
import ru.vsu.edu.shlyikov_d_g.main.Shop;
import ru.vsu.edu.shlyikov_d_g.main.application.*;
import ru.vsu.edu.shlyikov_d_g.main.application.game.GamePanel;
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
    private Supply supply;
    private final MainMenuPanel mainMenuPanel = new MainMenuPanel();
    private final SupplyMenuPanel supplyMenuPanel;
    private RoomPanel roomPanel = new RoomPanel();
    private GamePanel gamePanel = new GamePanel();
    private final ShopFrame frame;
    private List<String> list = new ArrayList<>();
    private final ReadyEvent readyEvent = new ReadyEvent();
    private int consignmentIndex = 0;

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
            supply.add(list);
            list.clear();

            frame.setCurrentNotGamePanel(ShopFrame.CurrentNotGamePanel.ROOM_PANEL);
            showConsignments(supply.getElements());
        });

        frame.addListener(() -> {
            if (frame.getCurrentNotGamePanel().equals(ShopFrame.CurrentNotGamePanel.MAIN_MENU_PANEL)) {
                getFrom("закупить", new SupplyUnit(""));
                shop.startDay();
                supply = shop.getSupply();
                supply.reset();
                showSuppliers(supply.getSupplierList());
            }
        });

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
    public void showInfoAmount(Amounts amounts, Room room, String roomName, Boolean normalize) {
    }

    @Override
    public void showConsignments(List<Consignment> consignmentList) {
        roomPanel = new RoomPanel(this, consignmentList, "Удалить");

        frame.setRoomPanel(roomPanel);
        frame.setCurrentNotGamePanel(ShopFrame.CurrentNotGamePanel.ROOM_PANEL);

        roomPanel.addListener(() -> {
                ContinuePanel continuePanel = new ContinuePanel("Удалить ");

                continuePanel.addExitListener(() -> {
                    if (!supply.isNormalize()) {
                        supply.remove(list);
                        list.clear();

                        shop.getStorage().addElements(supply.getElements());
                        shop.setSupplyMoney(new MoneyScore(supply.supplyMoney()));

                        transfer();
                    }
                        });
                        continuePanel.addContinueListener(() -> {
                            supply.remove(list);
                            list.clear();
                            roomPanel.refresh(supply.getElements());
                        });
                });
            }

            private void transfer() {
                roomPanel = new RoomPanel(this, shop.getStorage(), "Переместить");

                frame.setRoomPanel(roomPanel);
                frame.setCurrentNotGamePanel(ShopFrame.CurrentNotGamePanel.ROOM_PANEL);

                roomPanel.addListener(() -> {
                        ContinuePanel continueTransferPanel = new ContinuePanel("Переместить");

                        continueTransferPanel.addExitListener(() -> {
                            if (!supply.isNormalize()) {
                                shop.getTransfer().transfer(shop.getStorage(), shop.getStore(), shop.getTransfer().consignmentInput(list));
                                list.clear();

                                showStore();
                            }
                        });

                        continueTransferPanel.addContinueListener(() -> {
                            shop.getTransfer().transfer(shop.getStorage(), shop.getStore(), shop.getTransfer().consignmentInput(list));
                            list.clear();

                            roomPanel.refresh(shop.getStorage());
                        });
                });
            }

            private void showStore(){
                roomPanel = new RoomPanel(this, shop.getStore(), "Продолжить");
                frame.setRoomPanel(roomPanel);
                frame.setCurrentNotGamePanel(ShopFrame.CurrentNotGamePanel.ROOM_PANEL);
                roomPanel.addListener(this::showStorage);
            }

    private void showStorage(){
        roomPanel = new RoomPanel(this, shop.getStorage(), "Продолжить");
        frame.setRoomPanel(roomPanel);
        frame.setCurrentNotGamePanel(ShopFrame.CurrentNotGamePanel.ROOM_PANEL);
        roomPanel.addListener(this::purchases);
    }

    @Override
    public boolean continueEvent(String name) {
        return false;
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
        roomPanel.addListener(this::purchases);

        frame.setRoomPanel(roomPanel);
        frame.setCurrentNotGamePanel(ShopFrame.CurrentNotGamePanel.ROOM_PANEL);
    }

    private void purchases(){
        if (shop.isOverPurchaseCount()) {
            shop.purchase();
            shop.getStore().removeElements(shop.getPurchase().getCustomer().getBasket());
            shop.getPurchase().newCustomer();
            if (shop.getPurchase().getCustomer().getBasket().size() > 0) {
                consignmentIndex = 0;
                continuePurchase(consignmentIndex);
            }
            else{
                purchases();
            }
        }
        else{
            frame.setCurrentNotGamePanel(ShopFrame.CurrentNotGamePanel.SUPPLY_MENU_PANEL);
            getFrom("закупить", new SupplyUnit(""));
            shop.nextDay();
            shop.startDay();
            supply.reset();
            showSuppliers(supply.getSupplierList());
        }
    }

    public void continuePurchase(int i){
        if (shop.getPurchase().getCustomer().getBasket().size() > consignmentIndex) {
            purchase(shop.getPurchase().getCustomer(), shop.getPurchase().getCustomer().getBasket().get(i), shop.getStore());
        }
        else{
            shop.getPurchase().createCheque();
            shop.dayMoneyReceive(shop.getPurchase().getCheque().getGeneralAmount());
            showCheque(shop.getPurchase().getCheque(), shop.getPurchase().getCheque().getGeneralAmount());
            purchases();
        }
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

        gamePanel.addScalesListener(() -> {
            gamePanel.setCurrentGamePanel(GamePanel.CurrentGamePanel.BARCODE_AUTO_PANEL);
        });

        gamePanel.addBarcodeManualListener(() -> {
            consignmentIndex++;
            continuePurchase(consignmentIndex);
        });

        gamePanel.addBarcodeAutoListener(() -> {
            consignmentIndex++;
            continuePurchase(consignmentIndex);
        });

        gamePanel.addCashDeskListener(() -> {
            if (consignment.checkKG()) {
                gamePanel.setCurrentGamePanel(GamePanel.CurrentGamePanel.SCALES_PANEL);
            }
            else {
                gamePanel.setCurrentGamePanel(GamePanel.CurrentGamePanel.BARCODE_MANUAL_PANEL);
            }

//                while (!gamePanel.isCompleted()) {
//                    if (gamePanel.getCurrentGamePanel() == GamePanel.CurrentGamePanel.BARCODE_AUTO_PANEL) break;
//                    System.out.println(gamePanel.isCompleted());
//                }
//                if (gamePanel.getCurrentGamePanel() == GamePanel.CurrentGamePanel.BARCODE_AUTO_PANEL) {
//                    gamePanel.setScalesRight(false);
//                    while (!gamePanel.isSubmitted())
//                        System.out.println(gamePanel.isSubmitted());
//                }
        });
        return consignment.getUnitPrice().multiply(consignment.getAmount());
    }

    private void circleBase(String operationName){
        PopUpDisplay.showHelp(String.format("""
                Нажмите на товары, которые хотите %s,
                после этого выберите их количество и нажмите кнопку действия.
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

        return list;
    }

    private void getFromBase(){
        list.clear();
    }

    @Override
    public List<String> getFrom(String operationName, Unit u, Room room) {
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
