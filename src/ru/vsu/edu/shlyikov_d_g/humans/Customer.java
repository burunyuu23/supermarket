package ru.vsu.edu.shlyikov_d_g.humans;

import ru.vsu.edu.shlyikov_d_g.humans.enums.FemaleNames;
import ru.vsu.edu.shlyikov_d_g.humans.enums.Genders;
import ru.vsu.edu.shlyikov_d_g.humans.enums.MaleNames;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.products.Products;

import java.awt.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Customer extends Human {
    // passport data
    private String name;
    private int years;
    private String gender;
    private Image photo;

    private MoneyScore money;
    private Set<Consignment> basket;

    public Customer(Products products){
        setGender();
        setName();
        setYears((int) (Math.random() * 100 + 5));
        this.money = MoneyScore.random();
        this.basket = products.getRandomProducts((int) (this.money.getMoney() / 75));
    }

}
