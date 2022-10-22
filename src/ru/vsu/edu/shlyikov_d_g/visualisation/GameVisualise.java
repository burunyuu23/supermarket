package ru.vsu.edu.shlyikov_d_g.visualisation;

import ru.vsu.edu.shlyikov_d_g.attributes.Amounts;
import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.humans.buyers.Supplier;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.products.units.PurchaseUnit;
import ru.vsu.edu.shlyikov_d_g.products.units.TransferUnit;
import ru.vsu.edu.shlyikov_d_g.products.units.Unit;
import ru.vsu.edu.shlyikov_d_g.rooms.Room;
import ru.vsu.edu.shlyikov_d_g.rooms.Storage;

import java.math.BigDecimal;
import java.util.List;

public interface GameVisualise {

    void helpStart();

    void showProducts(List<Consignment> basket);

    void showSuppliers(List<Supplier> supplierList);

    void noConsignment();

    void inputSupplyConsignments();

    void showInfoGeneral(MoneyScore ms, BigDecimal cost, Amounts amounts, Storage storage);

    void showInfoCost(MoneyScore ms, BigDecimal cost);

    void showInfoAmount(Amounts amounts, Room room, String roomName, Boolean amountedNonFreeze, Boolean amountedFreeze);

    void showConsignments(List<Consignment> consignmentList);

    boolean continueEvent(String name);

    void supplyStart();

    void consignmentIsOver(Consignment c);

    void remove();

    void showRoom(Room room, String roomName);

    void askRoom(Room room, String roomName);

    List<String> getSupply();


    List<PurchaseUnit> getFromRoomPU(String operationName, String pattern);

    List<TransferUnit> getFromRoomTU(String operationName, String pattern);

}
