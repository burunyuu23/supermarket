package ru.vsu.edu.shlyikov_d_g.main.application.helper;

import ru.vsu.edu.shlyikov_d_g.main.visualisation.graphics.adapters.ReadyListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ContinuePanel extends AbstractPanel {

    private List<ReadyListener> exitListeners = new ArrayList<>();
    private List<ReadyListener> continueListeners = new ArrayList<>();

    public void addContinueListener(ReadyListener toAdd) {
        continueListeners.add(toAdd);
    }

    public void addExitListener(ReadyListener toAdd) {
        exitListeners.add(toAdd);
    }

    public ContinuePanel(String name){
        super(new Dimension());
        JDialog jDialog = new JDialog();
        jDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
            jDialog.dispose();
            continueListeners.forEach(ReadyListener::ready);
        });
        aExit.addActionListener(a -> {
            jDialog.dispose();
            exitListeners.forEach(ReadyListener::ready);
        });
    }
}
