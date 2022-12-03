package ru.vsu.edu.shlyikov_d_g.main.application.helper;

import javax.swing.*;
import java.awt.*;

public class ContinuePanel extends AbstractPanel {

    boolean clicked = false;
    boolean answer = false;

    public boolean isClicked() {
        return clicked;
    }

    public boolean isAnswer() {
        return answer;
    }

    public ContinuePanel(String name){
        super(new Dimension());
        JDialog jDialog = new JDialog();
        jDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        jDialog.setResizable(false);
        jDialog.add(this);
        jDialog.setMinimumSize(new Dimension(600, 300));
        jDialog.setVisible(true);

        JTextPane jTextPane = new JTextPane();
        setNormal(jTextPane);
        jTextPane.setText(String.format("%s?", name));
        JButton aContinue = new JButton("Продолжить");
        JButton aExit = new JButton("Отмена");
        setNormal(aContinue);
        setNormal(aExit);

        add(jTextPane);
        add(aContinue);
        add(aExit);

        jDialog.pack();

        aContinue.addActionListener(a -> {
            clicked = true;
            answer = true;
            jDialog.dispose();
        });
        aExit.addActionListener(a -> {
            clicked = true;
            answer = false;
            jDialog.dispose();
        });
    }
}
