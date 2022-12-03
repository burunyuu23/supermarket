package ru.vsu.edu.shlyikov_d_g.main.application.game;

import ru.vsu.edu.shlyikov_d_g.main.application.helper.AbstractDrawer;
import ru.vsu.edu.shlyikov_d_g.main.application.helper.ConsignmentPanel;
import ru.vsu.edu.shlyikov_d_g.products.Consignment;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ScalesPanel extends AbstractDrawer {
    BufferedImage img = null;
    Consignment consignment;

    public ScalesPanel(Consignment consignment){
        setLayout(new BorderLayout());

        this.consignment = consignment;
        try {
            img = ImageIO.read(new File("C:\\Users\\zEzzLike\\IdeaProjects\\oop_atta1\\src\\ru\\vsu\\edu\\shlyikov_d_g\\resources\\scales.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void draw(Graphics2D g) {
        g.drawImage(img, 0, 0, null);
        g.setStroke(new BasicStroke(2));
        g.setColor(Color.GREEN);
        g.drawString(consignment.getProductName(), 483 + 610/16, 367+185/2);
        g.drawRect(483, 367, 610, 185);

        g.drawString(consignment.getVendorCode(), 639, 111);

        g.setFont(new Font(g.getFont().getFontName(), g.getFont().getStyle(), 15));
        g.drawString(consignment.getAmount() + " " + consignment.getMeasure(), 597 + 5, 621+15);
        g.drawRect(597, 621, 510, 18);
    }
}
