package ru.vsu.edu.shlyikov_d_g.utils;

import ru.vsu.edu.shlyikov_d_g.products.Consignment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Utils {
    public static List<String> regexStr(String str, String pattern) {
        List<String> allMatches = new ArrayList<>();
        Matcher m = Pattern.compile(pattern)
                .matcher(str);
        while (m.find()) {
            allMatches.add(m.group());
        }
        return allMatches;
    }

    public static List<Integer> regexInt(String str, String pattern) {
        List<Integer> allMatches = new ArrayList<>();
        Matcher m = Pattern.compile(pattern)
                .matcher(str);
        while (m.find()) {
            allMatches.add(Integer.valueOf(m.group()));
        }
        return allMatches;
    }

    public static BigDecimal round(BigDecimal value, int places) {
        return value.setScale(places, RoundingMode.HALF_UP);
    }

    public static int findMinButcherNumber(Map<Integer, Consignment> map){
        List<Consignment> list = map.values().stream().toList();
        if (list.size() > 0) {
            List<Consignment> listTemp = list.subList(0, list.size());
            for (int i = 0; i < listTemp.size() - 1; i++) {
                Consignment c = listTemp.get(i);
                Consignment cNext = listTemp.get(i + 1);
                if (cNext.getBatchNumber() - c.getBatchNumber() > 1) {
                    return c.getBatchNumber() + 1;
                }
            }
            return listTemp.get(listTemp.size() - 1).getBatchNumber() + 1;
        }
        return 0;
    }

    public static Amounts countAmounts(List<Consignment> list){
        Amounts amounts = new Amounts(new BigDecimal(0), new BigDecimal(0));
        for (Consignment c : list){
            amounts.plus(c.getAmount(), c.getShouldBeInTheFridge());
        }
        return amounts;
    }

    public static BigDecimal countAmount(List<Consignment> list){
        BigDecimal amount = new BigDecimal(0);
        for (Consignment c : list){
            amount = amount.add(c.getAmount());
        }
        return amount;
    }

    public static BigDecimal countPrice(List<Consignment> list){
        BigDecimal price = new BigDecimal(0);
        for (Consignment c : list){
            price = price.add(c.getAmount().multiply(c.getUnitPrice()));
        }

        return round(price, 2);
    }

    public static BigDecimal countFreezeAmount(List<Consignment> list){
        BigDecimal amount = new BigDecimal(0);
        for (Consignment c : list){
            if (c.getShouldBeInTheFridge()) {
                amount = amount.add(c.getAmount());
            }
        }
        return amount;
    }

    public static BigDecimal countNonFreezeAmount(List<Consignment> list){
        BigDecimal amount = new BigDecimal(0);
        for (Consignment c : list){
            if (!c.getShouldBeInTheFridge()) {
                amount = amount.add(c.getAmount());
            }
        }
        return amount;
    }
}
