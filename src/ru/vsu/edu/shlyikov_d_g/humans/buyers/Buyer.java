package ru.vsu.edu.shlyikov_d_g.humans.buyers;

import ru.vsu.edu.shlyikov_d_g.humans.Human;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Buyer extends Human {
    // passport data
    List<Consignment> basket = new ArrayList<>();

    public List<Consignment> getBasket() {
        return basket;
    }

    public void setBasket(List<Consignment> basket) {
        this.basket = basket;
    }
}
