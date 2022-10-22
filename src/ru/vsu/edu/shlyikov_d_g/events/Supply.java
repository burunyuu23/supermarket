package ru.vsu.edu.shlyikov_d_g.events;

import ru.vsu.edu.shlyikov_d_g.Utils;
import ru.vsu.edu.shlyikov_d_g.attributes.Amounts;
import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.humans.buyers.Supplier;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.products.units.PurchaseUnit;
import ru.vsu.edu.shlyikov_d_g.rooms.Storage;
import ru.vsu.edu.shlyikov_d_g.visualisation.GameVisualise;

import java.math.BigDecimal;
import java.util.*;

public class Supply {
    private List<Supplier> supplierList;
    private List<Consignment> consignmentList;
    private BigDecimal cost;
    private Amounts amounts;
    private Storage storage;
    private MoneyScore ms;
    private int supplierQuantity;

    private GameVisualise visualiser;

    public Supply(GameVisualise visualiser, MoneyScore ms, Storage storage, int supplierQuantity){
        this.visualiser = visualiser;
        this.ms = ms;
        this.storage = storage;
        this.supplierQuantity = supplierQuantity;
    }

    private void reset(){
        supplierList = new ArrayList<>();
        consignmentList = new ArrayList<>();
        amounts = new Amounts(new BigDecimal(0), new BigDecimal(0));
        cost = new BigDecimal(0);
        for (int i = 0; i < supplierQuantity; i++) {
            supplierList.add(new Supplier());
        }
    }

    public List<Consignment> supply(){
        reset();
        visualiser.supplyStart();
        process();

        return consignmentList;
    }

    private void process(){
        do {
            boolean isEmpty = true;
            visualiser.showSuppliers(supplierList);

            for (Supplier supplier:supplierList) {
                if (!supplier.getBasket().isEmpty()){
                    isEmpty = false;
                    break;
                }
            }
            if (isEmpty){
                visualiser.noConsignment();
                break;
            }

            visualiser.inputSupplyConsignments();

            add();
            showInfo();

            if (!visualiser.continueEvent("закупку")){
                break;
            }
            visualiser.showConsignments(consignmentList);
        }
        while (true);
    }

    //  [3-1-1020,2-3-49,2-3-500,1-2-10,2-3-500]\
    private void add(){
        for (String supplyInput: visualiser.getSupply()) { // заменить

            PurchaseUnit purchaseUnit = new PurchaseUnit(0, 0, BigDecimal.valueOf(0));
            purchaseUnit.regexUnit(supplyInput, "\\w+[\\.\\w+]*");

            Consignment consignmentFromList = supplierList.get(purchaseUnit.getNumSupplier() - 1).getBasket().get(purchaseUnit.getNumConsignment() - 1);
            Consignment consignmentClone = new Consignment(consignmentFromList);

            boolean consignmentEqualsPurchaseUnit = false;

            for (Consignment consignment : consignmentList) {

                if (consignment.isEqualsInVendorCode(consignmentClone)){

                    BigDecimal minusAmount = consignmentFromList.minusAmount(purchaseUnit.getAmount());
                    consignment.plusAmount(minusAmount);
                    refreshEvent(minusAmount, consignment);

                    consignmentEqualsPurchaseUnit = true;
                    break;
                }
            }

            if (!consignmentEqualsPurchaseUnit) {
                BigDecimal minusAmount = consignmentFromList.minusAmount(purchaseUnit.getAmount());
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

    private void remove(){
        visualiser.remove();

        visualiser.showConsignments(consignmentList);

        List<PurchaseUnit> puList = new ArrayList<>(visualiser.getFromRoomPU("удалить", "\\w+-\\w+[\\.\\w+]*"));

        puList.sort(Comparator.comparing(PurchaseUnit::getNumConsignment).reversed());

        for (PurchaseUnit pu:puList) {
            int index = pu.getNumConsignment();
            Consignment c = consignmentList.get(index - 1);

            if (pu.getAmount().compareTo(c.getAmount()) > 0) {
                consignmentList.remove(index - 1);
            }

            BigDecimal minusAmount = c.minusAmount(pu.getAmount());
            refreshEvent(minusAmount.multiply(BigDecimal.valueOf(-1)), c);
        }
    }

    private void showInfo(){

        visualiser.showInfoGeneral(ms, cost, amounts, storage);

        while (cost.compareTo(ms.getMoney()) > 0 ||
                amounts.getNonFreeze().compareTo(storage.getCapacity()) > 0 || amounts.getFreeze().compareTo(storage.getFridgeCapacity()) > 0){
            visualiser.showInfoCost(ms, cost);
            visualiser.showInfoAmount(amounts, storage, "на складе",
                    amounts.getNonFreeze().compareTo(storage.getCapacity()) > 0,
                    amounts.getFreeze().compareTo(storage.getFridgeCapacity()) > 0);
            remove();
        }
    }
}
