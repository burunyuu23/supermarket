package ru.vsu.edu.shlyikov_d_g.events;

import ru.vsu.edu.shlyikov_d_g.Utils;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.products.PurchaseUnit;
import ru.vsu.edu.shlyikov_d_g.rooms.*;
import ru.vsu.edu.shlyikov_d_g.visualisation.GameVisualise;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransferGoods {
    Room storage;
    Room store;
    GameVisualise gameVisualise;

    public TransferGoods(Room storage, Room store, GameVisualise gameVisualise) {
        this.storage = storage;
        this.store = store;
        this.gameVisualise = gameVisualise;
    }

    public void askStorage(){
        gameVisualise.askRoom(storage, "склад");

        List<Consignment> list = new ArrayList<>();

        for (PurchaseUnit pu: gameVisualise.getFromRoom("перемещение со склада в торговый зал")) { // заменить

            int numСonsignment = pu.getNums().get(0) - 1;
            int numBatch = pu.getNums().get(1) - 1;
            BigDecimal amount = pu.getAmount();

            Consignment cTemp = storage.getElements().get(storage.getElements().keySet().stream().toList().get(numСonsignment)).get(numBatch);
            cTemp.setAmount(amount);

            list.add(cTemp);
        }
        transfer(storage, store, list);
        gameVisualise.showRoom(store, "торговый зал");
    }

    //   3-3-74.91,3-2-51.91,3-3-100,1-2-20.99

    private void transfer(Room from, Room to, List<Consignment> list){
        BigDecimal size = new BigDecimal(0);
        for (Consignment c:list) {
            size = size.add(c.getAmount());
        }
        if (size.compareTo(to.getCapacity()) > 0){
            gameVisualise.toCapacityError(size, to);
        }
        else {
            from.moveElements(to, list);
        }
    }

    public void show(){
        gameVisualise.showRoom(storage, "Склад");
    }

}
