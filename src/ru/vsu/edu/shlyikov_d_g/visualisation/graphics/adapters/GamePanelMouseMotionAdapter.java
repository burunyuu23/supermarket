package ru.vsu.edu.shlyikov_d_g.visualisation.graphics.adapters;

import ru.vsu.edu.shlyikov_d_g.main.application.game.GamePanel;
import ru.vsu.edu.shlyikov_d_g.visualisation.graphics.Panel;

import java.awt.*;
import java.awt.event.*;

public class GamePanelMouseMotionAdapter implements MouseMotionListener {
    private final ru.vsu.edu.shlyikov_d_g.visualisation.graphics.Panel panel;
    private final GamePanel gamePanel;
    private boolean checker = false;

    public GamePanelMouseMotionAdapter(Panel panel, GamePanel gamePanel) {
        this.panel = panel;
        this.gamePanel = gamePanel;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (gamePanel.getCurrentGamePanel() == GamePanel.CurrentGamePanel.BARCODE_MANUAL_PANEL){
            int x = e.getX();
            int y = e.getY();
            gamePanel.getChecker().setPoint(new Point(x, y));

            if (x < 1062 && x > 544 && y < 380 && y > 370) checker = true;
            else if (x < 1062 && x > 544 && y > 670 && y < 700 && checker) gamePanel.getBarcodeManualPanel().getListeners().forEach(ReadyListener::ready);
            else if (x < 1062 && x > 544 && y > 360 && y < 690){
            }
            else checker = false;
//            System.out.println(e.getX() + " " + e.getY());
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }
}
