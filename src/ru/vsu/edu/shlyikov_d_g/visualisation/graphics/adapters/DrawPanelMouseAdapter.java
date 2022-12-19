package ru.vsu.edu.shlyikov_d_g.visualisation.graphics.adapters;

import ru.vsu.edu.shlyikov_d_g.main.application.non_game.MainMenuPanel;
import ru.vsu.edu.shlyikov_d_g.visualisation.graphics.Panel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DrawPanelMouseAdapter extends MouseAdapter implements MouseListener {
    MainMenuPanel mainMenuPanel;

    public DrawPanelMouseAdapter(MainMenuPanel mainMenuPanel, Panel panel) {
        super(panel);
        this.mainMenuPanel = mainMenuPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (x > 49 && x < 590 && y < 439 && y > 357) mainMenuPanel.getStartGame().doClick();
        if (x > 49 && x < 590 && y < 439+100 && y > 357+100) mainMenuPanel.getTestGame().doClick();
        if (x > 49 && x < 590 && y < 439+200 && y > 357+200) mainMenuPanel.getHelpGame().doClick();
        if (x > 713 && x < 1382 && y < 94 && y > 0) mainMenuPanel.getTextField().grabFocus();
//        System.out.println(e.getX() + " " + e.getY());
    }
}
