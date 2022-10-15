package ru.vsu.edu.shlyikov_d_g.visualisation;

import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.humans.buyers.Supplier;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.products.PurchaseUnit;
import ru.vsu.edu.shlyikov_d_g.rooms.Room;
import ru.vsu.edu.shlyikov_d_g.rooms.Storage;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public interface GameVisualise {

    void helpStart();

    void showProducts(List<Consignment> basket);

    void showSuppliers(List<Supplier> supplierList);

    void noConsignment();

    void inputSupplyConsignments();

    void showInfoGeneral(MoneyScore ms, BigDecimal cost, BigDecimal amount);

    void showInfoCost(MoneyScore ms, BigDecimal cost);

    void showInfoAmount(BigDecimal amount, Storage storage);

    void showConsignments(List<Consignment> consignmentList);

    boolean continueEvent(String name);

    void supplyStart();

    void consignmentIsOver(Consignment c);

    void remove();

    void toCapacityError(BigDecimal size, Room room);

    void showRoom(Room room, String roomName);

    void askRoom(Room room, String roomName);

    List<String> getSupply();

    List<PurchaseUnit> getFromRoom(String nameRoom);

}
