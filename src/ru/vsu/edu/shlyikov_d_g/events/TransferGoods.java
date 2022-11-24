package ru.vsu.edu.shlyikov_d_g.events;

import ru.vsu.edu.shlyikov_d_g.products.units.PurchaseUnit;
import ru.vsu.edu.shlyikov_d_g.utils.Utils;
import ru.vsu.edu.shlyikov_d_g.utils.Amounts;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.products.units.TransferUnit;
import ru.vsu.edu.shlyikov_d_g.rooms.*;
import ru.vsu.edu.shlyikov_d_g.visualisation.GameVisualise;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
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

//        System.out.println("///////////////////////////////////////////////////////////////////////////////");
//        System.out.println(storage.getElements());
//        System.out.println(storage.getElements().keySet().stream().toList());
//        System.out.println("///////////////////////////////////////////////////////////////////////////////");

        do {
            List<Consignment> list = new ArrayList<>();
            List<TransferUnit> tuList = TransferUnit.toTransferUnitList(gameVisualise.getFromRoom("переместить", new TransferUnit("\\w+-\\w+-\\w+[\\.\\w+]*")));

            tuList.sort(Comparator.comparing(TransferUnit::getNumBatch).reversed());
            tuList.sort(Comparator.comparing(TransferUnit::getNumConsignment).reversed());

            System.out.println(tuList);

            for (TransferUnit tu : tuList) {
                int numСonsignment = tu.getNumConsignment() - 1;
                int numBatch = tu.getNumBatch() - 1;

                Consignment consignmentFromInput = storage.getElements().get(storage.getElements().keySet().stream().toList().get(numСonsignment)).get(numBatch).clone();

                BigDecimal amount;
                if (consignmentFromInput.checkKG()){
                    amount = tu.getAmount();
                }
                else {
                    amount = Utils.round(tu.getAmount(),0);
                }

                consignmentFromInput.setAmount(consignmentFromInput.minusAmount(amount));

                list.add(consignmentFromInput);
            }

            Amounts amounts = Utils.countAmounts(list);

            boolean nonFreeze = amounts.getNonFreeze().compareTo(store.getCapacity()) > 0;
            boolean freeze = amounts.getFreeze().compareTo(store.getFridgeCapacity()) > 0;

            if (transfer(storage, store, list)){
                show(amounts, nonFreeze, freeze);
                continue;
            }

            show(amounts, nonFreeze, freeze);
        } while (gameVisualise.continueEvent("Продолжить перемещение"));
    }

    private void show(Amounts amounts, boolean nonFreeze, boolean freeze){
        gameVisualise.showRoom(storage);
        gameVisualise.showInfoAmount(amounts, store, "В торговом зале", nonFreeze, freeze);
        gameVisualise.showRoom(store);
    }

    //   3-3-74.91,3-2-51.91,3-3-100,1-2-20.99
    //   3-1-10,2-1-24.92,1-1-11.91,3-1-0.99
    //   3-1-0.98,2-1-1.93,1-1-12,3-1-1,2-1-1,1-1-1

    //Введите что переместить:
    //5-1-10,4-1-1,3-1-22,4-1-1,2-1-100
    //[TransferUnit{numConsignment=2, numBatch=1, amout=100.0}, x5

    private boolean transfer(Room from, Room to, List<Consignment> list){
        Amounts amounts = Utils.countAmounts(list);

        amounts.plusFreeze(to.getAmounts().getFreeze());
        boolean nonFreeze = amounts.getNonFreeze().compareTo(to.getCapacity()) > 0;

        amounts.plusNonFreeze(to.getAmounts().getNonFreeze());
       boolean freeze = amounts.getFreeze().compareTo(to.getFridgeCapacity()) > 0;
        gameVisualise.showRoom(store);
        gameVisualise.showRoom(storage);
        if (nonFreeze || freeze){
            gameVisualise.showInfoAmount(amounts, to, to.equals(store) ? "В торговом зале" : "На складе", nonFreeze, freeze);
        }
        else {
            from.moveElements(to, list);
//            from.refresh();
//            to.refresh();
        }
        return nonFreeze || freeze;
    }

}
