package ru.vsu.edu.shlyikov_d_g.rooms;

import jdk.jshell.execution.Util;
import ru.vsu.edu.shlyikov_d_g.Utils;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;

import java.math.BigDecimal;
import java.util.*;

public abstract class Room {
    private BigDecimal capacity = new BigDecimal(0);
    private Map<String, Map<Integer, Consignment>> elements = new HashMap<>();
    private String roomName;

    public Room(){
    }

    public Room(int capacity, String roomName){
        this.capacity = BigDecimal.valueOf(capacity);
        this.roomName = roomName;
    }

    public Room(double capacity, String roomName){
        this.capacity = BigDecimal.valueOf(capacity);
        this.roomName = roomName;
    }

    // Добавить в текущую комнату
    public void addElements(List<Consignment> consignmentList){
        for (Consignment c:consignmentList) {
            String vendorCode = c.getVendorCode();
            if (!elements.containsKey(vendorCode)) {
                elements.put(vendorCode, new HashMap<>());
            }
            int num = Utils.findMinButcherNumber(elements.get(vendorCode));
            c.setBatchNumber(num);
            elements.get(vendorCode).put(num, c);
        }
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public String getRoomName(){
        return roomName;
    }

    public Map<String, Map<Integer, Consignment>> getElements() {
        return elements;
    }

    // Удалить из текущей комнаты
    private void removeElements(List<Consignment> consignmentList){
        for (Consignment c:consignmentList) {
            String vendorCode = c.getVendorCode();
            elements.get(vendorCode).remove(c.getBatchNumber(), c);
        }
    }

    // Переместить элементы в другую комнату
    public int moveElements(Room room, List<Consignment> list){ // TODO: возвращать amount
        this.removeElements(list);
        room.addElements(list);

        return 0;
    }
}
