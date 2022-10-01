package ru.vsu.edu.shlyikov_d_g.humans;

import ru.vsu.edu.shlyikov_d_g.humans.enums.FemaleNames;
import ru.vsu.edu.shlyikov_d_g.humans.enums.MaleNames;
import ru.vsu.edu.shlyikov_d_g.humans.enums.Genders;

import java.awt.*;

public abstract class Human {
    // passport data
    private String name = null;
    private int years = 0;
    private String gender = null;
    private Image photo = null;

    public void setGender(){
        this.gender = Genders.randomGender().toString();
    }

    public void setGender(String gender){
        if(Genders.isGender(gender)){
            this.gender = gender;
        }
        else{
            System.out.println("Неправильно установлен пол!");
        }
    }

    public void setName(){
        this.name = gender.equals("Муж") ? MaleNames.randomMaleName().toString() : FemaleNames.randomFemaleName().toString();
    }

    public void setYears(int years){
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
}
