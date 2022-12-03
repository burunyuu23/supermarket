package ru.vsu.edu.shlyikov_d_g.events;

import ru.vsu.edu.shlyikov_d_g.humans.buyers.Customer;
import ru.vsu.edu.shlyikov_d_g.products.Cheque;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.rooms.Room;
import ru.vsu.edu.shlyikov_d_g.rooms.Store;
import ru.vsu.edu.shlyikov_d_g.visualisation.Console;
import ru.vsu.edu.shlyikov_d_g.visualisation.GameVisualise;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Purchase {
    private final Store store;
    private Customer customer;
    private List<Cheque> chequeList = new ArrayList<>();
    private GameVisualise gameVisualise;

    public Purchase(Store store, GameVisualise gameVisualise) {
        this.store = store;
        this.gameVisualise = gameVisualise;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    public BigDecimal purchase(){
        setCustomer(new Customer());
        customer.chooseProducts(store);

        BigDecimal price = new BigDecimal(0);
        for (Consignment c : customer.getBasket()) {
            price = price.add(gameVisualise.purchase(customer, c, store)); // TODO добавить скидки
        }

        Cheque cheque = new Cheque(customer.getBasket());

        if (!price.equals(new BigDecimal(0))){
        store.removeElements(customer.getBasket());
        chequeList.add(cheque);

        gameVisualise.showCheque(cheque, price);
//        gameVisualise.showRoom(store);
        }
        return cheque.getGeneralAmount();
    }
}
