package ru.vsu.edu.shlyikov_d_g.products;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.Objects;

public class Consignment {
    private String vendor_code;
    private int id = 1;
    private Image photo;

    // this all must be received from vendor code
    private String product_name;
    private double amount;
    private String measure; // in kilograms/grams or pieces
    private double unit_price;
    private double curr_price;
    private int discount;
    private LocalDateTime date_of_manufacture;
    private int expiration_days;
    private boolean age_restricted;
    private boolean should_be_in_the_fridge;

    // perfect object creation
    public Consignment(String vendor_code){
        this.vendor_code = vendor_code;
    }

    // test object creation (w/o database) or nested creation from database
    public Consignment(String product_name, double amount, String measure,
                       double unit_price, LocalDateTime date_of_manufacture,
                       int expiration_days,boolean age_restricted,
                               boolean should_be_in_the_fridge){
        this.product_name = product_name;
        this.amount = amount;
        this.measure = measure;
        this.unit_price = unit_price;
        this.date_of_manufacture = date_of_manufacture;
        this.expiration_days = expiration_days;
        this.age_restricted = age_restricted;
        this.should_be_in_the_fridge = should_be_in_the_fridge;
    }

    public Consignment(String vendor_code, String product_name, double amount, String measure,
                       double unit_price, LocalDateTime date_of_manufacture,
                       int expiration_days,boolean age_restricted,
                       boolean should_be_in_the_fridge){
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
                       boolean should_be_in_the_fridge){
        this.vendor_code = vendor_code;
        this.product_name = product_name;
        this.measure = measure;
        this.unit_price = unit_price;
        this.expiration_days = expiration_days;
        this.age_restricted = age_restricted;
        this.should_be_in_the_fridge = should_be_in_the_fridge;
    }

    public String getVendor_code() {
        return vendor_code;
    }

    public int getId() {
        return id;
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

    public double getAmount() {
        return amount;
    }

    public String getMeasure() {
        return measure;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public LocalDateTime getDate_of_manufacture() {
        return date_of_manufacture;
    }

    public int getExpiration_days() {
        return expiration_days;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isAge_restricted() {
        return age_restricted;
    }

    public boolean isShould_be_in_the_fridge() {
        return should_be_in_the_fridge;
    }

    public boolean idCheck(Consignment c){
        if (Objects.equals(this.vendor_code, c.getVendor_code())){
            id = c.getId() + 1;
            return true;
        }
        return false;
    }

    // price based on extra charge
    public void setCurr_price(int percent){
        this.curr_price = Math.ceil(this.unit_price * 1 + percent/100.0) - 0.01;
    }

    // price entered by player
    public void setCurr_priceManual(int price){
        this.curr_price = price;
    }

    public void setDiscount(int d){
        this.discount = d;
        this.curr_price = Math.ceil(this.curr_price * 1 - this.discount/100.0) - 0.01;
    }

    public String toStringSupplier(){
        return product_name + "; " +
                "\nколичество: " + amount + ";" +
                "\nпо цене за " + measure + " : " + unit_price + ";" +
                "\nсрок годности: " + expiration_days + " дней" +
                (age_restricted ? ";\nне продаётся лицам младше 18 лет" : "") +
                (should_be_in_the_fridge ? ";\nдолжен храниться в холодильнике" : "") + ".";
    }

    public String toStringStore(){
        return product_name + "; " +
                "\nколичество: " + amount + ";" +
                "\nпо цене за " + measure + " : " + unit_price + ";" +
                "\nсрок годности: " + expiration_days + " дней" +
                (age_restricted ? ";\nне продаётся лицам младше 18 лет" : "") +
                (should_be_in_the_fridge ? ";\nдолжен храниться в холодильнике" : "") + ".";
    }

    public String toStringCustomer(){
        return product_name + "; " +
                "\nколичество: " + amount + ";" +
                "\nпо цене за " + measure + " : " + unit_price + ";" +
                "\nсрок годности: " + expiration_days + " дней" +
                (age_restricted ? ";\nне продаётся лицам младше 18 лет" : "") +
                (should_be_in_the_fridge ? ";\nдолжен храниться в холодильнике" : "") + ".";
    }

}
