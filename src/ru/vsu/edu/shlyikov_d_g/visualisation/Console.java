package ru.vsu.edu.shlyikov_d_g.visualisation;

import ru.vsu.edu.shlyikov_d_g.Utils;
import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.humans.buyers.Supplier;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.rooms.Storage;

import java.math.BigDecimal;
import java.rmi.MarshalledObject;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Function;

public class Console implements GameVisualise {

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
    public void showInfoGeneral(MoneyScore ms, BigDecimal cost, BigDecimal amount){
        System.out.printf("Общая сумма закупки составила: %.2f руб.\n", cost);
        System.out.printf("У вас осталось %.2f\n", ms.getMoney());
        System.out.printf("Количество : %s руб.\n", Utils.round(amount,2));
    }

    @Override
    public void showInfoCost(MoneyScore ms, BigDecimal cost){
        System.out.println("У вас недостаточно денег для закупки этих товаров!");
        System.err.printf("%.2f/%.2f\n", cost, ms.getMoney());
    }

    @Override
    public void showInfoAmount(BigDecimal amount, Storage storage){
        System.out.println("У вас недостаточно места на складе для размещения этих товаров!");
        System.err.printf("%.2f/%.2f\n", amount, storage.getCapacity());
    }

    @Override
    public void showConsignments(List<Consignment> consignmentList){
        if (!consignmentList.isEmpty()) {
            System.out.println("Корзинка:");
            int i = 1;
            for (Consignment c : consignmentList) {
                BigDecimal price = Utils.round(c.getAmount().multiply(c.getUnit_price()), 2);
                System.out.println("Общая сумма закупки этого товара: " + price);
                System.out.println(i + ". " + c.toStringSupplier());
                i++;
            }
        }
    }

    @Override
    public boolean continueSupply(Scanner scanner) {
        while (true) {
            System.out.println("Продолжить закупку?");
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
        System.out.println("Товар \"" + c.getProduct_name() + "\" закончился!");
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
}
