package ru.vsu.edu.shlyikov_d_g.main.application.helper;

import ru.vsu.edu.shlyikov_d_g.visualisation.graphics.adapters.ReadyListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ContinuePanel extends AbstractPanel {

    private boolean answer = false;
    private List<ReadyListener> listeners = new ArrayList<>();

    public void addListener(ReadyListener toAdd) {
        listeners.add(toAdd);
    }

    public boolean isAnswer() {
        return answer;
    }

    public void setAnswer(boolean answer) {
        this.answer = answer;
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
            listeners.forEach(ReadyListener::ready);
            answer = true;
            jDialog.dispose();
        });
        aExit.addActionListener(a -> {
            listeners.forEach(ReadyListener::ready);
            answer = false;
            jDialog.dispose();
        });
    }
}
