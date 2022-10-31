package ru.vsu.edu.shlyikov_d_g.products.units;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Unit {
    // TODO перенести в классы PU&TU
//    private Integer num1;
//    private Integer num2;
    private BigDecimal amount = new BigDecimal(0);
    private final String pattern;

    protected Unit(String pattern) {
        this.pattern = pattern;
    }

    protected abstract void setNums(List<Integer> allIntegers);

    public BigDecimal getAmount(){
        return amount;
    }

    public Unit fromString(String str) {
        // TODO getPattern()

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

        // TODO паттерн метод

        setNums(allIntegers);

        this.amount = amount;

        return this;
    }
}
