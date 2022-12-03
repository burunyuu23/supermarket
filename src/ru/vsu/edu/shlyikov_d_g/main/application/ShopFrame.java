package ru.vsu.edu.shlyikov_d_g.main.application;

import ru.vsu.edu.shlyikov_d_g.main.application.game.GamePanel;
import ru.vsu.edu.shlyikov_d_g.main.application.non_game.MainMenuPanel;
import ru.vsu.edu.shlyikov_d_g.main.application.non_game.SupplyMenuPanel;
import ru.vsu.edu.shlyikov_d_g.main.application.room.RoomPanel;

import javax.swing.*;
import java.awt.*;

public class ShopFrame extends JFrame{
    private JPanel formPanel;
    private JPanel mainCardsPanel;
    private JPanel mainPanel;
    private JPanel supplyPanel;
    private JTextField jTextField;
    private JPanel gamePanel;
    private JPanel roomPanel = new RoomPanel();
    private CurrentNotGamePanel currentNotGamePanel;

    public JPanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(JPanel gamePanel) {
        if (this.gamePanel != null) mainCardsPanel.remove(this.gamePanel);
        this.gamePanel = gamePanel;
        mainCardsPanel.add(this.gamePanel, String.valueOf(CurrentNotGamePanel.GAME_PANEL));
    }

    public void setRoomPanel(JPanel roomPanel) {
        mainCardsPanel.remove(this.roomPanel);
        this.roomPanel = roomPanel;
        mainCardsPanel.add(this.roomPanel, String.valueOf(CurrentNotGamePanel.ROOM_PANEL));
    }

    private void goToLayout(JPanel cards) {
        CardLayout cardLayout = (CardLayout) cards.getLayout();
        cardLayout.show(cards, String.valueOf(currentNotGamePanel));
    }

    public String getCurrentNotGamePanelString() {
        return String.valueOf(currentNotGamePanel);
    }

    public CurrentNotGamePanel getCurrentNotGamePanel() {
        return currentNotGamePanel;
    }

    public void setCurrentNotGamePanel(CurrentNotGamePanel currentNotGamePanel) {
        this.currentNotGamePanel = currentNotGamePanel;
        goToLayout(mainCardsPanel);
    }

    public enum CurrentNotGamePanel {
        MAIN_MENU_PANEL,
        SUPPLY_MENU_PANEL,
        ROOM_PANEL,
        GAME_PANEL
    }

    public ShopFrame(MainMenuPanel mainMenuPanel, SupplyMenuPanel supplyMenuPanel, GamePanel gamePanel) { // TODO передавать shop
        this.setTitle("Supermarket");
        this.setContentPane(formPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(1600, 900);

        JButton startGame = new JButton("Играть");
        startGame.setBounds(547,-222,634,90);

        startGame.addActionListener(actionEvent -> {
            System.out.println(jTextField.getText());
            currentNotGamePanel = CurrentNotGamePanel.SUPPLY_MENU_PANEL;
            goToLayout(mainCardsPanel);
        });

        mainCardsPanel.add(mainPanel, String.valueOf(CurrentNotGamePanel.MAIN_MENU_PANEL));
        mainCardsPanel.add(supplyPanel, String.valueOf(CurrentNotGamePanel.SUPPLY_MENU_PANEL));
        mainCardsPanel.add(roomPanel, String.valueOf(CurrentNotGamePanel.ROOM_PANEL));
        mainCardsPanel.add(gamePanel, String.valueOf(CurrentNotGamePanel.GAME_PANEL));

        currentNotGamePanel = CurrentNotGamePanel.MAIN_MENU_PANEL;
        goToLayout(mainCardsPanel);

        mainMenuPanel.setStartGame(startGame);
        mainMenuPanel.setTestGame();
        mainMenuPanel.setTextField(jTextField);
        mainPanel.add(mainMenuPanel);
        supplyPanel.add(supplyMenuPanel);

        this.setResizable(false);
        this.setVisible(true);
        this.pack();
    }

}
