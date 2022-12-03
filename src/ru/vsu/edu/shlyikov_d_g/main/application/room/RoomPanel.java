package ru.vsu.edu.shlyikov_d_g.main.application.room;

import ru.vsu.edu.shlyikov_d_g.main.application.helper.AbstractPanel;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.rooms.Room;
import ru.vsu.edu.shlyikov_d_g.utils.Amounts;
import ru.vsu.edu.shlyikov_d_g.utils.Utils;
import ru.vsu.edu.shlyikov_d_g.visualisation.graphics.Panel;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class RoomPanel extends AbstractPanel {
    private Room room;
    private final JTextPane jTextPane = new JTextPane();
    private final JScrollPane jScrollPane = new JScrollPane();
    private final Container cont = new Container();
    private final JButton button = new JButton();
    private boolean ready = false;
    private String operationName = null;

    {
        setNormal(jTextPane);
        jTextPane.setEditable(false);
        StyledDocument doc = jTextPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        jTextPane.setStyledDocument(doc);
        add(jTextPane, BorderLayout.CENTER);

        cont.setLayout(new BoxLayout(cont, BoxLayout.Y_AXIS));

        setNormal(jScrollPane);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane.setViewportBorder(new LineBorder(Color.RED));
        jScrollPane.getViewport().setView(cont);
        add(jScrollPane);

        setNormal(button);
        button.addActionListener(a -> ready = true);
        add(button);
    }

    public RoomPanel(){
        super(new Dimension());
    }

    public RoomPanel(Panel panel, List<Consignment> consignmentList, String operationName) {
        super(panel);

        this.operationName = operationName;

        Room room1 = panel.getShop().getStorage();
        String toRoom = getRoomString(room1);
        Amounts amounts = Utils.countAmounts(consignmentList);
            jTextPane.setText("Корзинка" + "\n"
                    + "Продукты требующие заморозки: " + amounts.getFreeze() + "\n"
                    + "Обычные продукты: " + amounts.getNonFreeze() + "\n"
                    + "to: " + toRoom + "\n"
                    + "money: " + Utils.countPrice(consignmentList) + "/" + panel.getShop().getMoney().getMoney());
        button.setText(operationName);

        for (int i = 0; i < consignmentList.size(); i++) {
            Consignment consignment = consignmentList.get(i);
            cont.add(new RoomConsignmentPanel(consignment, panel, operationName,i + 1, -1, getWidth()));
        }
        repaint();
    }

    private String getRoomString(Room room){
        return room.getRoomName() + "\n"
                + "Продукты требующие заморозки: " + room.getAmounts().getFreeze() + "/" + room.getFridgeCapacity() + "\n"
                + "Обычные продукты: " + room.getAmounts().getNonFreeze() + "/" + room.getNonFridgeCapacity();
    }

    public RoomPanel(Panel panel, Room room, String operationName) {
        super(panel);

        String fromRoom = getRoomString(room);
        if(Objects.equals(operationName.toLowerCase(Locale.ROOT), "переместить")) {
            Room room1;
            if (panel.getShop().getStorage() == room) {
                room1 = panel.getShop().getStore();
            } else {
                room1 = panel.getShop().getStorage();
            }
            String toRoom = getRoomString(room1);
            jTextPane.setText("from: " + fromRoom + "\n" + "to: " + toRoom);
        }
        else{
            jTextPane.setText(fromRoom);
        }
        button.setText(operationName);

        int i = 0;
        for (String vendorCode : room.getElements().keySet()) {
            i++;
            for (Integer batch : room.getElements().get(vendorCode).keySet()) {
                cont.add(new RoomConsignmentPanel(room.getElements().get(vendorCode).get(batch), panel, operationName, i, batch + 1, getWidth()));
            }
        }
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    // TODO лист с продуктами с кнопками удалить/переместить
}
