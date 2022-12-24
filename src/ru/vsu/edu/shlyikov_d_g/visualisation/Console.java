package ru.vsu.edu.shlyikov_d_g.visualisation;

import ru.vsu.edu.shlyikov_d_g.humans.buyers.Customer;
import ru.vsu.edu.shlyikov_d_g.main.Shop;
import ru.vsu.edu.shlyikov_d_g.products.Cheque;
import ru.vsu.edu.shlyikov_d_g.rooms.Store;
import ru.vsu.edu.shlyikov_d_g.utils.Utils;
import ru.vsu.edu.shlyikov_d_g.utils.Amounts;
import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.humans.buyers.Supplier;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.products.units.Unit;
import ru.vsu.edu.shlyikov_d_g.rooms.Room;
import ru.vsu.edu.shlyikov_d_g.rooms.Storage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Console implements GameVisualise {
    private final Shop shop;

    public Console(Shop shop){
        this.shop = shop;
    }

    @Override
    public void start(){
        System.out.println("Добро пожаловать в игру “Супермаркет”!\n" +
                "В начале игры вы имеете 500 тысяч рублей. На них вы должны будете открыть магазин, который будет приносить вам доход. " +
                "Закупайте товары, следите за их сроком годности, устанавливайте наценку, при которой прибыль будет наибольшей.\n" +
                "Игра представляет из себя бесконечность дней, следующих друг за другом.\n" +

                "В начале дня вы закупаете товар на склад, далее переносите его в торговый зал, " +
                "устанавливаете скидки и, наконец, когда всё готово, открываете магазин.\n" +

                "Во время рабочего дня вам нужно будет следить за количеством товара в торговом зале и пробивать товары на кассе.\n" +

                "После рабочего дня вы будете подсчитывать расходы и доходы, а также составлять дальнейший план развития.\n" +

                "Удачи в росте вашей торговой сети!\n");
        shop.circle();
    }

    @Override
    public void showProducts(List<Consignment> basket){
        int i = 1;
        for (Consignment c : basket) {
            if (!c.getAmount().equals(BigDecimal.valueOf(0))) {
                System.out.println(i + ". " + c.toStringSupplier());
                i++;
            }
        }
    }

    @Override
    public void showSuppliers(List<Supplier> supplierList){
        for (int i = 0; i < supplierList.size(); i++) {
            System.out.printf("Товары %d-го поставщика:\n", i+1);
            showProducts(supplierList.get(i).getBasket());
        }
    }

    @Override
    public void noConsignment(){
        System.out.println("У поставщиков больше не осталось товаров для Вас.");
    }

    @Override
    public void inputSupplyConsignments(){
        System.out.println("Какие товары вы купите?\n" +
                "Вводите в следующем формате [Номер поставщика-Номер товара-Количество], например [1-2-100, 1-3-125, 2-4-250].");
    }

    @Override
    public void showInfoGeneral(MoneyScore ms, BigDecimal cost, Amounts amounts, Storage storage){
        System.out.printf("Общая сумма закупки составила: %.2f руб.\n", cost);
        System.out.printf("У вас: %.2f руб.\n", ms.getMoney());
        System.out.printf("Обычные продукты: %.2f/%.2f\n", amounts.getNonFreeze(), storage.getNonFridgeCapacity());
        System.out.printf("Продукты, требующие хранения в холодильниках: %.2f/%.2f\n\n", amounts.getFreeze(), storage.getFridgeCapacity());

    }

    @Override
    public void showInfoCost(MoneyScore ms, BigDecimal cost){
        System.out.println("У вас недостаточно денег для закупки этих товаров!");
        System.out.printf("%.2f/%.2f\n\n", cost, ms.getMoney());
    }

    @Override
    public void showInfoAmount(Amounts amounts, Room room, String roomName, Boolean normalize){
        String nonFreze = String.format("Обычные продукты: %.2f/%.2f\n", amounts.getNonFreeze(), room.getNonFridgeCapacity());
        String freeze = String.format("Продукты, требующие хранения в холодильниках: %.2f/%.2f\n", amounts.getFreeze(), room.getFridgeCapacity());
        if (normalize){
            System.out.printf("У вас недостаточно места %s для размещения этих товаров!\n", roomName);
            System.out.print(nonFreze);
            System.out.println(freeze);
        }
    }

    @Override
    public void showConsignments(List<Consignment> consignmentList){
        if (!consignmentList.isEmpty()) {
            System.out.println("Корзинка:");
            int i = 1;
            for (Consignment c : consignmentList) {
                BigDecimal price = Utils.round(c.getAmount().multiply(c.getUnitPrice()), 2);
                System.out.println("Общая сумма закупки этого товара: " + price);
                System.out.println(i + ". " + c.toStringSupplier());
                i++;
            }
        }
        System.out.println();
    }

    @Override
    public boolean continueEvent(String name) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.printf("%s?\n", name);
            String str = scanner.nextLine().toLowerCase(Locale.ROOT);

            if (str.equals("нет")) {
                return false;
            }
            else if (str.equals("да")){
                return true;
            }
            else {
                System.out.println("Не могу разобрать, что вы сказали. Можете повторить?");
            }
        }
    }

    @Override
    public void supplyStart()
    {
        System.out.println("Время принимать поставки товаров:");
    }

    @Override
    public void consignmentIsOver(Consignment c){
        System.out.println("Товар \"" + c.getProductName() + "\" закончился!");
    }

    @Override
    public void remove(){
        System.out.println("""
                Удалите товары из списка, например, введя их в формате [<позиция>-<количество>, <позиция>-<количество>]
                Например: [1-10,3-200,5-1]
                При этом удалятся товары под номерами 1 10 ед.,3 200 ед. и 5 1 ед.
                Если количество удаляемого > количество в корзинке, товар удалится из корзины полностью.
                """);
    }

    @Override
    public void removeStore(){
        System.out.println("""
                Удалите товары из списка, например, введя их в формате [<позиция>-<партия>-<количество>, <позиция>-<партия>-<количество>]
                Например: [3-1-10,2-3-200,1-5-1]
                При этом удалятся товары под номерами 3-1 10 ед.,2-3 200 ед. и 1-5 1 ед.
                Если количество удаляемого > количество в корзинке, товар удалится из корзины полностью.
                """);
    }

    @Override
    public void showRoom(Room room) {
        System.out.printf("%s:\n", room.getRoomName());
        Amounts amounts = new Amounts(BigDecimal.valueOf(0), BigDecimal.valueOf(0));
        int i = 0;
        for (String key : room.getElements().keySet()) {
            for (Integer days : room.getElements().get(key).keySet()) {
                Consignment a = room.getElements().get(key).get(days);
                if (a.getBatchNumber() == 0) ++i;
                System.out.println(i + ". " + a.toStringStorage());
                amounts.plus(a.getAmount(), a.getShouldBeInTheFridge());
            }
        }

        System.out.printf("Количество обычных товаров: %.2f/%.2f\n", amounts.getNonFreeze(), room.getNonFridgeCapacity());
        System.out.printf("Количество товаров, требующих хранение в холодильнике: %.2f/%.2f\n\n", amounts.getFreeze(), room.getFridgeCapacity());
    }

    @Override
    public void askRoom(Room room, String roomName){
        System.out.printf("""
                Выберите какие товары и их количество, которые следует переместить в %s.
                Учитывайте вместимость склада/торгового зала и способ хранения продуктов. 
                Например: [2-3-10, 1-2-14.2]
                """, roomName);
    }

    @Override
    public void helpPurchase() {
        System.out.printf("""
                Сейчас на ленте стоят продукты случайного покупателя.
                Вы должны пробить каждый товар либо взвесить его в зависимости от его меры.
                """);
    }

    @Override
    public void showCheque(Cheque cheque, BigDecimal price) {
        System.out.println(cheque);
        System.out.println("Итого: " + Utils.round(price,2));
    }

    @Override
    public BigDecimal purchase(Customer customer, Consignment consignment, Store store) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(consignment.toStringPurchase(false));
        if (consignment.getMeasure().equals("кг")) {
            System.out.println("""
                    1. Взвесить
                    """);
            while (true) {
                String str = scanner.nextLine();

                if (str.equals("1")) {
                    System.out.printf("Продукт весит %s %s\n", consignment.getAmount(), consignment.getMeasure());

                    while (true) {
                        System.out.println("Введите артикул этого продукта:");
                        String key = scanner.nextLine();
                        if (!consignment.getVendorCode().equals(key)) {
                            System.out.println("Это не тот товар! ГАЛЯ ВОЗВРАТ!!!");
                        }
                        else{
                            break;
                        }
                    }
                    break;
                } else {
                    System.out.println("Такой функции нет. Можете повторить?");
                }
            }
        }
        else{
            System.out.println("""
                    1. Пробить штрих-код
                    2. Ввести вручную""");

            while (true) {
                String str = scanner.nextLine();

                if (str.equals("1")) {
                    System.out.println("пи-и-и-п");
                    break;
                } else if (str.equals("2")) {
                    while (true) {
                        System.out.println("Введите артикул этого продукта:");
                        String key = scanner.nextLine();
                        if (!consignment.getVendorCode().equals(key)) {
                            System.out.println("Это не тот товар! ГАЛЯ ВОЗВРАТ!!!");
                        }
                        else{
                            break;
                        }
                    }
                    break;
                } else {
                    System.out.println("Такой функции нет. Можете повторить?");
                }
            }
        }
        System.out.println(consignment.toStringPurchase(true));
        System.out.println();

        return consignment.getUnitPrice().multiply(consignment.getAmount());
    }

    private List<String> circle(String operationName, String pattern) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Введите что %s в формате %s:\n", operationName, pattern);
        String str = scanner.nextLine();
        return Utils.regexStr(str, pattern);
    }

    @Override
    public List<String> getFrom(String operationName, Unit u) {
        String pattern = u.getPattern();
        return circle(operationName, pattern);
    }

    @Override
    public void endOfTheDay(int dayPassed, MoneyScore receive, MoneyScore spending, MoneyScore allMoney) {
        System.out.printf("День %s закончен!\n\nОтчет:\n", dayPassed+1);
        System.out.println("За сегодня заработано: " + receive);
        System.out.println("За сегодня потрачено: " + spending);
        System.out.println("Всего монет: " + allMoney);
    }

    @Override
    public void startOfTheDay(int dayPassed){
        System.out.printf("День %s начинается!\n\n", dayPassed);
    }

    @Override
    public List<String> getFrom(String operationName, Unit u, Room room) {
        String pattern = u.getPattern();
        return circle(operationName, pattern);
    }
}
