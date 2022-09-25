package ru.vsu.edu.shlyikov_d_g;

public class MoneyScore {
    private double money;

    public MoneyScore(int money){
        this.money = money;
    }

    public static MoneyScore random(){
        return new MoneyScore((int) (Math.random() * 4900 + 100));
    }

    public double getMoney(){
        return money;
    }

    public void spend(double spending){
        money -= spending;
    }

    public void receive(double spending){
        money += spending;
    }
}
