package ru.vsu.edu.shlyikov_d_g.events;

import ru.vsu.edu.shlyikov_d_g.utils.Utils;
import ru.vsu.edu.shlyikov_d_g.utils.Amounts;
import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.humans.buyers.Supplier;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.products.units.SupplyUnit;
import ru.vsu.edu.shlyikov_d_g.rooms.Storage;
import ru.vsu.edu.shlyikov_d_g.main.visualisation.GameVisualise;

import java.math.BigDecimal;
import java.util.*;

public class Supply {
    private List<Supplier> supplierList;
    private List<Consignment> consignmentList;
    private BigDecimal cost;
    private Amounts amounts;
    private Storage storage;
    private MoneyScore moneyScore;
    private int supplierQuantity;

    private GameVisualise visualiser;

    public Supply(GameVisualise visualiser, MoneyScore moneyScore, Storage storage, int supplierQuantity){
        this.visualiser = visualiser;
        this.moneyScore = moneyScore;
        this.storage = storage;
        this.supplierQuantity = supplierQuantity;
    }

    public void reset(){
        supplierList = new ArrayList<>();
        consignmentList = new ArrayList<>();
        amounts = new Amounts(storage.getAmounts().getFreeze(), storage.getAmounts().getNonFreeze());
        cost = new BigDecimal(0);
        for (int i = 0; i < supplierQuantity; i++) {
            supplierList.add(new Supplier());
        }
    }

    public List<Consignment> getElements(){
        return consignmentList;
    }

    public List<Supplier> getSupplierList() {
        return supplierList;
    }

    public BigDecimal supply(){
        reset();
        visualiser.supplyStart();
        process();

        return Utils.round(cost, 2);
    }

     public BigDecimal supplyMoney(){
         return Utils.round(cost, 2);
     }

    private void process(){
        do {
            helpInput();
            showInfoToBuy();
            if (isEmpty()){
                break;
            }
            add(visualiser.getFrom("закупить", new SupplyUnit("\\w+-\\w+-\\w+[\\.\\w+]*")));
            showInfo();
            showInfoWhatGetting();
            while (visualiser.continueEvent("Удалить товары из корзинки")) {
                visualiser.showInfoGeneral(moneyScore, cost, amounts, storage);
                remove(visualiser.getFrom("удалить", new SupplyUnit("\\w+-\\w+[\\.\\w+]*")));
            }
        }
        while (continueEvent());
    }

    private void showInfoToBuy(){
        visualiser.showSuppliers(supplierList);
    }

    private boolean isEmpty(){
        boolean isEmpty = true;
        for (Supplier supplier:supplierList) {
            if (!supplier.getBasket().isEmpty()){
                isEmpty = false;
                break;
            }
        }
        if (isEmpty){
            visualiser.noConsignment();
            return true;
        }
        return false;
    }

    private boolean continueEvent(){
        return visualiser.continueEvent("Продолжить закупку");
    }

    private void helpInput(){
        visualiser.inputSupplyConsignments();
    }

    //  [3-1-1020,2-3-49,2-3-500,1-2-10,2-3-500]
    public void add(List<String> supplyGetFrom){
        for (String supplyInput : supplyGetFrom) {

            SupplyUnit supplyUnit = new SupplyUnit("\\w+[\\.\\w+]*");
            SupplyUnit pu = (SupplyUnit) supplyUnit.fromString(supplyInput);

            Consignment consignmentFromList = supplierList.get(pu.getNumSupplier() - 1).getBasket().get(pu.getNumConsignment() - 1);
            Consignment consignmentClone = consignmentFromList.clone();

            BigDecimal amount;
            if (consignmentFromList.checkKG()){
                amount = pu.getAmount();
            }
            else {
                amount = Utils.round(pu.getAmount(),0);
            }

            boolean consignmentEqualsPurchaseUnit = false;

            for (Consignment consignment : consignmentList) {

                if (consignment.isEqualsInVendorCode(consignmentClone)){
                    BigDecimal minusAmount = consignmentFromList.minusAmount(amount);
                    consignment.plusAmount(minusAmount);
                    refreshEvent(minusAmount, consignment);

                    consignmentEqualsPurchaseUnit = true;
                    break;
                }
            }

            if (!consignmentEqualsPurchaseUnit) {
                BigDecimal minusAmount = consignmentFromList.minusAmount(amount);
                if (minusAmount.compareTo(BigDecimal.valueOf(0)) != 0) {
                    consignmentClone.setAmount(minusAmount);
                    refreshEvent(minusAmount, consignmentClone);
                    consignmentList.add(consignmentClone);
                }
            }

            if (consignmentFromList.getAmount().compareTo(BigDecimal.valueOf(0)) == 0){
                visualiser.consignmentIsOver(consignmentFromList);
            }
        }
    }

    private void refreshEvent(BigDecimal minusAmount, Consignment consignment){
        amounts.plus(minusAmount, consignment.getShouldBeInTheFridge());
        cost = Utils.round(cost.add(consignment.getUnitPrice().multiply(minusAmount)),2);
    }

    public void remove(List<String> list){
        visualiser.remove();

        showInfoToBuy();
        visualiser.showConsignments(consignmentList);

        List<SupplyUnit> puList = SupplyUnit.toSupplyUnitList(list);

        visualiser.showInfoGeneral(moneyScore, cost, amounts, storage);

        puList.sort(Comparator.comparing(SupplyUnit::getNumConsignment).reversed());

        for (SupplyUnit pu:puList) {
            int index = pu.getNumConsignment();
            Consignment c = consignmentList.get(index - 1);

            BigDecimal amount;
            if (c.checkKG()){
                amount = pu.getAmount();
            }
            else {
                amount = Utils.round(pu.getAmount(),0);
            }

            if (amount.compareTo(c.getAmount()) > 0) {
                consignmentList.remove(index - 1);
            }

            BigDecimal minusAmount = c.minusAmount(amount);
            refreshEvent(minusAmount.multiply(BigDecimal.valueOf(-1)), c);
        }
    }

    private void showInfoWhatGetting(){
        visualiser.showConsignments(consignmentList);
    }

    public boolean isNormalize(){
        return cost.compareTo(moneyScore.getMoney()) > 0 ||
                amounts.getNonFreeze().compareTo(storage.getNonFridgeCapacity()) > 0 ||
                amounts.getFreeze().compareTo(storage.getFridgeCapacity()) > 0;
    }

    private void showInfo(){
        if (isNormalize()){
            while (isNormalize()){
                remove(visualiser.getFrom("удалить", new SupplyUnit("\\w+-\\w+[\\.\\w+]*")));
            }
        }
            else{
            visualiser.showInfoGeneral(moneyScore, cost, amounts, storage);
        }
    }
}
