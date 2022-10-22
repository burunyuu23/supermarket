package ru.vsu.edu.shlyikov_d_g.events;

import ru.vsu.edu.shlyikov_d_g.Utils;
import ru.vsu.edu.shlyikov_d_g.attributes.Amounts;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.products.units.PurchaseUnit;
import ru.vsu.edu.shlyikov_d_g.products.units.TransferUnit;
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

    public void askStorage() {
        gameVisualise.showRoom(storage, "Склад");

        while (true) {
            List<Consignment> list = new ArrayList<>();
            List<TransferUnit> tuList = new ArrayList<>(gameVisualise.getFromRoomTU("удалить", "\\w+-\\w+[\\.\\w+]*"));

            for (TransferUnit tu : tuList) { // заменить
                int numСonsignment = tu.getNumConsignment() - 1;
                int numBatch = tu.getNumBatch()  - 1;
                BigDecimal amount = tu.getAmount();

                Consignment cTemp = storage.getElements().get(storage.getElements().keySet().stream().toList().get(numСonsignment)).get(numBatch);
                cTemp.setAmount(amount);

                list.add(cTemp);
            }

            Amounts amounts = new Amounts(Utils.countFreezeAmount(list), Utils.countNonFreezeAmount(list));

            boolean a = amounts.getNonFreeze().compareTo(store.getCapacity()) <= 0;
            boolean b = amounts.getFreeze().compareTo(store.getFridgeCapacity()) <= 0;

            if (a && b) {
                transfer(storage, store, list);
                if (!gameVisualise.continueEvent("перемещение")){
                    break;
                }
            }
            gameVisualise.showRoom(storage, "Склад");
            gameVisualise.showInfoAmount(amounts, store, "В торговом зале", !a, !b);
            gameVisualise.showRoom(store, "Торговый зал");
        }
    }

    //   3-3-74.91,3-2-51.91,3-3-100,1-2-20.99

    private void transfer(Room from, Room to, List<Consignment> list){
        Amounts amounts = Utils.countAmounts(list);
        boolean a = amounts.getNonFreeze().compareTo(to.getCapacity()) > 0;
        boolean b = amounts.getFreeze().compareTo(to.getFridgeCapacity()) > 0;
        gameVisualise.showRoom(store, "Торговый зал");
        gameVisualise.showRoom(storage, "Склад");
        if (a || b){
            gameVisualise.showInfoAmount(amounts, to, to.equals(store) ? "В торговом зале" : "На складе", a, b);
        }
        else {
            from.moveElements(to, list);
        }
    }

}
