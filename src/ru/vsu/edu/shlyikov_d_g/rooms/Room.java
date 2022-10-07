package ru.vsu.edu.shlyikov_d_g.rooms;

import ru.vsu.edu.shlyikov_d_g.products.Consignment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Room {
    double capacity = 0;
    Map<String, Consignment> elements = new HashMap<>();

    public Room(){
    }

    public Room(int capacity){
        this.capacity = capacity;
    }

    public Room(double capacity){
        this.capacity = capacity;
    }

    // Добавить в текущую комнату
    public void addElements(List<Consignment> consignmentList){

    }

    // Удалить из текущей комнаты
    private void removeElements(List<Consignment> consignmentList){

    }

    // Переместить элементы в другую комнаты
    public void moveElements(Room room){

    }
}
