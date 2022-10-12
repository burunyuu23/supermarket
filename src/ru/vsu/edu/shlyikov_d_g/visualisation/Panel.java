package ru.vsu.edu.shlyikov_d_g.visualisation;

import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.humans.buyers.Supplier;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.rooms.Storage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Panel implements GameVisualise{
    @Override
    public void showProducts(List<Consignment> basket) {

    }

    @Override
    public void showSuppliers(List<Supplier> supplierList) {

    }

    @Override
    public void noConsignment() {

    }

    @Override
    public void inputSupplyConsignments(){

    }

    @Override
    public void showInfoGeneral(MoneyScore ms, BigDecimal cost, BigDecimal amount) {

    }

    @Override
    public void showInfoCost(MoneyScore ms, BigDecimal cost) {

    }

    @Override
    public void showInfoAmount(BigDecimal amount, Storage storage) {

    }

    @Override
    public void showConsignments(List<Consignment> consignmentList) {

    }

    @Override
    public boolean continueSupply(Scanner scanner) {
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
}
