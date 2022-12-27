package ru.vsu.edu.shlyikov_d_g.main.visualisation;

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

public interface GameVisualise extends Game {

    void start();

    void showProducts(List<Consignment> basket);

    void showSuppliers(List<Supplier> supplierList);

    void noConsignment();

    void inputSupplyConsignments();

    void showInfoGeneral(MoneyScore ms, BigDecimal cost, Amounts amounts, Storage storage);

    void showInfoCost(MoneyScore ms, BigDecimal cost);

    void showInfoAmount(Amounts amounts, Room room, String roomName, Boolean normalize);

    void showConsignments(List<Consignment> consignmentList);

    boolean continueEvent(String name);

    void supplyStart();

    void consignmentIsOver(Consignment c);

    void remove();

    void removeStore();

    void showRoom(Room room);

    void askRoom(Room room, String roomName);

    void helpPurchase(); // TODO в графическом сделать пробивание товара как провести мышкой по штрих-коду товара

    void showCheque(Cheque cheque, BigDecimal price);

    BigDecimal purchase(Customer customer, Consignment consignment, Store store);

    List<String> getFrom(String operationName, Unit u, Room room);

    List<String> getFrom(String operationName, Unit u);

    void endOfTheDay(int dayPassed, MoneyScore receive, MoneyScore spending, MoneyScore allMoney);

    void startOfTheDay(int dayPassed);
}
