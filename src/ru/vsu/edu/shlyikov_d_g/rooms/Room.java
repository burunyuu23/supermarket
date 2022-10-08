package ru.vsu.edu.shlyikov_d_g.rooms;

import ru.vsu.edu.shlyikov_d_g.products.Consignment;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Room {
    BigDecimal capacity = new BigDecimal(0);
    Map<String, Consignment> elements = new HashMap<>();

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

    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    // Удалить из текущей комнаты
    private void removeElements(List<Consignment> consignmentList){

    }

    // Переместить элементы в другую комнаты
    public void moveElements(Room room){

    }
}
