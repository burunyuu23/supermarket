package ru.vsu.edu.shlyikov_d_g.events;

import ru.vsu.edu.shlyikov_d_g.Utils;
import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.humans.buyers.Supplier;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.products.PurchaseUnit;
import ru.vsu.edu.shlyikov_d_g.rooms.Storage;

import java.math.BigDecimal;
import java.util.*;

public class Supply {
    private List<Supplier> supplierList;
    private final List<Consignment> consignmentList = new ArrayList<>();
    private BigDecimal cost = new BigDecimal(0);
    private BigDecimal amount = new BigDecimal(0);

    private void reset(){
        supplierList = new ArrayList<>();
        supplierList.add(new Supplier());
        supplierList.add(new Supplier());
        supplierList.add(new Supplier());
    }

    public void supply(MoneyScore ms, Storage storage){
        reset();
        System.out.println("Время принимать поставки товаров:");

        process(supplierList, ms, storage);
    }

    private void showSupplierList(){
        for (int i = 0; i < supplierList.size(); i++) {
            System.out.printf("Товары %d-го поставщика:\n", i+1);
            supplierList.get(i).showProducts();
        }
    }

    private void process(List<Supplier> supplierList, MoneyScore ms, Storage storage){
        Scanner scanner = new Scanner(System.in);

        do {
            boolean isEmpty = true;
            showSupplierList();

            for (Supplier s:supplierList) {
                if (!s.getBasket().isEmpty()){
                    isEmpty = false;
                    break;
                }
            }
            if (isEmpty){
                System.out.println("У поставщиков больше не осталось товаров для Вас.");
                break;
            }

            System.out.println("Какие товары вы купите?\n" +
                    "Вводите в следующем формате [Номер поставщика-Номер товара-Количество], например [1-2-100, 1-3-125, 2-4-250].");
            add(scanner.nextLine(), supplierList, storage);
            showInfo(ms, storage, scanner);
            System.out.println("Продолжить закупку?");
            String str = scanner.nextLine();

            if (str.toLowerCase(Locale.ROOT).equals("нет")){
                break;
            }
            else{
                while (!str.toLowerCase(Locale.ROOT).equals("да")){
                    System.out.println("Не могу разобрать, что вы сказали. Можете повторить?");
                    System.out.println("Продолжить закупку?");
                    str = scanner.nextLine();
                    if (str.toLowerCase(Locale.ROOT).equals("нет")){
                        break;
                    }
                }
            }
            System.out.println();
            showConsignment();
        }
        while (true);

//        showConsignment();
    }

    //  [3-1-1020,2-3-49,2-3-500,1-2-10,2-3-500]

    protected void add(String str, List<Supplier> supplierList, Storage storage){
        // стоит ли переносить взаимодействие с другими классами в них самих?
        boolean equals = false;
        for (String s:Utils.regexStr(str,"\\w+-\\w+-\\w+[\\.\\w+]*")) {
            PurchaseUnit pu = Utils.regexPurchaseUnit(s, "\\w+[\\.\\w+]*");

            int num_supplier = pu.getNums().get(0) - 1;
            int num_consignment = pu.getNums().get(1) - 1;
            BigDecimal amount = pu.getAmount();

            Consignment cTemp = supplierList.get(num_supplier).getBasket().get(num_consignment);
            Consignment c = new Consignment(cTemp);

            for (Consignment consignment : this.consignmentList) {

                if (consignment.getVendor_code().equals(c.getVendor_code())){

                    BigDecimal a = cTemp.minusAmount(amount);
                    consignment.plusAmount(a);
                    this.amount = this.amount.add(a);
                    cost = Utils.round(cost.add(a.multiply(consignment.getUnit_price())),2);

                    equals = true;
                }
                if (equals){
                    break;
                }
            }
            if (!equals) {
                BigDecimal a = cTemp.minusAmount(amount);
                if (a.compareTo(BigDecimal.valueOf(0)) != 0) {
                    c.setAmount(a);
                    this.amount = this.amount.add(a);
                    cost = Utils.round(cost.add(c.getUnit_price().multiply(a)),2);
                    this.consignmentList.add(c);
                }
            }

            if (cTemp.getAmount().compareTo(BigDecimal.valueOf(0)) == 0){
                System.out.println("Товар \"" + cTemp.getProduct_name() + "\" закончился!");
            }

            System.out.println();

            equals = false;
        }
    }

    protected void remove(Scanner scanner){
        System.out.println("""
                Удалите товары из списка, например, введя их в формате [<позиция>-<количество>, <позиция>-<количество>]
                Например: [1-10,3-200,5-1]
                При этом удалятся товары под номерами 1 10 ед.,3 200 ед. и 5 1 ед.
                Если количество удаляемого > количество в корзинке, товар удалится из корзины полностью.
                """);
        showConsignment();
        String str = scanner.nextLine();
        List<PurchaseUnit> puList = new ArrayList<>();
        for (String s:Utils.regexStr(str,"\\w+-\\w+[\\.\\w+]*")) {
            PurchaseUnit pu = Utils.regexPurchaseUnit(s, "\\w+[\\.\\w+]*");
            puList.add(pu);
        }
        puList.sort(Comparator.comparing((PurchaseUnit pu) -> pu.getNums().get(0)).reversed());
        for (PurchaseUnit pu:puList) {
            for (int i : pu.getNums()) {
                Consignment c = consignmentList.get(i-1);
                if (pu.getAmount().compareTo(c.getAmount()) > 0){ // точно ли так
                    consignmentList.remove(i-1);
                }

                BigDecimal amount = c.minusAmount(pu.getAmount());
                this.amount = this.amount.add(amount.multiply(BigDecimal.valueOf(-1)));
                cost = cost.add(c.getUnit_price().multiply(amount.multiply(BigDecimal.valueOf(-1))));
            }
        }
    }

    private void showInfo(MoneyScore ms, Storage storage, Scanner scanner){
        System.out.printf("Общая сумма закупки составила: %.2f руб.\n", cost);
        System.out.printf("У вас осталось %.2f\n", ms.getMoney());
        System.out.printf("Количество : %s руб.\n", Utils.round(this.amount,2));
        while (this.cost.compareTo(ms.getMoney()) > 0 || this.amount.compareTo(storage.getCapacity()) > 0){
            if (this.cost.compareTo(ms.getMoney()) > 0) {
                System.out.println("У вас недостаточно денег для закупки этих товаров!");
                System.err.printf("%.2f/%.2f\n", cost, ms.getMoney());
                remove(scanner);
            }
            if (this.amount.compareTo(storage.getCapacity()) > 0){
                System.out.println("У вас недостаточно места на складе для размещения этих товаров!");
                System.err.printf("%.2f/%.2f\n", this.amount, storage.getCapacity());
                remove(scanner);
            }
        }
    }

    private void showConsignment(){
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
}
