package ru.vsu.edu.shlyikov_d_g.main.application.game;

import ru.vsu.edu.shlyikov_d_g.main.application.ShopFrame;

import javax.swing.*;
import java.util.Locale;

public class TEST {
    public static void main(String[] args) {
        GamePanel gamePanel = new GamePanel();
        JFrame jFrame = new JFrame();
        jFrame.add(gamePanel);

        Locale.setDefault(Locale.ROOT);

        java.awt.EventQueue.invokeLater(() -> {
            jFrame.setSize(1600, 900);
            jFrame.setResizable(false);
            jFrame.setVisible(true);
        });
    }
}
