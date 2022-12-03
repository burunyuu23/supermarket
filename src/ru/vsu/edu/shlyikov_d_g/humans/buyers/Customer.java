package ru.vsu.edu.shlyikov_d_g.humans.buyers;

import ru.vsu.edu.shlyikov_d_g.attributes.MoneyScore;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.rooms.Room;
import ru.vsu.edu.shlyikov_d_g.rooms.Store;
import ru.vsu.edu.shlyikov_d_g.utils.Utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Customer extends Buyer {
    private MoneyScore money;

    public MoneyScore getMoney() {
        return money;
    }

    public Customer(){
        setGender();
        setName();
        setYears((int) (Math.random() * 100 + 5));
        money = new MoneyScore();
    }

    public void chooseProducts(Room store) {
        Random randomAmount = new Random();
        Random randomChance = new Random();
        for (String key : store.getElements().keySet()) {
            for (Integer batch : store.getElements().get(key).keySet()) {

                Consignment consignment = store.getElements().get(key).get(batch);
                BigDecimal amount;
                if (consignment.getMeasure().equals("кг")){
                    amount = BigDecimal.valueOf(randomAmount.nextDouble() * (int) Math.ceil(Math.log10(consignment.getAmount().doubleValue() + 1)));
                }
                else{
                    amount = BigDecimal.valueOf(randomAmount.nextInt((int) Math.ceil(Math.log10(consignment.getAmount().doubleValue() + 1))));
                }

                amount = Utils.round(amount, 2);
                BigDecimal price = Utils.round(amount.multiply(consignment.getUnitPrice()),2);

                BigDecimal randomChanceAdditional = BigDecimal.valueOf(randomChance.nextInt(1) / 100.0);
                BigDecimal priceChance = price.divide(price.add(getMoney().getMoney()), 2, RoundingMode.HALF_UP);

                int chance = priceChance.compareTo(randomChanceAdditional);

//                System.out.println("//////////////////////////////////////////////////");
//                System.out.println(consignment.toStringStorage());
//                System.out.println("amount= " + amount);
//                System.out.println("price= " + price);
//                System.out.println("money= " + getMoney().getMoney());
//                System.out.println("randomChance= " + randomChanceInt);
//                System.out.println("priceChance= " + priceChance);
//                System.out.println("chance= " + chance);
//                System.out.println("//////////////////////////////////////////////////");

                if (getMoney().getMoney().compareTo(price) > 0 && chance > 0) {
                    Consignment consignmentToTranslate = consignment.clone();

                    BigDecimal minusAmount = consignmentToTranslate.minusAmount(amount);

                    consignmentToTranslate.setAmount(minusAmount);
                    basket.add(consignmentToTranslate);
                }
            }
        }
    }
}
