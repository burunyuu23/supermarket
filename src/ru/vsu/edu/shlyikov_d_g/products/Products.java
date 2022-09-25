package ru.vsu.edu.shlyikov_d_g.products;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Products {
    private Map<String, Consignment> product_range = new HashMap<>();
    private Consignment a;

    //    String vendor_code,
    //    String product_name,
    //    String measure,
    //    double unit_price,
    //    int expiration_days,
    //    boolean age_restricted,
    //    boolean should_be_in_the_fridge

    public Products(){
        a = new Consignment("FRU-APP-CHA",
                "Яблоко 'Чемпион'", "кг",116.99,
                6,false,false);
        product_range.put(a.getVendor_code(), a);
        a = new Consignment("FRU-APP-SEA",
                "Яблоко 'Сезонные'", "кг",74.99,
                6,false,false);
        product_range.put(a.getVendor_code(), a);
        a = new Consignment("FRU-APP-GOL",
                "Яблоко 'Гольден'", "кг",116.99,
                6,false,false);
        product_range.put(a.getVendor_code(), a);
        a = new Consignment("FRU-APP-GRA",
                "Яблоко 'Гренни Смит'", "кг",99.99,
                6,false,false);
        product_range.put(a.getVendor_code(), a);

        a = new Consignment("FRU-BAN-WEI",
                "Бананы 'Весовые'", "кг",61.99,
                3,false,false);
        product_range.put(a.getVendor_code(), a);
        a = new Consignment("FRU-BAN-GRE",
                "Бананы 'Зелёные'", "кг",74.99,
                3,false,false);
        product_range.put(a.getVendor_code(), a);
        a = new Consignment("FRU-BAN-RED",
                "Бананы 'Красные'", "кг",87.99,
                3,false,false);
        product_range.put(a.getVendor_code(), a);
        a = new Consignment("FRU-BAN-MIN",
                "Бананы 'Мини'", "кг",141.99,
                3,false,false);
        product_range.put(a.getVendor_code(), a);


        a = new Consignment("MIL-MIL-PRO",
                "Молоко 'Простоквашино' 970мл 1.5%", "шт",91.99,
                2,false,true);
        product_range.put(a.getVendor_code(), a);
        a = new Consignment("MIL-MIL-DOM",
                "Молоко 'Домик в деревне' 950мл 3.2%", "шт",70.99,
                2,false,true);
        product_range.put(a.getVendor_code(), a);
        a = new Consignment("MIL-MIL-ECO",
                "Молоко 'ЭкоНива' 1л 3.2%", "кг",68.99,
                2,false,true);
        product_range.put(a.getVendor_code(), a);

        a = new Consignment("MIL-CHE-GAU",
                "Сыр 'Гауда'", "шт",479.99,
                4,false,true);
        product_range.put(a.getVendor_code(), a);
        a = new Consignment("MIL-CHE-MOZ",
                "Сыр 'Моцарелла'", "шт",674.99,
                4,false,true);
        product_range.put(a.getVendor_code(), a);
        a = new Consignment("MIL-CHE-MAA",
                "Сыр 'Маасдам'", "кг",819.99,
                4,false,true);
        product_range.put(a.getVendor_code(), a);
        a = new Consignment("MIL-CHE-SUL",
                "Сыр 'Сулугуни'", "кг",659.99,
                4,false,true);
        product_range.put(a.getVendor_code(), a);
        a = new Consignment("MIL-CHE-CHE",
                "Сыр 'Чеддер'", "кг",749.99,
                4,false,true);
        product_range.put(a.getVendor_code(), a);
    }

    public Consignment getProduct(String vendor_code){
        return product_range.get(vendor_code);
    }

    public Set<Consignment> getRandomProducts(int percentage){
        Set<Consignment> s = new HashSet<>();
        for (String key:product_range.keySet()) {
            if(Math.random() * 100 < percentage){
                s.add(product_range.get(key));
            }
        }
        return s;
    }
}
