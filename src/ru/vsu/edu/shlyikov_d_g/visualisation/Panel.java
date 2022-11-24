package ru.vsu.edu.shlyikov_d_g.visualisation;

import ru.vsu.edu.shlyikov_d_g.humans.buyers.Customer;
import ru.vsu.edu.shlyikov_d_g.products.Cheque;
import ru.vsu.edu.shlyikov_d_g.rooms.Store;
import ru.vsu.edu.shlyikov_d_g.utils.Amounts;
import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.humans.buyers.Supplier;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.products.units.Unit;
import ru.vsu.edu.shlyikov_d_g.rooms.Room;
import ru.vsu.edu.shlyikov_d_g.rooms.Storage;

import java.math.BigDecimal;
import java.util.List;

public class Panel implements GameVisualise{

    @Override
    public void helpStart(){

    };

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
    public void showRoom(Room room) {

    }

    @Override
    public void askRoom(Room room, String roomName) {

    }

    @Override
    public void helpPurchase() {

    }

    @Override
    public void showCheque(Cheque cheque, BigDecimal price) {

    }

    @Override
    public BigDecimal purchase(Consignment consignment, Store store)  {
        return null;
    }

    @Override
    public List<String> getFromRoom(String operationName, Unit u) {
        return null;
    }
}
