package ru.vsu.edu.shlyikov_d_g.main.application.helper;

import ru.vsu.edu.shlyikov_d_g.products.Consignment;
import ru.vsu.edu.shlyikov_d_g.visualisation.graphics.Panel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AbstractPanel extends JPanel
{
    protected final Color textFieldColor = new Color(207,203,140);
    protected final Font textFont = new Font("Microsoft Sans Serif", Font.BOLD, 46);
    protected Graphics2D g;
    protected Panel panel;

    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(textFieldColor);
        setFont(textFont);

        this.setVisible(true);
    }

    public AbstractPanel(Panel panel){
        this.panel = panel;
    }

    public AbstractPanel(Dimension dimension){
        setMinimumSize(dimension);
    }

    protected void setNormal(JComponent component){
        component.setFont(textFont);
        component.setBackground(textFieldColor);
        component.setVisible(true);
        component.setAlignmentX(Component.CENTER_ALIGNMENT);
        component.setAlignmentY(Component.TOP_ALIGNMENT);
    }

    protected void refreshGraphics(){
        g.setColor(Color.BLACK);
        g.setFont(textFont);
    }
}
