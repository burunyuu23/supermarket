package ru.vsu.edu.shlyikov_d_g.events;

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

public class Transfer {
    Room storage;
    Room store;
    GameVisualise gameVisualise;

    public Transfer(Room storage, Room store, GameVisualise gameVisualise) {
        this.storage = storage;
        this.store = store;
        this.gameVisualise = gameVisualise;
    }

    public void askStorage() {
        gameVisualise.showRoom(storage);

        do {
            List<Consignment> list = consignmentInput(gameVisualise.getFrom("Переместить", new TransferUnit("\\w+-\\w+-\\w+[\\.\\w+]*"), storage));

            Amounts amounts = Utils.countAmounts(list);

            boolean nonFreeze = amounts.getNonFreeze().compareTo(store.getNonFridgeCapacity()) > 0;
            boolean freeze = amounts.getFreeze().compareTo(store.getFridgeCapacity()) > 0;

            show(amounts, nonFreeze, freeze);

            if (transfer(storage, store, list)){
                continue;
            }

            show(amounts, nonFreeze, freeze);
        } while (gameVisualise.continueEvent("Продолжить перемещение"));
    }

    private void show(Amounts amounts, boolean nonFreeze, boolean freeze){
        gameVisualise.showInfoAmount(amounts, store, "В торговом зале", nonFreeze || freeze);
    }

    public List<Consignment> consignmentInput(List<String> input){
        List<Consignment> list = new ArrayList<>();
        List<TransferUnit> tuList = TransferUnit.toTransferUnitList(input);

        tuList.sort(Comparator.comparing(TransferUnit::getNumBatch).reversed());
        tuList.sort(Comparator.comparing(TransferUnit::getNumConsignment).reversed());

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

        return list;
    }

    //   3-3-74.91,3-2-51.91,3-3-100,1-2-20.99
    //   3-1-10,2-1-24.92,1-1-11.91,3-1-0.99
    //   3-1-0.98,2-1-1.93,1-1-12,3-1-1,2-1-1,1-1-1

    //Введите что переместить:
    //5-1-10,4-1-1,3-1-22,4-1-1,2-1-100
    //[TransferUnit{numConsignment=2, numBatch=1, amout=100.0}, x5

    public boolean isNormalize(Room to, Amounts amounts){
        amounts.plusFreeze(to.getAmounts().getFreeze());
        boolean nonFreeze = amounts.getNonFreeze().compareTo(to.getNonFridgeCapacity()) > 0;

        amounts.plusNonFreeze(to.getAmounts().getNonFreeze());
        boolean freeze = amounts.getFreeze().compareTo(to.getFridgeCapacity()) > 0;

        return nonFreeze || freeze;
    }

    public boolean transfer(Room from, Room to, List<Consignment> list){
        Amounts amounts = Utils.countAmounts(list);

        boolean normalize = isNormalize(to, amounts);

        if (normalize){
            gameVisualise.showInfoAmount(amounts, to, to.equals(store) ? "В торговом зале" : "На складе", true);
        }
        else {
            from.moveElements(to, list);
//            from.refresh();
//            to.refresh();
        }
        return normalize;
    }

}
