package ru.vsu.edu.shlyikov_d_g.humans;

import ru.vsu.edu.shlyikov_d_g.humans.enums.FemaleNames;
import ru.vsu.edu.shlyikov_d_g.humans.enums.MaleNames;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.humans.enums.Genders;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public abstract class Human {
    // passport data
    String name = null;
    int years = 0;
    String gender = null;
    Image photo = null;

    MoneyScore money = null;
    Set<Consignment> basket = new HashSet<>();

    void setGender(){
        this.gender = Genders.randomGender().toString();
    }

    void setGender(String gender){
        if(Genders.isGender(gender)){
            this.gender = gender;
        }
        else{
            System.out.println("Неправильно установлен пол!");
        }
    }

    void setName(){
        this.name = gender.equals("Муж") ? MaleNames.randomMaleName().toString() : FemaleNames.randomFemaleName().toString();
    }

    void setYears(int years){
        if (years <= 0 || years >= 120){
            System.out.println("Неправильно установлен возраст!");
        }
        else{
            this.years = years;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMoney(int money) {
        this.money = new MoneyScore(money);
    }

    public String getName() {
        return name;
    }

    public int getYears() {
        return years;
    }

    public String getGender() {
        return gender;
    }

    public Image getPhoto() {
        return photo;
    }

    public MoneyScore getMoney() {
        return money;
    }

    public Set<Consignment> getBasket() {
        return basket;
    }
}
