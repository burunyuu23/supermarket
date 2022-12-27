package ru.vsu.edu.shlyikov_d_g.main.network;

import ru.vsu.edu.shlyikov_d_g.events.Purchase;
import ru.vsu.edu.shlyikov_d_g.humans.buyers.Customer;
import ru.vsu.edu.shlyikov_d_g.main.Shop;
import ru.vsu.edu.shlyikov_d_g.main.application.helper.AbstractPanel;
import ru.vsu.edu.shlyikov_d_g.main.application.room.RoomConsignmentPanel;
import ru.vsu.edu.shlyikov_d_g.main.application.room.RoomPanel;
import ru.vsu.edu.shlyikov_d_g.main.visualisation.Game;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

import ru.vsu.edu.shlyikov_d_g.main.visualisation.graphics.Panel;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.products.units.TransferUnit;
import ru.vsu.edu.shlyikov_d_g.utils.Utils;
import java.util.List;

import javax.swing.*;

public class PurchaseGame extends RoomPanel implements Game {
    JFrame jFrame = new JFrame();
    Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public PurchaseGame(NetworkPanel panel, Socket socket) throws IOException {
        super(panel, panel.getShop().getStore(),"Купить");
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        jFrame.add(this);
        addListener(() -> {
            // посылать серверу
            panel.addPurchase(new Purchase(new Customer("пал палыч", 111), consignmentInput(panel.getList())));
            panel.getList().clear();
        });
        winMain();
    }

    private List<Consignment> consignmentInput(List<String> input){
        java.util.List<Consignment> list = new ArrayList<>();
        java.util.List<TransferUnit> tuList = TransferUnit.toTransferUnitList(input);

        tuList.sort(Comparator.comparing(TransferUnit::getNumBatch).reversed());
        tuList.sort(Comparator.comparing(TransferUnit::getNumConsignment).reversed());

        for (TransferUnit tu : tuList) {
            int numСonsignment = tu.getNumConsignment() - 1;
            int numBatch = tu.getNumBatch() - 1;

            Consignment consignmentFromInput = panel.getShop().getStore().getElements().get(
                    panel.getShop().getStore().getElements().keySet().stream().toList().get(numСonsignment)).get(numBatch).clone();

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

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        repaint();
    }

    @Override
    public void repaint(Rectangle r) {
        super.repaint(r);
    }

    private void winMain() {
        Locale.setDefault(Locale.ROOT);

        java.awt.EventQueue.invokeLater(() -> {
            jFrame.setSize(1600, 900);
            jFrame.setVisible(true);
        });
    }
}
