package ru.vsu.edu.shlyikov_d_g.shop_baskets;

import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.humans.buyers.Supplier;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.products.PurchaseUnit;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class PlayerShopBasket extends ShopBasket{
    public void process(List<Supplier> supplierList, MoneyScore ms){
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Какие товары вы купите?\n" +
                    "Вводите в следующем формате [Номер поставщика-Номер товара-Количество], например [1-2-100, 1-3-125, 2-4-250].");
            add(scanner.nextLine(), supplierList);
            showCost(ms, scanner);
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

    protected void add(String str, List<Supplier> supplierList){ // стоит ли переносить взаимодействие с другими классами в них самих?
        boolean equals = false;
        for (String s:regexStr(str,"\\w+-\\w+-\\w+[\\.\\w+]*")) {
            PurchaseUnit pu = regexPurchaseUnit(s, "\\w+[\\.\\w+]*");

            int num_supplier = pu.getNumOfSupplier() - 1;
            int num_consignment = pu.getNumOfConsignment() - 1;
            double amount = pu.getAmount();

            Consignment cTemp = supplierList.get(num_supplier).getBasket().get(num_consignment);
            Consignment c = new Consignment(cTemp);

            for (Consignment consignment : this.lst) {

                if (consignment.getVendor_code().equals(c.getVendor_code())){

                    double a = cTemp.minusAmount(amount);
                    consignment.plusAmount(a);
                    cost += consignment.getUnit_price() * a;
                    cost = Consignment.round(cost, 2);

                    equals = true;
                }
                if (equals){
                    break;
                }
            }
            if (!equals) {
                double a = cTemp.minusAmount(amount);
                if (a != 0) {
                    c.setAmount(a);
                    cost += c.getUnit_price() * a;
                    cost = Consignment.round(cost, 2);
                    this.lst.add(c);
                }
            }

            if (cTemp.getAmount() == 0){
                System.out.println("Товар \"" + cTemp.getProduct_name() + "\" закончился!");
            }

            equals = false;
        }
    }

    @Override
    protected void remove(Scanner scanner){
        System.out.println("""
                Удалите товары из списка, например, введя их в формате [1,3,5]
                При этом удалятся товары под номерами 1,3 и 5
                """);
        showConsignment();
        String str = scanner.nextLine();
        for (String s:regexStr(str,"\\w+")) {
            List<Integer> list = regexInt(s, "\\w+");
            for (int i:list) {
                Consignment c = lst.remove(i-1);
                cost -= c.getUnit_price() * c.getAmount();
            }
        }
    }

    private void showCost(MoneyScore ms, Scanner scanner){
        System.out.printf("Общая сумма закупки составила: %.2f руб.\n", cost);
        System.out.printf("У вас осталось %.2f\n", ms.getMoney());
        while (ms.getMoney() < cost){
            System.out.println("У вас недостаточно денег для закупки этих товаров");
            System.err.printf("%.2f/%.2f\n", ms.getMoney(), cost);
            remove(scanner);
        }
    }

    private void showConsignment(){
        System.out.println("Корзинка:");
        int i = 1;
        for (Consignment c:lst) {
            double price = Consignment.round(c.getAmount() * c.getUnit_price(), 2);
            System.out.println("Общая сумма закупки этого товара: " + price);
            System.out.println(i+". " + c.toStringSupplier());
            i++;
        }
    }
}
