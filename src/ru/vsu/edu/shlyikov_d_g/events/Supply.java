package ru.vsu.edu.shlyikov_d_g.events;

import ru.vsu.edu.shlyikov_d_g.Utils;
import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.humans.buyers.Supplier;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.products.PurchaseUnit;
import ru.vsu.edu.shlyikov_d_g.rooms.Storage;
import ru.vsu.edu.shlyikov_d_g.visualisation.GameVisualise;

import java.math.BigDecimal;
import java.util.*;

public class Supply {
    private List<Supplier> supplierList;
    private List<Consignment> consignmentList;
    private BigDecimal cost;
    private BigDecimal amount;

    private GameVisualise visualiser;

    public Supply(GameVisualise visualiser, List<Consignment> consignmentList, List<Supplier> supplierList){
        this.visualiser = visualiser;
        this.consignmentList = consignmentList;
        this.supplierList = supplierList;
        cost = new BigDecimal(0);
        amount = new BigDecimal(0);
    }

    private void reset(){
        supplierList.add(new Supplier());
        supplierList.add(new Supplier());
        supplierList.add(new Supplier());
    }

    public List<Consignment> supply(MoneyScore ms, Storage storage){
        reset();

        visualiser.supplyStart();

        process(supplierList, ms, storage);

        return consignmentList;
    }

    private void process(List<Supplier> supplierList, MoneyScore ms, Storage storage){
        Scanner scanner = new Scanner(System.in);

        do {
            boolean isEmpty = true;
            visualiser.showSuppliers(supplierList);

            for (Supplier s:supplierList) {
                if (!s.getBasket().isEmpty()){
                    isEmpty = false;
                    break;
                }
            }
            if (isEmpty){
                visualiser.noConsignment();
                break;
            }

            visualiser.inputSupplyConsignments();

            add(scanner.nextLine(), supplierList, storage);
            showInfo(ms, storage, scanner);

            if (!visualiser.continueSupply(scanner)){
                break;
            }
            visualiser.showConsignments(consignmentList);
        }
        while (true);
    }

    //  [3-1-1020,2-3-49,2-3-500,1-2-10,2-3-500]

    private void add(String str, List<Supplier> supplierList, Storage storage){
        // стоит ли переносить взаимодействие с другими классами в них самих?
        // storage передавать как аргумент или присваивать в него значения листа
        boolean equals = false;
        for (String s:Utils.regexStr(str,"\\w+-\\w+-\\w+[\\.\\w+]*")) {
            PurchaseUnit pu = Utils.regexPurchaseUnit(s, "\\w+[\\.\\w+]*");

            int num_supplier = pu.getNums().get(0) - 1;
            int num_consignment = pu.getNums().get(1) - 1;
            BigDecimal amount = pu.getAmount();

            Consignment cTemp = supplierList.get(num_supplier).getBasket().get(num_consignment);
            Consignment c = new Consignment(cTemp);

            for (Consignment consignment : this.consignmentList) {

                if (consignment.getVendorCode().equals(c.getVendorCode())){

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
                visualiser.consignmentIsOver(cTemp);
            }

            equals = false;
        }
    }

    protected void remove(Scanner scanner){
        visualiser.remove();
        visualiser.showConsignments(consignmentList);
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

        visualiser.showInfoGeneral(ms, this.cost, this.amount);

        boolean costed = this.cost.compareTo(ms.getMoney()) > 0;
        boolean amounted = this.amount.compareTo(storage.getCapacity()) > 0;
        while (costed || amounted){
            if (costed && amounted){
                visualiser.showInfoCost(ms, cost);
                visualiser.showInfoAmount(amount, storage);
                remove(scanner);
            }
            else if (costed) {
                visualiser.showInfoCost(ms, cost);
                remove(scanner);
            }
            else {
                visualiser.showInfoAmount(amount, storage);
                remove(scanner);
            }
            costed = this.cost.compareTo(ms.getMoney()) > 0;
            amounted = this.amount.compareTo(storage.getCapacity()) > 0;
        }
    }
}
