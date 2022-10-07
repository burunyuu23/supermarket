package ru.vsu.edu.shlyikov_d_g.products;

import java.math.BigDecimal;
import java.util.*;

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
                "Молоко 'ЭкоНива' 1л 3.2%", "шт",68.99,
                2,false,true);
        product_range.put(a.getVendor_code(), a);

        a = new Consignment("MIL-CHE-GAU",
                "Сыр 'Гауда'", "кг",479.99,
                4,false,true);
        product_range.put(a.getVendor_code(), a);
        a = new Consignment("MIL-CHE-MOZ",
                "Сыр 'Моцарелла'", "кг",674.99,
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

    public List<Consignment> getRandomProducts(BigDecimal percentage){
        Random random = new Random();
        List<Consignment> s = new ArrayList<>();
        for (String key:product_range.keySet()) {
            if(percentage.compareTo(BigDecimal.valueOf(random.nextInt(100))) > 0){
                s.add(product_range.get(key));
            }
        }
        return s;
    }

    public List<Consignment> getLimitedRandomProducts(int a){
        List<Consignment> s = new ArrayList<>();
            for (int i = 0; i < a; i++) {
                    Random       random    = new Random();
                    List<String> keys      = new ArrayList<>(product_range.keySet());
                    String       randomKey = keys.get(random.nextInt(keys.size()));
                if (!s.contains(product_range.get(randomKey))) {
                    Consignment value = product_range.get(randomKey);
                    s.add(value);
                }
                else{
                    i--;
                }
                }
        return s;
    }
}
