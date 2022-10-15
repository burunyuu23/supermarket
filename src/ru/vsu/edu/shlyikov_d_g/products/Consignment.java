package ru.vsu.edu.shlyikov_d_g.products;

import ru.vsu.edu.shlyikov_d_g.Utils;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Consignment {
    private String vendorCode;
    private int id = 1;
    private Image photo;

    // this all must be received from vendor code
    private String productName; // TODO заменить названия
    private BigDecimal amount;
    private String measure; // in kilograms/grams or pieces
    private BigDecimal unitPrice;
    private BigDecimal currPrice;
    private int discount;
    private int batchNumber;
    private LocalDateTime dateOfManufacture;
    private int expirationDays;
    private boolean ageRestricted;
    private boolean shouldBeInTheFridge;

    // perfect object creation
    public Consignment(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    // test object creation (w/o database) or nested creation from database
    public Consignment(String product_name, BigDecimal amount, String measure,
                       BigDecimal unitPrice, LocalDateTime dateOfManufacture,
                       int expirationDays, boolean ageRestricted,
                       boolean shouldBeInTheFridge) {
        this.productName = product_name;
        this.amount = amount;
        this.measure = measure;
        this.unitPrice = unitPrice;
        this.dateOfManufacture = dateOfManufacture;
        this.expirationDays = expirationDays;
        this.ageRestricted = ageRestricted;
        this.shouldBeInTheFridge = shouldBeInTheFridge;
    }

    // obj clone
    public Consignment(Consignment c) {
        this.vendorCode = c.getVendorCode();
        this.productName = c.getProduct_name();
        this.amount = c.getAmount();
        this.measure = c.getMeasure();
        this.unitPrice = c.getUnitPrice();
        this.dateOfManufacture = c.getDateOfManufacture();
        this.expirationDays = c.getExpirationDays();
        this.ageRestricted = c.getAgeRestricted();
        this.shouldBeInTheFridge = c.getShouldBeInTheFridge();
        ;
    }

    public Consignment(String vendorCode, String product_name, BigDecimal amount, String measure,
                       BigDecimal unitPrice, LocalDateTime dateOfManufacture,
                       int expirationDays, boolean ageRestricted,
                       boolean shouldBeInTheFridge) {
        this.vendorCode = vendorCode;
        this.productName = product_name;
        this.amount = amount;
        this.measure = measure;
        this.unitPrice = unitPrice;
        this.dateOfManufacture = dateOfManufacture;
        this.expirationDays = expirationDays;
        this.ageRestricted = ageRestricted;
        this.shouldBeInTheFridge = shouldBeInTheFridge;
    }

    // for set with all products
    public Consignment(String vendorCode, String product_name, String measure,
                       double unitPrice, int expirationDays, boolean ageRestricted,
                       boolean shouldBeInTheFridge) {
        this.vendorCode = vendorCode;
        this.productName = product_name;
        this.measure = measure;
        this.unitPrice = BigDecimal.valueOf(unitPrice);
        this.expirationDays = expirationDays;
        this.ageRestricted = ageRestricted;
        this.shouldBeInTheFridge = shouldBeInTheFridge;
    }



    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendor_code) {
        this.vendorCode = vendor_code;
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
        return productName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getMeasure() {
        return measure;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public LocalDateTime getDateOfManufacture() {
        return dateOfManufacture;
    }

    public int getExpirationDays() {
        return expirationDays;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = Utils.round(amount, 2);
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

    public boolean getAgeRestricted() {
        return ageRestricted;
    }

    public boolean getShouldBeInTheFridge() {
        return shouldBeInTheFridge;
    }

    public boolean idCheck(Consignment c) {
        if (Objects.equals(this.vendorCode, c.getVendorCode())) {
            id = c.getId() + 1;
            return true;
        }
        return false;
    }

    // Цена, с наценкой
    public void setCurrPrice(int percent) {
        this.currPrice = this.unitPrice.multiply(new BigDecimal(1)).add(new BigDecimal(percent / 100.0)).add(BigDecimal.valueOf(-0.01));
    }

    // Цена, введённая игроком
    public void setCurr_priceManual(int price) {
        this.currPrice = new BigDecimal(price);
    }

    public void setDiscount(int d) {
        this.discount = d;
        this.currPrice = this.currPrice.multiply(new BigDecimal(1)).add(new BigDecimal(discount / 100.0)).add(BigDecimal.valueOf(-0.01));
    }

    private String rightDays() {
        if (expirationDays % 10 == 1 && expirationDays % 100 != 11) {
            return "день";
        } else if (expirationDays % 10 == 2 || expirationDays % 10 == 3
                || expirationDays % 10 == 4 && expirationDays % 100 != 12
                && expirationDays % 100 != 13 && expirationDays % 100 != 14) {
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
        return productName + "; " +
                "\nколичество: " + amount + ";" +
                "\nпо цене за " + measure + ": " + unitPrice + ";" +
                "\nсрок годности: " + expirationDays + " " + rightDays() +
                (ageRestricted ? ";\nне продаётся лицам младше 18 лет" : "") +
                (shouldBeInTheFridge ? ";\nдолжен храниться в холодильнике" : "") + ".\n";
    }

    public String toStringStore() {
        return productName + "; " +
                "\nколичество: " + amount + ";" +
                "\nпо цене за " + measure + ": " + unitPrice + ";" +
                "\nсрок годности: " + expirationDays + " " + rightDays() +
                (ageRestricted ? ";\nне продаётся лицам младше 18 лет" : "") +
                (shouldBeInTheFridge ? ";\nдолжен храниться в холодильнике" : "") + ".\n";
    }

    public String toStringStorage() {
        return "артикул: " + vendorCode + "; \n"
                + productName + "; " +
                "\nномер партии: " + (batchNumber + 1) + ";" +
                "\nколичество: " + amount + ";" +
                "\nпо цене за " + measure + ": " + unitPrice + ";" +
                "\nсрок годности: " + expirationDays + " " + rightDays() +
                (ageRestricted ? ";\nне продаётся лицам младше 18 лет" : "") +
                (shouldBeInTheFridge ? ";\nдолжен храниться в холодильнике" : "") + ".\n";
    }

    public String toStringCustomer() {
        return productName + "; " +
                "\nколичество: " + amount + ";" +
                "\nпо цене за " + measure + ": " + unitPrice + ";" +
                "\nсрок годности: " + expirationDays + " " + rightDays() +
                (ageRestricted ? ";\nне продаётся лицам младше 18 лет" : "") +
                (shouldBeInTheFridge ? ";\nдолжен храниться в холодильнике" : "") + ".\n";
    }
}

