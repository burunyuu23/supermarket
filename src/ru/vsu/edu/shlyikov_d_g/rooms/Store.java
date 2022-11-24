package ru.vsu.edu.shlyikov_d_g.rooms;

import ru.vsu.edu.shlyikov_d_g.events.Discount;

import java.util.List;

public class Store extends Room {
    private List<Discount> discountList;

    public Store(int capacity, int fridgeCapacity, String roomName){
        super(capacity, fridgeCapacity, roomName);
    }

    public Store(double capacity, double fridgeCapacity, String roomName){
        super(capacity, fridgeCapacity, roomName);
    }

    public List<Discount> getDiscountList() {
        return discountList;
    }

    public void setDiscountList(List<Discount> discountList) {
        this.discountList = discountList;
    }
}
