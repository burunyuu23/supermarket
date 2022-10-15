package ru.vsu.edu.shlyikov_d_g;

import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.products.PurchaseUnit;

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

    public static PurchaseUnit regexPurchaseUnit(String str, String pattern) {
        PurchaseUnit pu;
        List<String> allMatches = new ArrayList<>();
        List<Integer> allIntegers = new ArrayList<>();
        BigDecimal a = new BigDecimal(0);
        Matcher m = Pattern.compile(pattern)
                .matcher(str);
        while (m.find()) {
            allMatches.add(m.group());
        }
        for (int i = 0; i < allMatches.size(); i++) {
            if (i != allMatches.size() - 1) {
                allIntegers.add(Integer.parseInt(allMatches.get(i)));
            } else if (i == allMatches.size() - 1) {
                a = new BigDecimal(Double.parseDouble(allMatches.get(i)));
            }
        }
        pu = new PurchaseUnit(allIntegers, a);
        return pu;
    }

    public static BigDecimal round(BigDecimal value, int places) {
        return value.setScale(places, RoundingMode.HALF_UP);
    }

    public static int findMinButcherNumber(Map<Integer, Consignment> map){
        List<Consignment> list = map.values().stream().toList();
        if (list.size() > 0) {
            List<Consignment> listTemp = list.subList(0, list.size() - 1);
            listTemp.sort(Comparator.comparing(Consignment::getBatchNumber));
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
}
