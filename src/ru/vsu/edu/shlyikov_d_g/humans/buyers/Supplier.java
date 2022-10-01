package ru.vsu.edu.shlyikov_d_g.humans.buyers;

import ru.vsu.edu.shlyikov_d_g.main.Game;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.products.Products;

public class Supplier extends Buyer {
    Products products = new Products();
    // passport data
    public Supplier(){
        setGender();
        setName();
        setYears((int) (Math.random() * 50 + 20));
        this.basket = products.getRandomProducts(75, 3);

        for (Consignment c:this.basket) {
            c.setAmountRandom();
        }
    }

    public void resetProducts(){
        this.basket = products.getRandomProducts(75, 3);

        for (Consignment c:this.basket) {
            c.setAmountRandom();
        }
    }

    public void showProducts(){
        int i = 1;
        for (Consignment c : basket) {
            if (c.getAmount() != 0) {
                System.out.println(i + ". " + c.toStringSupplier());
                i++;
            }
        }
    }
}
