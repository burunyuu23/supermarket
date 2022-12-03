package ru.vsu.edu.shlyikov_d_g.main.application.helper;

import javax.swing.*;
import java.awt.*;

public class ChequePanel extends InfoPanel {
    public ChequePanel(Dimension dimension, String string) {
        super(dimension, string);

        setLayout(null);
        setVisible(false);
        jDialog.setBackground(Color.WHITE);
        JScrollPane jScrollPane = new JScrollPane();
        setBackground(Color.WHITE);
        jScrollPane.getViewport().setView(jTextPane);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jDialog.add(jScrollPane);
        jTextPane.setBackground(Color.WHITE);
        jScrollPane.setVisible(true);

        dispose();
    }
}
