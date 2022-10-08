package ru.vsu.edu.shlyikov_d_g;

import ru.vsu.edu.shlyikov_d_g.products.PurchaseUnit;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
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

    public static PurchaseUnit regexPurchaseUnit(String str, String pattern){
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
            if (i != allMatches.size() - 1){
                allIntegers.add(Integer.parseInt(allMatches.get(i)));
            }
            else if (i == allMatches.size() - 1){
                a = new BigDecimal(Double.parseDouble(allMatches.get(i)));
            }
        }
        pu = new PurchaseUnit(allIntegers, a);
        return pu;
    }

    public static BigDecimal round(BigDecimal value, int places) {
        return value.setScale(places, RoundingMode.HALF_UP);
    }
//        MathContext mathContext = new MathContext(15, RoundingMode.HALF_UP); // для double
//        BigDecimal bigDecimal = new BigDecimal(value, mathContext);
//        bigDecimal = bigDecimal.setScale(2, RoundingMode.DOWN);
//        value = bigDecimal.doubleValue();
//
//        return value;

//        DecimalFormat decimalFormat = new DecimalFormat("#." + "#".repeat(places));
//        return Double.parseDouble(decimalFormat.format(value));

//        if (places < 0) throw new IllegalArgumentException();
//        long factor = (long) Math.pow(10, places);
//        value = value * factor;
//        long tmp = Math.round(value);
//        return (double) tmp / factor;
}
