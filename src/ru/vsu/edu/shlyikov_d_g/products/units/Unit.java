package ru.vsu.edu.shlyikov_d_g.products.units;

import ru.vsu.edu.shlyikov_d_g.Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Unit {
    private Integer num1;
    private Integer num2;
    private BigDecimal amount;

    public Unit(int num1, int num2, BigDecimal amount) {
        this.num1 = num1;
        this.num2 = num2;
        this.amount = amount;
    }

    public Unit(int num1, int num2, double amount) {
        this.num1 = num1;
        this.num2 = num2;
        this.amount = BigDecimal.valueOf(amount);
    }

    @Override
    public String toString() {
        return "Unit{" +
                "num1=" + num1 +
                ", num2=" + num2 +
                ", amount=" + amount +
                '}';
    }

    public int getNum1(){
        return num1;
    }

    public int getNum2(){
        return num2;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void regexUnit(String str, String pattern) {
        List<String> allMatches = new ArrayList<>();
        List<Integer> allIntegers = new ArrayList<>();
        BigDecimal amount = new BigDecimal(0);

        Matcher m = Pattern.compile(pattern)
                .matcher(str);

        while (m.find()) {
            allMatches.add(m.group());
        }

        for (int i = 0; i < allMatches.size(); i++) {
            if (i != allMatches.size() - 1) {
                allIntegers.add(Integer.parseInt(allMatches.get(i)));
            } else if (i == allMatches.size() - 1) {
                amount = BigDecimal.valueOf(Double.parseDouble(allMatches.get(i)));
            }
        }

        if (allIntegers.size() == 1) {
            num1 = -1;
            num2 = allIntegers.get(0);
        }
        else{
            num1 = allIntegers.get(0);
            num2 = allIntegers.get(1);
        }

        this.amount = amount;
    }


}
