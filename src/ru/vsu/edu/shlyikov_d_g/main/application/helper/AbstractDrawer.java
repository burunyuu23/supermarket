package ru.vsu.edu.shlyikov_d_g.main.application.helper;

import ru.vsu.edu.shlyikov_d_g.visualisation.graphics.adapters.ReadyListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDrawer extends AbstractPanel{
    protected List<ReadyListener> listeners = new ArrayList<>();

    public void addAllListener(List<ReadyListener> toAdd) {
        listeners.addAll(toAdd);
    }
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
