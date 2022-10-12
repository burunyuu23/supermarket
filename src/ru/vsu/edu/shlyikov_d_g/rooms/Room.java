package ru.vsu.edu.shlyikov_d_g.rooms;

import ru.vsu.edu.shlyikov_d_g.Utils;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;

import java.math.BigDecimal;
import java.util.*;

public abstract class Room {
    private BigDecimal capacity = new BigDecimal(0);
    private Map<String, Map<Integer, Consignment>> elements = new HashMap<>();

    public Room(){
    }

    public Room(int capacity){
        this.capacity = BigDecimal.valueOf(capacity);
    }

    public Room(double capacity){
        this.capacity = BigDecimal.valueOf(capacity);
    }

    // Добавить в текущую комнату
    public void addElements(List<Consignment> consignmentList){
        for (Consignment c:consignmentList) {
            String vendorCode = c.getVendorCode();
            if (!elements.containsKey(vendorCode)) {
                elements.put(vendorCode, new HashMap<>());
                c.setBatchNumber(Utils.findMinButcherNumber(elements.get(vendorCode)));
            }
            elements.get(vendorCode).put(c.getBatchNumber(), c);
        }
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    // Удалить из текущей комнаты
    private void removeElements(List<Consignment> consignmentList){
        for (Consignment c:consignmentList) {
            String vendorCode = c.getVendorCode();
            elements.get(vendorCode).remove(c.getBatchNumber(), c);
        }
    }

    // Переместить элементы в другую комнату
    public void moveElements(Room room, List<Consignment> list){
        this.removeElements(list);
        room.addElements(list);
    }
}
