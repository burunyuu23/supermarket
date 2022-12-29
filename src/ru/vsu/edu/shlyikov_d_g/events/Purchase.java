package ru.vsu.edu.shlyikov_d_g.events;

import ru.vsu.edu.shlyikov_d_g.humans.buyers.Customer;
import ru.vsu.edu.shlyikov_d_g.products.Cheque;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.rooms.Store;
import ru.vsu.edu.shlyikov_d_g.main.visualisation.Console;
import ru.vsu.edu.shlyikov_d_g.main.visualisation.GameVisualise;

import java.math.BigDecimal;
import java.util.List;

public class Purchase {
    private Store store;
    private Customer customer;
    private Cheque cheque;
    private GameVisualise gameVisualise;
    private BigDecimal price = new BigDecimal(0);

    public Purchase(Store store, GameVisualise gameVisualise) {
        this.store = store;
        this.gameVisualise = gameVisualise;
        newCustomer();
    }

    public Purchase(List<Consignment> basket) {
        setCustomer(new Customer());
        customer.setBasket(basket);
    }

    public Purchase(Customer customer, List<Consignment> basket) {
        setCustomer(customer);
        customer.setBasket(basket);
    }

    public Purchase(Store store, Customer customer) {
        this.store = store;
        this.customer = customer;
    }


    public Cheque getCheque() {
        return cheque;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void newCustomer(){
        setCustomer(new Customer());
        customer.chooseProducts(store);
    }

    public BigDecimal purchase(List<Consignment> basket){
        newCustomer();

        for (Consignment c : basket) {
            price = countPrice(gameVisualise.purchase(customer, c, store));
        }

        createCheque();
        showCheque();
        removeFromStore();

        return cheque.getGeneralAmount();
    }

    public BigDecimal purchase(){
        purchase(customer.getBasket());

        return cheque.getGeneralAmount();
    }

    public void removeFromStore(){
        store.removeElements(customer.getBasket());
    }

    public void createCheque(){
        cheque = new Cheque(customer.getBasket());
    }

    public void showCheque(){
        gameVisualise.showCheque(cheque, price);
    }

    public BigDecimal countPrice(BigDecimal count){
        BigDecimal price = new BigDecimal(0);
        return price.add(count);
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "store=" + store +
                ", customer=" + customer +
                ", cheque=" + cheque +
                ", gameVisualise=" + gameVisualise +
                ", price=" + price +
                '}';
    }
}
