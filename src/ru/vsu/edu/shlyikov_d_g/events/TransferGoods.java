package ru.vsu.edu.shlyikov_d_g.events;

import ru.vsu.edu.shlyikov_d_g.utils.Utils;
import ru.vsu.edu.shlyikov_d_g.utils.Amounts;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
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
        gameVisualise.showRoom(storage);

        while (true) {
            List<Consignment> list = new ArrayList<>();
            List<TransferUnit> tuList = TransferUnit.toTransferUnitList(gameVisualise.getFromRoom("удалить", "\\w+-\\w+-\\w+[\\.\\w+]*"));

            for (TransferUnit tu : tuList) {
                int numСonsignment = tu.getNumConsignment() - 1;
                int numBatch = tu.getNumBatch()  - 1;
                BigDecimal amount = tu.getAmount();

                Consignment consignmentFromInput = storage.getElements().get(storage.getElements().keySet().stream().toList().get(numСonsignment)).get(numBatch);
                consignmentFromInput.setAmount(amount);

                list.add(consignmentFromInput);
            }

            Amounts amounts = new Amounts(Utils.countFreezeAmount(list), Utils.countNonFreezeAmount(list));

            boolean nonFreeze = amounts.getNonFreeze().compareTo(store.getCapacity()) <= 0;
            boolean freeze = amounts.getFreeze().compareTo(store.getFridgeCapacity()) <= 0;

            if (nonFreeze && freeze) {
                transfer(storage, store, list);
                if (!gameVisualise.continueEvent("перемещение")){
                    break;
                }
            }
            gameVisualise.showRoom(storage);
            gameVisualise.showInfoAmount(amounts, store, "В торговом зале", !nonFreeze, !freeze);
            gameVisualise.showRoom(store);
        }
    }

    //   3-3-74.91,3-2-51.91,3-3-100,1-2-20.99

    private void transfer(Room from, Room to, List<Consignment> list){
        Amounts amounts = Utils.countAmounts(list);
       boolean nonFreeze = amounts.getNonFreeze().compareTo(to.getCapacity()) > 0;
       boolean freeze = amounts.getFreeze().compareTo(to.getFridgeCapacity()) > 0;
        gameVisualise.showRoom(store);
        gameVisualise.showRoom(storage);
        if (nonFreeze || freeze){
            gameVisualise.showInfoAmount(amounts, to, to.equals(store) ? "В торговом зале" : "На складе", nonFreeze, freeze);
        }
        else {
            from.moveElements(to, list);
        }
    }

}
