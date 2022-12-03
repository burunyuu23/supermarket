package ru.vsu.edu.shlyikov_d_g.visualisation.graphics.adapters;

import ru.vsu.edu.shlyikov_d_g.visualisation.graphics.Panel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseAdapter implements MouseListener{
    protected final Panel panel;

    public MouseAdapter(Panel panel) {
        this.panel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
