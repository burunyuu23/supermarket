package ru.vsu.edu.shlyikov_d_g.rooms;

import jdk.jshell.execution.Util;
import ru.vsu.edu.shlyikov_d_g.Utils;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;

import java.math.BigDecimal;
import java.util.*;

public abstract class Room {
    private final BigDecimal capacity;
    private final BigDecimal fridgeCapacity; // холодильник

    private Map<String, Map<Integer, Consignment>> elements = new HashMap<>();
    private String roomName;

    public Room(int capacity, int fridgeCapacity, String roomName){
        this.capacity = BigDecimal.valueOf(capacity);
        this.fridgeCapacity = BigDecimal.valueOf(fridgeCapacity);
        this.roomName = roomName;
    }

    public Room(double capacity, double fridgeCapacity, String roomName){
        this.capacity = BigDecimal.valueOf(capacity);
        this.fridgeCapacity = BigDecimal.valueOf(fridgeCapacity);
        this.roomName = roomName;
    }

    // Добавить в текущую комнату
    public BigDecimal addElements(List<Consignment> consignmentList){
        for (Consignment c:consignmentList) {
            String vendorCode = c.getVendorCode();
            if (!elements.containsKey(vendorCode)) {
                elements.put(vendorCode, new HashMap<>());
            }
            int num = Utils.findMinButcherNumber(elements.get(vendorCode));
            c.setBatchNumber(num);
            elements.get(vendorCode).put(num, c);
        }
        return Utils.countAmount(consignmentList);
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public BigDecimal getFridgeCapacity(){
        return fridgeCapacity;
    }

    public String getRoomName(){
        return roomName;
    }

    public Map<String, Map<Integer, Consignment>> getElements() {
        return elements;
    }

    // Удалить из текущей комнаты
    private BigDecimal removeElements(List<Consignment> consignmentList){
        for (Consignment c:consignmentList) {
            String vendorCode = c.getVendorCode();
            elements.get(vendorCode).remove(c.getBatchNumber(), c);
        }
        return Utils.countAmount(consignmentList);
    }

    // Переместить элементы в другую комнату
    public BigDecimal moveElements(Room room, List<Consignment> list){

        this.removeElements(list);
        room.addElements(list);

        return Utils.countAmount(list);
    }
}
