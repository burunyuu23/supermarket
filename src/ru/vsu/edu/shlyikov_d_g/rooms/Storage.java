package ru.vsu.edu.shlyikov_d_g.rooms;

import java.math.BigDecimal;

public class Storage extends Room {

    public Storage(int capacity, int fridgeCapacity, String roomName){
        super(capacity, fridgeCapacity, roomName);
    }

    public Storage(double capacity, double fridgeCapacity, String roomName){
        super(capacity, fridgeCapacity, roomName);
    }
}
