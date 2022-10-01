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

    // obj clone
    public Consignment(Consignment c){
        this.vendor_code = c.getVendor_code();
        this.product_name = c.getProduct_name();
        this.amount = c.getAmount();
        this.measure = c.getMeasure();
        this.unit_price = c.getUnit_price();
        this.date_of_manufacture = c.getDate_of_manufacture();
        this.expiration_days = c.getExpiration_days();
        this.age_restricted = c.getAge_restricted();
        this.should_be_in_the_fridge = c.getShould_be_in_the_fridge();;
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

    public void plusAmount(double amount) {
        this.amount += amount;
    }

    public double minusAmount(double amount) {
        double a = getMinusAmount(amount);
        this.amount -= a;
        Consignment.round(this.amount, 2);
        return a;
    }

    private double getMinusAmount(double amount) {
        if (this.amount - amount <= 0){
            return this.amount;
        }
        else{
            return amount;
        }
    }

    public boolean getAge_restricted() {
        return age_restricted;
    }

    public boolean getShould_be_in_the_fridge() {
        return should_be_in_the_fridge;
    }

    public boolean idCheck(Consignment c){
        if (Objects.equals(this.vendor_code, c.getVendor_code())){
            id = c.getId() + 1;
            return true;
        }
        return false;
    }

    // Цена, с наценкой
    public void setCurr_price(int percent){
        this.curr_price = Math.ceil(this.unit_price * 1 + percent/100.0) - 0.01;
    }

    // Цена, введённая игроком
    public void setCurr_priceManual(int price){
        this.curr_price = price;
    }

    public void setDiscount(int d){
        this.discount = d;
        this.curr_price = Math.ceil(this.curr_price * 1 - this.discount/100.0) - 0.01;
    }

    private String rightDays(){
        if (expiration_days % 10 == 1 && expiration_days % 100 != 11){
            return "день";
        }
        else if (expiration_days % 10 == 2 || expiration_days % 10 == 3
                || expiration_days % 10 == 4 && expiration_days % 100 != 12
                && expiration_days % 100 != 13 && expiration_days % 100 != 14){
            return "дня";
        }
        else{
            return "дней";
        }

    }

    private boolean checkKG(){
        return this.measure.equals("кг");
    }

    private boolean checkSH(){
        return this.measure.equals("шт");
    }

    private double amountRandom(){
        double d = Math.random() * 1000 + 399;
        if (this.checkSH()){
            return round(d, 0);
        }
        return round(d, 2);
    }

    public void setAmountRandom(){
        setAmount(amountRandom());
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public String toStringSupplier(){
        return product_name + "; " +
                "\nколичество: " + amount + ";" +
                "\nпо цене за " + measure + ": " + unit_price + ";" +
                    "\nсрок годности: " + expiration_days + " " + rightDays() +
                (age_restricted ? ";\nне продаётся лицам младше 18 лет" : "") +
                (should_be_in_the_fridge ? ";\nдолжен храниться в холодильнике" : "") + ".\n";
    }

    public String toStringStore(){
        return product_name + "; " +
                "\nколичество: " + amount + ";" +
                "\nпо цене за " + measure + ": " + unit_price + ";" +
                "\nсрок годности: " + expiration_days + " " + rightDays() +
                (age_restricted ? ";\nне продаётся лицам младше 18 лет" : "") +
                (should_be_in_the_fridge ? ";\nдолжен храниться в холодильнике" : "") + ".";
    }

    public String toStringCustomer(){
        return product_name + "; " +
                "\nколичество: " + amount + ";" +
                "\nпо цене за " + measure + ": " + unit_price + ";" +
                "\nсрок годности: " + expiration_days + " " + rightDays() +
                (age_restricted ? ";\nне продаётся лицам младше 18 лет" : "") +
                (should_be_in_the_fridge ? ";\nдолжен храниться в холодильнике" : "") + ".";
    }

}
