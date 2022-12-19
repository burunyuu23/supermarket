package ru.vsu.edu.shlyikov_d_g.main.application.helper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoPanel extends AbstractPanel {
    protected JFrame jFrame = new JFrame();
    protected JTextArea jTextPane = new JTextArea(5,30);

    public InfoPanel(String string){
        super(new Dimension());

        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.add(this);
        jTextPane.setEditable(false);
        jTextPane.setText(string);
        jTextPane.setWrapStyleWord(true);
        jTextPane.setLineWrap(true);
        jTextPane.setAlignmentX(JTextField.CENTER);
        jTextPane.setText(string);
        setNormal(jTextPane);
        add(jTextPane);
        setVisible(true);

        jFrame.setVisible(true);
        jFrame.pack();

        new Timer(7500, e -> jFrame.dispose()).start();
    }
}
