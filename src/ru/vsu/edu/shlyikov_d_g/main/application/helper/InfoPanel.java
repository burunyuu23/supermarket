package ru.vsu.edu.shlyikov_d_g.main.application.helper;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends AbstractPanel {
    protected JFrame jFrame = new JFrame();
    protected JTextPane jTextPane = new JTextPane();

    public InfoPanel(String string){
        super(new Dimension());

        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.add(this);
        jTextPane.setEditable(false);
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
