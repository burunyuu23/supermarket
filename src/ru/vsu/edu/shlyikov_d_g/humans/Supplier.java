package ru.vsu.edu.shlyikov_d_g.humans;

import ru.vsu.edu.shlyikov_d_g.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.humans.enums.FemaleNames;
import ru.vsu.edu.shlyikov_d_g.humans.enums.Genders;
import ru.vsu.edu.shlyikov_d_g.humans.enums.MaleNames;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.products.Products;

import java.awt.*;
import java.util.Set;

public class Supplier extends Human {
    // passport data
    private String name;
    private int years;
    private String gender;
    private Image photo;
    private Products products;

    private Set<Consignment> product_range;

    public Supplier(Products products){
        setGender();
        setName();
        setYears((int) (Math.random() * 50 + 20));
        this.products = products;
        this.product_range = this.products.getRandomProducts(75);

        for (Consignment c:this.product_range) {
            c.setAmount(Math.random() * 500 + 399);
        }
    }

    public void resetProducts(){
        this.product_range = this.products.getRandomProducts(75);

        for (Consignment c:this.product_range) {
            c.setAmount(Math.random() * 500 + 399);
        }
    }

    public void showProducts(){
        for (Consignment c:product_range) {
            System.out.println(c.toStringSupplier());
        }
    }
}
