package ru.vsu.edu.shlyikov_d_g.shop_baskets;

import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.humans.buyers.Supplier;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.products.PurchaseUnit;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopBasket {
    protected final List<Consignment> lst = new ArrayList<>();
    protected double cost;

    // главная функция обработки корзины
    public void process(List<Supplier> supplierList, MoneyScore ms) {}

    // функция добавление в корзину
    protected void add(String str, List<Supplier> supplierList){}

    // функция удаления из корзины
    protected void remove(Scanner scanner){}

    public List<Consignment> getBasket(){
        return lst;
    }

    protected List<String> regexStr(String str, String pattern) {
        List<String> allMatches = new ArrayList<>();
        Matcher m = Pattern.compile(pattern)
                .matcher(str);
        while (m.find()) {
            allMatches.add(m.group());
        }
        return allMatches;
    }

    protected List<Integer> regexInt(String str, String pattern) {
        List<Integer> allMatches = new ArrayList<>();
        Matcher m = Pattern.compile(pattern)
                .matcher(str);
        while (m.find()) {
            allMatches.add(Integer.valueOf(m.group()));
        }
        return allMatches;
    }

    protected PurchaseUnit regexPurchaseUnit(String str, String pattern){
        PurchaseUnit pu;
        List<String> allMatches = new ArrayList<>();
        Matcher m = Pattern.compile(pattern)
                .matcher(str);
        while (m.find()) {
            allMatches.add(m.group());
        }
        pu = new PurchaseUnit(Integer.parseInt(allMatches.get(0)),
                Integer.parseInt(allMatches.get(1)), Double.parseDouble(allMatches.get(2)));
        return pu;
    }
}
