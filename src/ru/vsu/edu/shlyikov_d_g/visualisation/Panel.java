package ru.vsu.edu.shlyikov_d_g.visualisation;

import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.humans.buyers.Supplier;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.products.PurchaseUnit;
import ru.vsu.edu.shlyikov_d_g.rooms.Room;
import ru.vsu.edu.shlyikov_d_g.rooms.Storage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

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
    public void toCapacityError(BigDecimal size, Room room) {

    }

    @Override
    public void showRoom(Room room, String roomName) {

    }

    @Override
    public void askRoom(Room room, String roomName) {

    }

    @Override
    public List<String> getSupply() {
        return null;
    }

    @Override
    public List<PurchaseUnit> getFromRoom(String nameRoom) {
        return null;
    }
}
