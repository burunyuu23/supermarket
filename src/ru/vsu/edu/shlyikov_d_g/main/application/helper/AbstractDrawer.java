package ru.vsu.edu.shlyikov_d_g.main.application.helper;

import java.awt.*;

public abstract class AbstractDrawer extends AbstractPanel{
    public AbstractDrawer() {
        super(new Dimension());
    }

    protected abstract void draw(Graphics2D g);

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        draw((Graphics2D) g);
    }

    @Override
    public void paintComponent (Graphics g){
        repaint();
    }
}
