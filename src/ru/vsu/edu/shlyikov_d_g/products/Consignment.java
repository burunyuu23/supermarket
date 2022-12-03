package ru.vsu.edu.shlyikov_d_g.products;

import ru.vsu.edu.shlyikov_d_g.utils.Icon;
import ru.vsu.edu.shlyikov_d_g.utils.Utils;

import java.math.BigDecimal;

public class Consignment {
    private String vendorCode;
    private Icon photo;

    // this all must be received from vendor code
    private String productName;
    private BigDecimal amount;
    private String measure; // in kilograms/grams or pieces
    private BigDecimal unitPrice;
    private BigDecimal currPrice;
    private int discount;
    private int batchNumber;
    private int expirationDays;
    private boolean ageRestricted;
    private boolean shouldBeInTheFridge;

    // perfect object creation
    public Consignment(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    // test object creation (w/o database) or nested creation from database
    public Consignment(ConsignmentBuilder consignmentBuilder){
        vendorCode = consignmentBuilder.vendorCode;
        photo = consignmentBuilder.photo;
        productName = consignmentBuilder.productName;
        amount = consignmentBuilder.amount;
        measure = consignmentBuilder.measure;
        unitPrice = consignmentBuilder.unitPrice;
        currPrice = consignmentBuilder.currPrice;
        discount = consignmentBuilder.discount;
        batchNumber = consignmentBuilder.batchNumber;
        expirationDays = consignmentBuilder.expirationDays;
        ageRestricted = consignmentBuilder.ageRestricted;
        shouldBeInTheFridge = consignmentBuilder.shouldBeInTheFridge;
    }

    // TODO метод возвращающий клон
    public Consignment clone(){
        Consignment conNew = new Consignment(new ConsignmentBuilder(
                this.vendorCode, this.getProductName(), this.getMeasure(),
                this.unitPrice, this.expirationDays, this.ageRestricted,
                this.shouldBeInTheFridge
        ));
        if (this.getAmount() != null){
            conNew.setAmount(this.getAmount());
        }
        conNew.setBatchNumber(this.getBatchNumber());
        return conNew;
    }

    public static class ConsignmentBuilder{
        private final String vendorCode;
        private Icon photo;

        // this all must be received from vendor code
        private final String productName;
        private BigDecimal amount; // optional
        private final String measure; // in kilograms/grams or pieces
        private final BigDecimal unitPrice;
        private BigDecimal currPrice; // optional
        private int discount; // optional
        private int batchNumber; // optional
        private int expirationDays;
        private final boolean ageRestricted;
        private final boolean shouldBeInTheFridge;

        public ConsignmentBuilder(String vendorCode, String product_name, String measure, double unitPrice,
                               int expirationDays, boolean ageRestricted,
                               boolean shouldBeInTheFridge) {
            this.vendorCode = vendorCode;
            this.productName = product_name;
            this.measure = measure;
            this.unitPrice = BigDecimal.valueOf(unitPrice);
            this.expirationDays = expirationDays;
            this.ageRestricted = ageRestricted;
            this.shouldBeInTheFridge = shouldBeInTheFridge;
        }

        public ConsignmentBuilder(String vendorCode, String product_name, String measure, BigDecimal unitPrice,
                                  int expirationDays, boolean ageRestricted,
                                  boolean shouldBeInTheFridge) {
            this.vendorCode = vendorCode;
            this.productName = product_name;
            this.measure = measure;
            this.unitPrice = unitPrice;
            this.expirationDays = expirationDays;
            this.ageRestricted = ageRestricted;
            this.shouldBeInTheFridge = shouldBeInTheFridge;
        }

        public ConsignmentBuilder setPhoto(Icon photo){
            this.photo = photo;
            return this;
        }

        public ConsignmentBuilder setAmount(BigDecimal amount){
            this.amount = amount;
            return this;
        }

        public ConsignmentBuilder setAmount(double amount){
            this.amount = BigDecimal.valueOf(amount);
            return this;
        }

        public ConsignmentBuilder setDiscount(int discount){
            this.discount = discount;
            return this;
        }

        public ConsignmentBuilder setBatchNumber(int batchNumber){
            this.batchNumber = batchNumber;
            return this;
        }

        public ConsignmentBuilder setCurrPrice(BigDecimal currPrice){
            this.currPrice = currPrice;

            return this;
        }

        public Consignment build() {
            return new Consignment(this);
        }
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendor_code) {
        this.vendorCode = vendor_code;
    }

    public int getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(int batchNumber) {
        this.batchNumber = batchNumber;
    }

    public Icon getPhoto() {
        return photo;
    }

    public String getProductName() {
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

    public BigDecimal getCurrPrice() {
        return currPrice;
    }

    public int getExpirationDays() {
        return expirationDays;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = Utils.round(amount, 2);
    }

    public BigDecimal plusAmount(BigDecimal amount) {
        this.amount = Utils.round(this.amount.add(amount), 2);
        return this.amount;
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

    public boolean isEqualsInVendorCode(Consignment consignment){
        return this.vendorCode.equals(consignment.getVendorCode());
    }

    public boolean getAgeRestricted() {
        return ageRestricted;
    }

    public boolean getShouldBeInTheFridge() {
        return shouldBeInTheFridge;
    }

    // Цена, с наценкой
    public void setCurrPrice(int percent) {
        this.currPrice = this.unitPrice.multiply(new BigDecimal(1)).add(new BigDecimal(percent / 100.0)).add(BigDecimal.valueOf(-0.01));
    }

    // Цена, введённая игроком
    public void setCurrPriceManual(int price) {
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

    public void nextDay(){
        this.expirationDays -= 1;
    }

    public void setExpirationDays(int expirationDays) {
        this.expirationDays = expirationDays;
    }

    public boolean checkKG() {
        return this.measure.equals("кг");
    }

    public boolean checkSH() {
        return this.measure.equals("шт");
    }

    private BigDecimal amountRandom() {
        double d = Math.random() * 1000 + 399;
        if (checkSH()){
            return Utils.round(new BigDecimal((int) d), 2);
        }
        else{
            return Utils.round(new BigDecimal(d), 2);
        }
    }

    public void setAmountRandom() {
        setAmount(amountRandom());
    }

    @Override
    public String toString() {
        return "Consignment{" +
                "vendorCode='" + vendorCode + '\'' +
                ", photo=" + photo +
                ", productName='" + productName + '\'' +
                ", amount=" + amount +
                ", measure='" + measure + '\'' +
                ", unitPrice=" + unitPrice +
                ", currPrice=" + currPrice +
                ", discount=" + discount +
                ", batchNumber=" + batchNumber +
                ", expirationDays=" + expirationDays +
                ", ageRestricted=" + ageRestricted +
                ", shouldBeInTheFridge=" + shouldBeInTheFridge +
                '}';
    }

    // TODO StringBuilder
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

    public String toStringPurchase(boolean bool){
        return  productName + "; "
                + (bool ? (
                "\nколичество: " + amount + measure + ";" + "\nцена: " + unitPrice.multiply(amount) + ";") : "");
    }
}

