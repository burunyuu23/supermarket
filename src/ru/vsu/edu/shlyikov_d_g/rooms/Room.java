package ru.vsu.edu.shlyikov_d_g.rooms;

import ru.vsu.edu.shlyikov_d_g.utils.Amounts;
import ru.vsu.edu.shlyikov_d_g.utils.Utils;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;

import java.math.BigDecimal;
import java.util.*;

public abstract class Room {
    private final String roomName;
    private Amounts amounts = new Amounts(0, 0);
    private final BigDecimal nonFridgeCapacity;
    private final BigDecimal fridgeCapacity; // холодильник

    private final Map<String, Map<Integer, Consignment>> elements = new HashMap<>();

    public Room(int nonFridgeCapacity, int fridgeCapacity, String roomName){
        this.nonFridgeCapacity = BigDecimal.valueOf(nonFridgeCapacity);
        this.fridgeCapacity = BigDecimal.valueOf(fridgeCapacity);
        this.roomName = roomName;
    }

    public Room(double nonFridgeCapacity, double fridgeCapacity, String roomName){
        this.nonFridgeCapacity = BigDecimal.valueOf(nonFridgeCapacity);
        this.fridgeCapacity = BigDecimal.valueOf(fridgeCapacity);
        this.roomName = roomName;
    }

    // Добавить в текущую комнату
    public BigDecimal addElements(List<Consignment> consignmentList){
        for (Consignment c:consignmentList) {
            String vendorCode = c.getVendorCode();
            amounts.plus(c.getAmount(), c.getShouldBeInTheFridge());
            if (!elements.containsKey(vendorCode)) {
                elements.put(vendorCode, new HashMap<>());
            }
            boolean flag = true;
            for (int i = 0; i < elements.get(vendorCode).size(); i++) {
                if (elements.get(vendorCode).get(i).getExpirationDays() == c.getExpirationDays()) {
                    Consignment cTemp = elements.get(vendorCode).get(i).clone();
                    cTemp.setAmount(c.plusAmount(elements.get(vendorCode).get(i).getAmount()));
                    elements.get(vendorCode).put(i, cTemp);
                    flag = false;
                    break;
                }
            }
            if (flag) {
                int num = Utils.findMinButcherNumber(elements.get(vendorCode));
                c.setBatchNumber(num);
                elements.get(vendorCode).put(num, c);
            }
        }
        return Utils.countAmount(consignmentList);
    }

    public BigDecimal getNonFridgeCapacity() {
        return nonFridgeCapacity;
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

    public void refresh(){
        for (String vendorCode:this.getElements().keySet()) {
            for (Integer batch:this.getElements().get(vendorCode).keySet()) {
                Consignment c = this.getElements().get(vendorCode).get(batch);

                if (c.getAmount().equals(BigDecimal.valueOf(0))) {
                    deleteElements(vendorCode, batch);
                }
            }
        }
    }

    private void deleteElements(String vendorCode, Integer batch){
        this.getElements().get(vendorCode).remove(batch);
        if (this.getElements().get(vendorCode).size() == 0){
            this.getElements().remove(vendorCode);
        }
    }

    public void nextDay(){
        for (String vendorCode:this.getElements().keySet()) {
            for (Integer batch:this.getElements().get(vendorCode).keySet()) {
                Consignment c = this.getElements().get(vendorCode).get(batch);

                c.nextDay();
            }
        }
    }

    public Amounts getAmounts() {
        return amounts;
    }

    public void setAmounts(Amounts amounts) {
        this.amounts = amounts;
    }

    // Удалить из текущей комнаты
    public List<Consignment> removeElements(List<Consignment> consignmentList){
        for (Consignment c:consignmentList) {
            Consignment input = this.getElements().get(c.getVendorCode()).get(c.getBatchNumber());
            amounts.minus(c.getAmount(), c.getShouldBeInTheFridge());
                if (c.getAmount().compareTo(input.getAmount()) >= 0) {
                    deleteElements(c.getVendorCode(), c.getBatchNumber());
                }
                else{
                    c.setAmount(input.minusAmount(c.getAmount()));
                }
        }
        return consignmentList;
    }

    // Переместить элементы в другую комнату
    public BigDecimal moveElements(Room room, List<Consignment> list){

        room.addElements(this.removeElements(list));

        return Utils.countAmount(list);
    }
}
