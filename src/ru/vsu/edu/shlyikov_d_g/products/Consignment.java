package ru.vsu.edu.shlyikov_d_g.products;

import ru.vsu.edu.shlyikov_d_g.Utils;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Consignment {
    private String vendor_code;
    private int id = 1;
    private Image photo;

    // this all must be received from vendor code
    private String product_name;
    private BigDecimal amount;
    private String measure; // in kilograms/grams or pieces
    private BigDecimal unit_price;
    private BigDecimal curr_price;
    private int discount;
    private int batchNumber;
    private LocalDateTime date_of_manufacture;
    private int expiration_days;
    private boolean age_restricted;
    private boolean should_be_in_the_fridge;

    // perfect object creation
    public Consignment(String vendor_code) {
        this.vendor_code = vendor_code;
    }

    // test object creation (w/o database) or nested creation from database
    public Consignment(String product_name, BigDecimal amount, String measure,
                       BigDecimal unit_price, LocalDateTime date_of_manufacture,
                       int expiration_days, boolean age_restricted,
                       boolean should_be_in_the_fridge) {
        this.product_name = product_name;
        this.amount = amount;
        this.measure = measure;
        this.unit_price = unit_price;
        this.date_of_manufacture = date_of_manufacture;
        this.expiration_days = expiration_days;
        this.age_restricted = age_restricted;
        this.should_be_in_the_fridge = should_be_in_the_fridge;
    }

    // obj clone
    public Consignment(Consignment c) {
        this.vendor_code = c.getVendorCode();
        this.product_name = c.getProduct_name();
        this.amount = c.getAmount();
        this.measure = c.getMeasure();
        this.unit_price = c.getUnit_price();
        this.date_of_manufacture = c.getDate_of_manufacture();
        this.expiration_days = c.getExpiration_days();
        this.age_restricted = c.getAge_restricted();
        this.should_be_in_the_fridge = c.getShould_be_in_the_fridge();
        ;
    }

    public Consignment(String vendor_code, String product_name, BigDecimal amount, String measure,
                       BigDecimal unit_price, LocalDateTime date_of_manufacture,
                       int expiration_days, boolean age_restricted,
                       boolean should_be_in_the_fridge) {
        this.vendor_code = vendor_code;
        this.product_name = product_name;
        this.amount = amount;
        this.measure = measure;
        this.unit_price = unit_price;
        this.date_of_manufacture = date_of_manufacture;
        this.expiration_days = expiration_days;
        this.age_restricted = age_restricted;
        this.should_be_in_the_fridge = should_be_in_the_fridge;
    }

    // for set with all products
    public Consignment(String vendor_code, String product_name, String measure,
                       double unit_price, int expiration_days, boolean age_restricted,
                       boolean should_be_in_the_fridge) {
        this.vendor_code = vendor_code;
        this.product_name = product_name;
        this.measure = measure;
        this.unit_price = BigDecimal.valueOf(unit_price);
        this.expiration_days = expiration_days;
        this.age_restricted = age_restricted;
        this.should_be_in_the_fridge = should_be_in_the_fridge;
    }


    public String getVendorCode() {
        return vendor_code;
    }

    public void setVendorCode(String vendor_code) {
        this.vendor_code = vendor_code;
    }

    public int getId() {
        return id;
    }

    public int getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(int batchNumber) {
        this.batchNumber = batchNumber;
    }

    public void setId(int a) {
        id = a;
    }

    public Image getPhoto() {
        return photo;
    }

    public String getProduct_name() {
        return product_name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getMeasure() {
        return measure;
    }

    public BigDecimal getUnit_price() {
        return unit_price;
    }

    public LocalDateTime getDate_of_manufacture() {
        return date_of_manufacture;
    }

    public int getExpiration_days() {
        return expiration_days;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void plusAmount(BigDecimal amount) {
        this.amount = Utils.round(this.amount.add(amount), 2);
    }

    public BigDecimal minusAmount(BigDecimal amount) {
        BigDecimal a = getMinusAmount(amount);
        this.amount = Utils.round(this.amount.add(a.multiply(BigDecimal.valueOf(-1))), 2);
        return Utils.round(a, 2);
    }

    private BigDecimal getMinusAmount(BigDecimal amount) {
        if (this.amount.add(amount.multiply(new BigDecimal(-1))).compareTo(new BigDecimal(0)) < 0) {
            return this.amount;
        } else if (amount.compareTo(new BigDecimal(0)) <= 0) {
            return new BigDecimal(0);
        } else {
            return amount;
        }
    }

    public boolean getAge_restricted() {
        return age_restricted;
    }

    public boolean getShould_be_in_the_fridge() {
        return should_be_in_the_fridge;
    }

    public boolean idCheck(Consignment c) {
        if (Objects.equals(this.vendor_code, c.getVendorCode())) {
            id = c.getId() + 1;
            return true;
        }
        return false;
    }

    // Цена, с наценкой
    public void setCurr_price(int percent) {
        this.curr_price = this.unit_price.multiply(new BigDecimal(1)).add(new BigDecimal(percent / 100.0)).add(BigDecimal.valueOf(-0.01));
    }

    // Цена, введённая игроком
    public void setCurr_priceManual(int price) {
        this.curr_price = new BigDecimal(price);
    }

    public void setDiscount(int d) {
        this.discount = d;
        this.curr_price = this.curr_price.multiply(new BigDecimal(1)).add(new BigDecimal(discount / 100.0)).add(BigDecimal.valueOf(-0.01));
    }

    private String rightDays() {
        if (expiration_days % 10 == 1 && expiration_days % 100 != 11) {
            return "день";
        } else if (expiration_days % 10 == 2 || expiration_days % 10 == 3
                || expiration_days % 10 == 4 && expiration_days % 100 != 12
                && expiration_days % 100 != 13 && expiration_days % 100 != 14) {
            return "дня";
        } else {
            return "дней";
        }

    }

    private boolean checkKG() {
        return this.measure.equals("кг");
    }

    private boolean checkSH() {
        return this.measure.equals("шт");
    }

    private BigDecimal amountRandom() {
        double d = Math.random() * 1000 + 399;
        return Utils.round(new BigDecimal(d), 2);
    }

    public void setAmountRandom() {
        setAmount(amountRandom());
    }

    public String toStringSupplier() {
        return product_name + "; " +
                "\nколичество: " + amount + ";" +
                "\nпо цене за " + measure + ": " + unit_price + ";" +
                "\nсрок годности: " + expiration_days + " " + rightDays() +
                (age_restricted ? ";\nне продаётся лицам младше 18 лет" : "") +
                (should_be_in_the_fridge ? ";\nдолжен храниться в холодильнике" : "") + ".\n";
    }

    public String toStringStore() {
        return product_name + "; " +
                "\nколичество: " + amount + ";" +
                "\nпо цене за " + measure + ": " + unit_price + ";" +
                "\nсрок годности: " + expiration_days + " " + rightDays() +
                (age_restricted ? ";\nне продаётся лицам младше 18 лет" : "") +
                (should_be_in_the_fridge ? ";\nдолжен храниться в холодильнике" : "") + ".";
    }

    public String toStringCustomer() {
        return product_name + "; " +
                "\nколичество: " + amount + ";" +
                "\nпо цене за " + measure + ": " + unit_price + ";" +
                "\nсрок годности: " + expiration_days + " " + rightDays() +
                (age_restricted ? ";\nне продаётся лицам младше 18 лет" : "") +
                (should_be_in_the_fridge ? ";\nдолжен храниться в холодильнике" : "") + ".";
    }
}

