package ru.vsu.edu.shlyikov_d_g.visualisation.graphics.adapters;

import ru.vsu.edu.shlyikov_d_g.main.application.game.GamePanel;
import ru.vsu.edu.shlyikov_d_g.visualisation.graphics.Panel;

import java.awt.event.MouseEvent;

public class GamePanelMouseAdapter extends MouseAdapter {
    private final GamePanel gamePanel;

    public GamePanelMouseAdapter(Panel panel, GamePanel gamePanel) {
        super(panel);
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
//        System.out.println(x + " " + y);
        if (gamePanel.getCurrentGamePanel() == GamePanel.CurrentGamePanel.CASHDESK_PANEL)
            if (x < 559 && x > 244 && y < 555 && y > 389) {
                gamePanel.setCustomerChosen(true);
            }

        if (gamePanel.getCurrentGamePanel() == GamePanel.CurrentGamePanel.SCALES_PANEL)
            if (x > 483 && y > 367 && x < 483 + 610 && y < 367 + 185) {
                gamePanel.setScalesRight(true);
            }

        if (gamePanel.getCurrentGamePanel() == GamePanel.CurrentGamePanel.BARCODE_MANUAL_PANEL) {
            if (x > gamePanel.getWidth() - 60 && y > gamePanel.getHeight() / 2 - 30 &&
                    x < gamePanel.getWidth() - 10 && y < gamePanel.getHeight() / 2 + 20){
                gamePanel.setCurrentGamePanel(GamePanel.CurrentGamePanel.BARCODE_AUTO_PANEL);
            }
        }
    }

}
