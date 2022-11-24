package ru.vsu.edu.shlyikov_d_g.products.units;

import ru.vsu.edu.shlyikov_d_g.utils.Amounts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Unit {
    private BigDecimal amount = new BigDecimal(0);
    private String pattern;

    protected Unit(String pattern) {
        this.pattern = pattern;
    }

    protected abstract void setNums(List<Integer> allIntegers);

    public BigDecimal getAmount(){
        return amount;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Unit fromString(String str){
        Answer answer = parsing(str);
        setNums(answer.getAllIntegers());
        setAmount(answer.getAmount());
        return this;
    }

    protected Answer parsing(String str) {
        List<String> allMatches = new ArrayList<>();
        List<Integer> allIntegers = new ArrayList<>();
        BigDecimal amount = new BigDecimal(0);

        Matcher m = Pattern.compile(getPattern())
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

        return new Answer(allIntegers, amount);
    }

    protected class Answer{
        List<Integer> allIntegers;
        BigDecimal amount;

        public Answer(List<Integer> allIntegers, BigDecimal amount) {
            this.allIntegers = allIntegers;
            this.amount = amount;
        }

        public List<Integer> getAllIntegers() {
            return allIntegers;
        }

        public void setAllIntegers(List<Integer> allIntegers) {
            this.allIntegers = allIntegers;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
    }

}
