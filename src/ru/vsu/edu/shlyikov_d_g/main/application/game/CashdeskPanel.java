package ru.vsu.edu.shlyikov_d_g.main.application.game;

import ru.vsu.edu.shlyikov_d_g.humans.buyers.Customer;
import ru.vsu.edu.shlyikov_d_g.main.application.helper.AbstractDrawer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CashdeskPanel  extends AbstractDrawer {
    BufferedImage img = null;
    Customer customer;

    public CashdeskPanel(Customer customer){
        this.customer = customer;
        try {
            img = ImageIO.read(new File("C:\\Users\\zEzzLike\\IdeaProjects\\oop_atta1\\src\\ru\\vsu\\edu\\shlyikov_d_g\\resources\\cashdesk.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        @Override
        protected void draw(Graphics2D g) {
            g.drawImage(img, 0, 0, null);
            g.setStroke(new BasicStroke(3));
            g.setColor(Color.GREEN);
            g.drawString(customer.getName(), 244, 389+166/2);
            g.drawRect(244, 389, 315, 166);
        }
}
