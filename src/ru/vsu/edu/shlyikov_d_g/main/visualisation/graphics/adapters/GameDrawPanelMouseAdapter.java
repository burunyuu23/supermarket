package ru.vsu.edu.shlyikov_d_g.main.visualisation.graphics.adapters;

import ru.vsu.edu.shlyikov_d_g.main.application.helper.ConsignmentPanel;
import ru.vsu.edu.shlyikov_d_g.main.application.non_game.SupplyMenuPanel;
import ru.vsu.edu.shlyikov_d_g.main.visualisation.graphics.Panel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class GameDrawPanelMouseAdapter extends MouseAdapter implements MouseListener {
    private final SupplyMenuPanel supplyMenuPanel;
    private final List<ConsignmentPanel> list = new ArrayList<>();

    public GameDrawPanelMouseAdapter(SupplyMenuPanel supplyMenuPanel, Panel panel) {
        super(panel);
        this.supplyMenuPanel = supplyMenuPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        Point pointWidthAndHeigth = supplyMenuPanel.getWidthAndHeight();
        for (int i = 0; i < supplyMenuPanel.getPoints().length; i++){
            Point[] pArray = supplyMenuPanel.getPoints()[i];
            for (int j = 0; j < pArray.length; j++) {
                Point p = pArray[j];
                if (x > p.getX() && x < (p.getX() + pointWidthAndHeigth.getX())
                        && y > p.getY() && y < (p.getY() + pointWidthAndHeigth.getY())){
                    int finalI = i;
                    int finalJ = j;
                    new Thread(() -> new ConsignmentPanel(supplyMenuPanel.getConsignment(finalI, finalJ), panel, "Купить", finalI + 1, finalJ + 1)).start();
                }
            }
        }
        for (ConsignmentPanel consignmentPanel:list) {
            consignmentPanel.setVisible(true);
        }
//        System.out.println(e.getX() + " " + e.getY());
    }
}
