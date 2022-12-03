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

public class BarcodeManualPanel extends AbstractDrawer {
    BufferedImage back = null;
    BufferedImage consignmentIcon = null;
    BufferedImage barcode = null;
    JButton switcher = new JButton(">");
    boolean switchering = false;
    boolean completed = false;
    Checker checker = new Checker();
    Consignment consignment;

    public Checker getChecker() {
        return checker;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isSwitchering() {
        return switchering;
    }

    public void setSwitchering(boolean switchering) {
        this.switchering = switchering;
    }

    public BarcodeManualPanel(Consignment consignment){
        this.consignment = consignment;
        setLayout(null);
        setNormal(switcher);
        switcher.setBounds(getWidth() - 120, getHeight()/2 - 50, 100, 100);

        switcher.addActionListener(a -> switchering = true);
        try {
            back = ImageIO.read(new File("C:\\Users\\zEzzLike\\IdeaProjects\\oop_atta1\\src\\ru\\vsu\\edu\\shlyikov_d_g\\resources\\barcode2_background.png"));
            consignmentIcon = ImageIO.read(new File("C:\\Users\\zEzzLike\\IdeaProjects\\oop_atta1\\src\\ru\\vsu\\edu\\shlyikov_d_g\\resources\\product.png"));
            barcode = ImageIO.read(new File("C:\\Users\\zEzzLike\\IdeaProjects\\oop_atta1\\src\\ru\\vsu\\edu\\shlyikov_d_g\\resources\\barcode.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void draw(Graphics2D g) {
        g.drawImage(back, 0, 0, null);
        g.drawImage(consignmentIcon, 0, 0, null);
        g.drawImage(barcode, 0, 0, null);
        g.drawString(consignment.getVendorCode(), 657, 631);

        checker.draw(g);

        g.translate(getWidth() - 60, getHeight()/2 - 30);
        switcher.paint(g);
    }

    public class Checker extends AbstractDrawer{
        private Point point;
        private boolean exist = false;
        private BufferedImage checker;

        public Checker(){
            try {
                checker = ImageIO.read(new File("C:\\Users\\zEzzLike\\IdeaProjects\\oop_atta1\\src\\ru\\vsu\\edu\\shlyikov_d_g\\resources\\scanner.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public Point getPoint() {
            return point;
        }

        public void setPoint(Point point) {
            this.point = point;
        }

        public boolean isExist() {
            return exist;
        }

        public void setExist(boolean exist) {
            this.exist = exist;
        }

        @Override
        protected void draw(Graphics2D g) {
            if (point != null) g.drawImage(checker, (int) point.getX() - checker.getWidth()/2, (int) point.getY() - checker.getHeight()/4, null);
        }
    }

}
