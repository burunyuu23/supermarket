package ru.vsu.edu.shlyikov_d_g.main.application.helper;

import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.visualisation.graphics.Panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class ConsignmentPanel extends AbstractPanel {

    public ConsignmentPanel(Consignment consignment, Panel panel, String operationName, int field1, int field2){
        super(panel);

        JDialog jDialog = new JDialog();
        jDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jDialog.setResizable(false);
        jDialog.add(this);
        jDialog.setMinimumSize(new Dimension(600, 900));

        setMinimumSize(new Dimension(600, 900));

        JTextPane consignmentName = new JTextPane();
        consignmentName.setText(consignment.toStringSupplier());
        consignmentName.setEditable(false);
        setNormal(consignmentName);
        add(consignmentName);

        JSlider amountSlider = new JSlider();

        double multiply = consignment.checkKG() ? 100.0 : 1;

        amountSlider.setModel(new DefaultBoundedRangeModel(0, 0, 0, (int) (consignment.getAmount().doubleValue()*multiply)));
        Hashtable<Integer, JLabel> ht = new Hashtable<>();
        for (int i = 0; i < consignment.getAmount().doubleValue()*multiply; ++i) {
            JLabel label = new JLabel();
            ht.put(i, label);
        }
        amountSlider.setLabelTable(ht);

        JLabel amountText = new JLabel();

        amountText.setText((amountSlider.getValue()/multiply + " / " + amountSlider.getMaximum()/multiply));
        amountSlider.addChangeListener(arg0 -> amountText.setText((amountSlider.getValue()/multiply + " / " + amountSlider.getMaximum()/multiply)));
        setNormal(amountSlider);
        setNormal(amountText);
        add(amountSlider);
        add(amountText);

        JButton buyButton = new JButton(operationName);
        buyButton.addActionListener(a -> {
            StringBuilder sb = new StringBuilder();
            sb.append(field1);
            sb.append("-");
            if (field2 != -1) {
                sb.append(field2);
                sb.append("-");
            }
            sb.append(amountSlider.getValue()/multiply);
            panel.getList().add(sb.toString());
            jDialog.dispose();
        });
        setNormal(buyButton);
        add(buyButton);

        jDialog.setVisible(true);
        jDialog.pack();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        jDialog.dispose();
    }
}
