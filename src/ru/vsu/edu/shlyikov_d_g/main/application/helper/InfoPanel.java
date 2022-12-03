package ru.vsu.edu.shlyikov_d_g.main.application.helper;

import ru.vsu.edu.shlyikov_d_g.main.application.helper.AbstractPanel;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends AbstractPanel {
    protected JDialog jDialog = new JDialog();
    protected JTextArea jTextPane = new JTextArea(5,30);

    public InfoPanel(Dimension dimension, String string){
        super(dimension);

        jDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jDialog.setResizable(false);
        jDialog.add(this);
        jTextPane.setEditable(false);
        jTextPane.setText(string);
        jTextPane.setWrapStyleWord(true);
        jTextPane.setLineWrap(true);
        jTextPane.setAlignmentX(JTextField.CENTER);
        jTextPane.setText(string);
        setNormal(jTextPane);
        add(jTextPane);
        setVisible(true);

        jDialog.setVisible(true);
        jDialog.pack();
    }

    public void dispose(){
        try {
            Thread.sleep(7500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        jDialog.dispose();
    }
}
